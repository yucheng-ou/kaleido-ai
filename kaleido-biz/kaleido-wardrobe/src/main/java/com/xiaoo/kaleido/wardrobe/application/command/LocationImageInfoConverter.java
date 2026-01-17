package com.xiaoo.kaleido.wardrobe.application.command;

import com.xiaoo.kaleido.api.wardrobe.command.CreateLocationWithImagesCommand;
import com.xiaoo.kaleido.api.wardrobe.command.UpdateLocationCommand;

/**
 * 位置图片信息转换器
 * <p>
 * 使用模板方法模式转换位置相关的图片信息
 * 消除LocationCommandService中的重复转换代码
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
public class LocationImageInfoConverter {
    
    /**
     * 创建位置命令的图片信息转换器
     */
    public static class CreateCommandConverter 
            extends ImageInfoConverterTemplate<CreateLocationWithImagesCommand.ImageInfo, LocationCommandService.LocationImageInfo> {
        
        @Override
        protected LocationCommandService.LocationImageInfo convertSingle(CreateLocationWithImagesCommand.ImageInfo source) {
            LocationCommandService.LocationImageInfo info = new LocationCommandService.LocationImageInfo();
            info.setPath(source.getPath());
            info.setImageOrder(source.getImageOrder());
            info.setIsPrimary(source.getIsPrimary());
            return info;
        }
    }
    
    /**
     * 更新位置命令的图片信息转换器
     */
    public static class UpdateCommandConverter 
            extends ImageInfoConverterTemplate<UpdateLocationCommand.ImageInfo, LocationCommandService.LocationImageInfo> {
        
        @Override
        protected LocationCommandService.LocationImageInfo convertSingle(UpdateLocationCommand.ImageInfo source) {
            LocationCommandService.LocationImageInfo info = new LocationCommandService.LocationImageInfo();
            info.setPath(source.getPath());
            info.setImageOrder(source.getImageOrder());
            info.setIsPrimary(source.getIsPrimary());
            return info;
        }
    }
    
    /**
     * 便捷方法：转换创建命令的图片信息
     */
    public static java.util.List<LocationCommandService.LocationImageInfo> convertCreateCommandImages(
            java.util.List<CreateLocationWithImagesCommand.ImageInfo> sourceImages) {
        return new CreateCommandConverter().convert(sourceImages);
    }
    
    /**
     * 便捷方法：转换更新命令的图片信息
     */
    public static java.util.List<LocationCommandService.LocationImageInfo> convertUpdateCommandImages(
            java.util.List<UpdateLocationCommand.ImageInfo> sourceImages) {
        return new UpdateCommandConverter().convert(sourceImages);
    }
}
