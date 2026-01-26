package com.xiaoo.kaleido.wardrobe.domain.image.chain;

import com.xiaoo.kaleido.wardrobe.domain.image.chain.handler.ImageOptimizationHandler;
import com.xiaoo.kaleido.wardrobe.domain.image.chain.handler.MetadataExtractionHandler;
import com.xiaoo.kaleido.wardrobe.domain.image.chain.handler.ValidationHandler;
import com.xiaoo.kaleido.wardrobe.domain.image.context.ImageProcessingContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片处理责任链构建器
 * <p>
 * 负责构建和管理图片处理的责任链
 *
 * @author ouyucheng
 * @date 2026/1/23
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ImageProcessingChainBuilder {
    
    private final ValidationHandler validationHandler;
    private final MetadataExtractionHandler metadataExtractionHandler;
    private final ImageOptimizationHandler imageOptimizationHandler;
    
    /**
     * 责任链头节点
     */
    private ImageProcessingHandler chainHead;
    
    /**
     * 所有处理器列表
     */
    private final List<ImageProcessingHandler> allHandlers = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        buildChain();
        log.info("图片处理责任链构建完成，处理器数量: {}", allHandlers.size());
    }
    
    /**
     * 构建责任链
     */
    private void buildChain() {
        // 清空现有处理器
        allHandlers.clear();
        
        // 添加处理器到列表
        allHandlers.add(validationHandler);
        allHandlers.add(metadataExtractionHandler);
        allHandlers.add(imageOptimizationHandler);
        
        // 构建链式关系
        for (int i = 0; i < allHandlers.size() - 1; i++) {
            allHandlers.get(i).setNext(allHandlers.get(i + 1));
        }
        
        // 设置头节点
        chainHead = allHandlers.get(0);
        
        log.debug("责任链构建完成，头节点: {}", chainHead.getClass().getSimpleName());
    }
    
    /**
     * 获取责任链头节点
     */
    public ImageProcessingHandler getChain() {
        return chainHead;
    }
    
    /**
     * 执行图片处理
     *
     * @param context 图片处理上下文
     */
    public void process(ImageProcessingContext context) {
        if (chainHead == null) {
            log.error("责任链未初始化");
            context.setError("图片处理责任链未初始化");
            return;
        }
        
        log.debug("开始执行图片处理责任链");
        chainHead.handle(context);
        log.debug("图片处理责任链执行完成，结果: {}", context.getResult().isSuccess() ? "成功" : "失败");
    }
    
    /**
     * 动态添加处理器到链中
     *
     * @param handler 要添加的处理器
     * @param position 插入位置（-1表示末尾）
     */
    public void addHandler(ImageProcessingHandler handler, int position) {
        if (handler == null) {
            log.warn("尝试添加空的处理器");
            return;
        }
        
        if (position < 0 || position >= allHandlers.size()) {
            // 添加到末尾
            allHandlers.add(handler);
        } else {
            allHandlers.add(position, handler);
        }
        
        // 重新构建链
        buildChain();
        log.info("添加处理器成功: {}, 新处理器数量: {}", handler.getClass().getSimpleName(), allHandlers.size());
    }
    
    /**
     * 移除处理器
     *
     * @param handlerClass 要移除的处理器类
     */
    public void removeHandler(Class<? extends ImageProcessingHandler> handlerClass) {
        boolean removed = allHandlers.removeIf(handler -> handler.getClass().equals(handlerClass));
        
        if (removed) {
            buildChain();
            log.info("移除处理器成功: {}, 剩余处理器数量: {}", handlerClass.getSimpleName(), allHandlers.size());
        } else {
            log.warn("未找到要移除的处理器: {}", handlerClass.getSimpleName());
        }
    }
    
    /**
     * 获取所有处理器信息
     */
    public List<String> getHandlerInfo() {
        List<String> info = new ArrayList<>();
        for (ImageProcessingHandler handler : allHandlers) {
            info.add(handler.getClass().getSimpleName());
        }
        return info;
    }
}
