package com.xiaoo.kaleido.wardrobe.domain.outfit.adapter.file;

import com.xiaoo.kaleido.api.wardrobe.command.OutfitImageInfoCommand;
import com.xiaoo.kaleido.wardrobe.domain.outfit.service.dto.OutfitImageInfoDTO;

import java.util.List;

public interface IOutfitFileService {

    List<OutfitImageInfoDTO> convertorImageInfo(List<OutfitImageInfoCommand> images);
}
