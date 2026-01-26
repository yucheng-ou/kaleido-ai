package com.xiaoo.kaleido.wardrobe.domain.image.chain;

import com.xiaoo.kaleido.wardrobe.domain.image.context.ImageProcessingContext;

/**
 * 图片处理处理器接口
 * <p>
 * 责任链模式中的处理器接口
 *
 * @author ouyucheng
 * @date 2026/1/23
 */
public interface ImageProcessingHandler {
    
    /**
     * 处理图片
     *
     * @param context 图片处理上下文
     */
    void handle(ImageProcessingContext context);
    
    /**
     * 设置下一个处理器
     *
     * @param next 下一个处理器
     */
    void setNext(ImageProcessingHandler next);
    
    /**
     * 获取下一个处理器
     *
     * @return 下一个处理器
     */
    ImageProcessingHandler getNext();
}
