package com.xiaoo.kaleido.wardrobe.domain.location.adapter.file;

import com.xiaoo.kaleido.api.wardrobe.command.LocationImageInfoCommand;
import com.xiaoo.kaleido.wardrobe.domain.location.service.dto.LocationImageInfoDTO;

import java.util.List;

public interface ILocationFileService {

    List<LocationImageInfoDTO> convertorImageInfo(List<LocationImageInfoCommand> images);
}
