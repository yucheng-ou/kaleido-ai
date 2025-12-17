package com.xiaoo.kaleido.user.trigger.controller;

import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.user.command.ChangeNickNameCommand;
import com.xiaoo.kaleido.user.command.UserCommandService;
import com.xiaoo.kaleido.user.query.dto.UserDTO;
import com.xiaoo.kaleido.user.query.service.UserQueryService;
import com.xiaoo.kaleido.user.trigger.controller.request.UpdateNicknameRequest;
import com.xiaoo.kaleido.user.types.exception.UserErrorCode;
import com.xiaoo.kaleido.user.types.exception.UserException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "用户管理", description = "用户相关接口")
public class UserController {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    @Operation(summary = "查询用户信息", description = "根据用户ID查询用户信息")
    @GetMapping("/{userId}")
    public Result<UserDTO> getUserById(
            @Parameter(description = "用户ID", required = true)
            @PathVariable String userId) {
        try {
            log.debug("查询用户信息，userId={}", userId);
            UserDTO userDTO = userQueryService.getUserById(userId);
            return Result.success(userDTO);
        } catch (UserException e) {
            log.warn("查询用户信息失败，userId={}, errorCode={}", userId, e.getErrorCode());
            return Result.error(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error("查询用户信息异常，userId={}", userId, e);
            return Result.error(UserErrorCode.USER_OPERATE_FAILED.getCode(), "系统异常");
        }
    }

    @Operation(summary = "修改用户昵称", description = "修改当前用户的昵称")
    @PutMapping("/{userId}/nickname")
    public Result<Boolean> updateNickname(
            @Parameter(description = "用户ID", required = true, example = "1234567890123456789")
            @PathVariable String userId,
            @Parameter(description = "操作者ID", required = true, example = "1234567890123456789")
            @Valid @RequestBody UpdateNicknameRequest request) {
        try {
            //TODO 操作人信息后续基于token解析
            String operatorId = "001";

            log.debug("修改用户昵称，userId={}, operatorId={}, request={}", userId, operatorId, request);

            // 使用新的请求体，昵称已经通过@NotBlank验证
            ChangeNickNameCommand nickNameCommand = ChangeNickNameCommand.builder()
                    .userId(userId)
                    .operatorId(operatorId)
                    .nickName(request.getNickName())
                    .build();

            userCommandService.changeNickName(nickNameCommand);

            log.info("用户昵称修改成功，userId={}, newNickname={}", userId, request.getNickName());
            return Result.success(true);
        } catch (UserException e) {
            log.warn("修改用户昵称失败，userId={}, errorCode={}", userId, e.getErrorCode());
            return Result.error(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error("修改用户昵称异常，userId={}", userId, e);
            return Result.error(UserErrorCode.USER_OPERATE_FAILED.getCode(), "系统异常");
        }
    }
}
