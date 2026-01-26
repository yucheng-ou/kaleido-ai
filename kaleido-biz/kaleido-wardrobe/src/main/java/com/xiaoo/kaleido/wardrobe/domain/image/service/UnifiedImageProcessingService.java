package com.xiaoo.kaleido.wardrobe.domain.image.service;

import com.xiaoo.kaleido.wardrobe.domain.image.chain.ImageProcessingChainBuilder;
import com.xiaoo.kaleido.wardrobe.domain.image.context.ImageProcessingContext;
import com.xiaoo.kaleido.wardrobe.domain.image.enums.DomainType;
import com.xiaoo.kaleido.wardrobe.domain.image.model.ProcessedImageInfo;
import com.xiaoo.kaleido.wardrobe.domain.image.strategy.ImageConversionContext;
import com.xiaoo.kaleido.wardrobe.infrastructure.adapter.file.BasicImageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 统一图片处理服务
 * <p>
 * 整合模板方法、责任链、策略、装饰器模式
 * 提供统一的图片处理入口
 *
 * @author ouyucheng
 * @date 2026/1/23
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UnifiedImageProcessingService {
    
    private final ImageProcessingChainBuilder chainBuilder;
    private final ImageConversionContext conversionContext;
    
    /**
     * 处理图片列表（模板方法模式）
     *
     * @param domainType 领域类型
     * @param imageCommands 图片命令列表
     * @param adapter 适配器函数，将命令转换为BasicImageInfo
     * @param <T> 图片命令类型
     * @param <R> 目标DTO类型
     * @return 处理后的DTO列表
     */
    public <T, R> List<R> processImages(
            DomainType domainType,
            List<T> imageCommands,
            Function<T, BasicImageInfo> adapter) {
        
        log.info("开始处理图片列表，领域类型: {}, 图片数量: {}", domainType, imageCommands.size());
        
        // 1. 验证输入
        validateInput(imageCommands);
        
        // 2. 转换为统一格式（适配器模式）
        List<BasicImageInfo> basicInfos = adaptImageInfos(imageCommands, adapter);
        
        // 3. 处理图片（责任链模式）
        List<ProcessedImageInfo> processedInfos = processImageInfos(basicInfos);
        
        // 4. 转换为目标DTO（策略模式）
        List<R> resultDTOs = convertToDTOs(domainType, processedInfos);
        
        // 5. 后处理
        return postProcess(resultDTOs);
    }
    
    /**
     * 验证输入（模板方法的一部分）
     */
    private <T> void validateInput(List<T> imageCommands) {
        if (imageCommands == null) {
            throw new IllegalArgumentException("图片命令列表不能为null");
        }
        
        if (imageCommands.isEmpty()) {
            log.debug("图片命令列表为空，跳过处理");
        }
    }
    
    /**
     * 适配图片信息（适配器模式）
     */
    private <T> List<BasicImageInfo> adaptImageInfos(
            List<T> imageCommands,
            Function<T, BasicImageInfo> adapter) {
        
        return imageCommands.stream()
                .map(adapter)
                .collect(Collectors.toList());
    }
    
    /**
     * 处理图片信息（责任链模式）
     */
    private List<ProcessedImageInfo> processImageInfos(List<BasicImageInfo> basicInfos) {
        return basicInfos.stream()
                .map(this::processSingleImage)
                .collect(Collectors.toList());
    }
    
    /**
     * 处理单个图片（责任链模式）
     */
    private ProcessedImageInfo processSingleImage(BasicImageInfo basicInfo) {
        ImageProcessingContext context = new ImageProcessingContext(basicInfo);
        
        try {
            // 执行责任链处理
            chainBuilder.process(context);
            
            // 获取处理结果
            ProcessedImageInfo result = context.getResult();
            
            if (result.isSuccess()) {
                log.debug("图片处理成功: {}", basicInfo.getPath());
            } else {
                log.warn("图片处理失败: {}, 错误: {}", basicInfo.getPath(), result.getErrorMessage());
            }
            
            return result;
            
        } catch (Exception e) {
            log.error("图片处理异常: {}, 错误: ", basicInfo.getPath(), e);
            return ProcessedImageInfo.fromBasicOnly(basicInfo);
        }
    }
    
    /**
     * 转换为目标DTO（策略模式）
     */
    private <R> List<R> convertToDTOs(
            DomainType domainType,
            List<ProcessedImageInfo> processedInfos) {
        
        List<R> result = new java.util.ArrayList<>();
        for (ProcessedImageInfo processedInfo : processedInfos) {
            try {
                R dto = conversionContext.convert(
                        domainType,
                        processedInfo.getBasicInfo(),
                        processedInfo.getMinioInfo()
                );
                if (dto != null) {
                    result.add(dto);
                }
            } catch (Exception e) {
                log.error("图片DTO转换失败，路径: {}, 错误: ", 
                        processedInfo.getBasicInfo().getPath(), e);
            }
        }
        return result;
    }
    
    /**
     * 后处理（模板方法的一部分）
     */
    private <R> List<R> postProcess(List<R> resultDTOs) {
        // 这里可以添加后处理逻辑，如日志记录、统计等
        log.info("图片处理完成，成功处理数量: {}", resultDTOs.size());
        return resultDTOs;
    }
    
    /**
     * 批量处理图片（简化版）
     *
     * @param domainType 领域类型
     * @param basicInfos 基础图片信息列表
     * @param <R> 目标DTO类型
     * @return 处理后的DTO列表
     */
    public <R> List<R> processBasicImages(
            DomainType domainType,
            List<BasicImageInfo> basicInfos) {
        
        return processImages(
                domainType,
                basicInfos,
                Function.identity() // BasicImageInfo已经是目标类型，使用恒等函数
        );
    }
    
    /**
     * 处理单个图片（简化版）
     *
     * @param domainType 领域类型
     * @param basicInfo 基础图片信息
     * @param <R> 目标DTO类型
     * @return 处理后的DTO
     */
    public <R> R processSingleImage(
            DomainType domainType,
            BasicImageInfo basicInfo) {
        
        List<R> results = processBasicImages(domainType, List.of(basicInfo));
        return results.isEmpty() ? null : results.get(0);
    }
    
    /**
     * 检查领域类型是否支持
     *
     * @param domainType 领域类型
     * @return 是否支持
     */
    public boolean supportsDomain(DomainType domainType) {
        return conversionContext.supports(domainType);
    }
    
    /**
     * 获取支持的领域类型
     *
     * @return 领域类型数组
     */
    public DomainType[] getSupportedDomains() {
        return conversionContext.getSupportedDomainTypes();
    }
}
