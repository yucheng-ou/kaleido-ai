package com.xiaoo.kaleido.wardrobe.application.command;

import lombok.Data;

/**
 * 图片信息适配器
 * <p>
 * 用于适配不同来源的图片信息，使其实现BasicImageInfo接口
 * 避免直接修改API模块中的类
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Data
public class ImageInfoAdapter implements BasicImageInfo {
    
    private final String path;
    private final Integer imageOrder;
    private final Boolean isPrimary;
    
    /**
     * 从服装创建命令的ImageInfo创建适配器
     */
    public static ImageInfoAdapter fromClothingImageInfo(
            com.xiaoo.kaleido.api.wardrobe.command.CreateClothingWithImagesCommand.ImageInfo imageInfo) {
        return new ImageInfoAdapter(
                imageInfo.getPath(),
                imageInfo.getImageOrder(),
                imageInfo.getIsPrimary()
        );
    }
    
    /**
     * 从服装更新命令的ImageInfo创建适配器
     */
    public static ImageInfoAdapter fromClothingUpdateImageInfo(
            com.xiaoo.kaleido.api.wardrobe.command.UpdateClothingCommand.ImageInfo imageInfo) {
        return new ImageInfoAdapter(
                imageInfo.getPath(),
                imageInfo.getImageOrder(),
                imageInfo.getIsMain()
        );
    }
    
    /**
     * 从位置创建命令的ImageInfo创建适配器
     */
    public static ImageInfoAdapter fromLocationImageInfo(
            com.xiaoo.kaleido.api.wardrobe.command.CreateLocationWithImagesCommand.ImageInfo imageInfo) {
        return new ImageInfoAdapter(
                imageInfo.getPath(),
                imageInfo.getImageOrder(),
                imageInfo.getIsPrimary()
        );
    }
    
    /**
     * 从位置更新命令的ImageInfo创建适配器
     */
    public static ImageInfoAdapter fromLocationUpdateImageInfo(
            com.xiaoo.kaleido.api.wardrobe.command.UpdateLocationCommand.ImageInfo imageInfo) {
        return new ImageInfoAdapter(
                imageInfo.getPath(),
                imageInfo.getImageOrder(),
                imageInfo.getIsPrimary()
        );
    }
    
    /**
     * 从穿搭创建命令的ImageInfo创建适配器
     */
    public static ImageInfoAdapter fromOutfitImageInfo(
            com.xiaoo.kaleido.api.wardrobe.command.CreateOutfitWithClothingsCommand.ImageInfo imageInfo) {
        return new ImageInfoAdapter(
                imageInfo.getPath(),
                imageInfo.getImageOrder(),
                imageInfo.getIsPrimary()
        );
    }
    
    /**
     * 从穿搭更新命令的ImageInfo创建适配器
     */
    public static ImageInfoAdapter fromOutfitUpdateImageInfo(
            com.xiaoo.kaleido.api.wardrobe.command.UpdateOutfitCommand.ImageInfo imageInfo) {
        return new ImageInfoAdapter(
                imageInfo.getPath(),
                imageInfo.getImageOrder(),
                imageInfo.getIsPrimary()
        );
    }
    
    /**
     * 从LocationCommandService的内部类创建适配器
     */
    public static ImageInfoAdapter fromLocationCommandImageInfo(
            LocationCommandService.LocationImageInfo imageInfo) {
        return new ImageInfoAdapter(
                imageInfo.getPath(),
                imageInfo.getImageOrder(),
                imageInfo.getIsPrimary()
        );
    }
    
    private ImageInfoAdapter(String path, Integer imageOrder, Boolean isPrimary) {
        this.path = path;
        this.imageOrder = imageOrder;
        this.isPrimary = isPrimary;
    }
    
    @Override
    public String getPath() {
        return path;
    }
    
    @Override
    public Integer getImageOrder() {
        return imageOrder;
    }
    
    @Override
    public Boolean getIsPrimary() {
        return isPrimary;
    }
}
