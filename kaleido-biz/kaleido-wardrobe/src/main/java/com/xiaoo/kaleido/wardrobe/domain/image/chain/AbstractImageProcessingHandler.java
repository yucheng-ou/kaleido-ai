package com.xiaoo.kaleido.wardrobe.domain.image.chain;

import com.xiaoo.kaleido.wardrobe.domain.image.context.ImageProcessingContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 抽象图片处理处理器
 * <p>
 * 责任链模式中的抽象处理器，提供通用实现
 *
 * @author ouyucheng
 * @date 2026/1/23
 */
@Slf4j
public abstract class AbstractImageProcessingHandler implements ImageProcessingHandler {
    
    /**
     * 下一个处理器
     */
    protected ImageProcessingHandler next;
    
    @Override
    public void setNext(ImageProcessingHandler next) {
        this.next = next;
    }
    
    @Override
    public ImageProcessingHandler getNext() {
        return next;
    }
    
    /**
     * 处理图片的模板方法
     *
     * @param context 图片处理上下文
     */
    @Override
    public void handle(ImageProcessingContext context) {
        // 如果已经发生错误，跳过处理
        if (context.hasError()) {
            log.debug("上下文已存在错误，跳过处理器: {}", getClass().getSimpleName());
            handleNext(context);
            return;
        }
        
        try {
            // 执行具体处理逻辑
            doHandle(context);
            
            // 如果处理成功且没有错误，继续下一个处理器
            if (!context.hasError()) {
                handleNext(context);
            }
        } catch (Exception e) {
            // 处理异常
            log.error("图片处理失败，处理器: {}, 错误: ", getClass().getSimpleName(), e);
            context.setError("处理器 " + getClass().getSimpleName() + " 处理失败: " + e.getMessage());
        }
    }
    
    /**
     * 执行具体的处理逻辑
     *
     * @param context 图片处理上下文
     */
    protected abstract void doHandle(ImageProcessingContext context);
    
    /**
     * 调用下一个处理器
     *
     * @param context 图片处理上下文
     */
    protected void handleNext(ImageProcessingContext context) {
        if (next != null) {
            next.handle(context);
        }
    }
    
    /**
     * 获取处理器名称（用于日志）
     */
    protected String getHandlerName() {
        return getClass().getSimpleName();
    }
}
