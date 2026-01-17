package com.xiaoo.kaleido.wardrobe.application.command;

/**
 * 基础图片信息接口
 * <p>
 * 定义所有图片信息类必须实现的基本方法
 * 用于统一处理不同领域的图片信息
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
public interface BasicImageInfo {
    
    /**
     * 获取图片路径（在MinIO中的文件路径）
     */
    String getPath();
    
    /**
     * 获取图片排序序号
     */
    Integer getImageOrder();
    
    /**
     * 获取是否为主图
     */
    Boolean getIsPrimary();
}
