package com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.file;

import com.xiaoo.kaleido.api.wardrobe.command.ClothingImageInfoCommand;
import com.xiaoo.kaleido.wardrobe.domain.clothing.service.dto.ImageInfoDTO;

import java.util.List;

public interface IClothingFileService {

    List<ImageInfoDTO> convertorImageInfo(List<ClothingImageInfoCommand> images);
}
