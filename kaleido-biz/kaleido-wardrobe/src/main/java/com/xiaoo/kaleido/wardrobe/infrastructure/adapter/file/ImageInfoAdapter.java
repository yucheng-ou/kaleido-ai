package com.xiaoo.kaleido.wardrobe.infrastructure.adapter.file;

import com.xiaoo.kaleido.api.wardrobe.command.ClothingImageInfoCommand;
import com.xiaoo.kaleido.api.wardrobe.command.LocationImageInfoCommand;
import com.xiaoo.kaleido.api.wardrobe.command.OutfitImageInfoCommand;
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
            ClothingImageInfoCommand imageInfo) {
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
            ClothingImageInfoCommand imageInfo) {
        return new ImageInfoAdapter(
                imageInfo.getPath(),
                imageInfo.getImageOrder(),
                imageInfo.getIsPrimary()
        );
    }
    
    /**
     * 从位置创建命令的ImageInfo创建适配器
     */
    public static ImageInfoAdapter fromLocationImageInfo(LocationImageInfoCommand imageInfo) {
        return new ImageInfoAdapter(
                imageInfo.getPath(),
                imageInfo.getImageOrder(),
                imageInfo.getIsPrimary()
        );
    }

    
    /**
     * 从穿搭创建命令的ImageInfo创建适配器
     */
    public static ImageInfoAdapter fromOutfitImageInfo(OutfitImageInfoCommand imageInfo) {
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
