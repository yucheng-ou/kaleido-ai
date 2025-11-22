package com.xiaoo.kaleido.auth.trigger.controller;

import com.xiaoo.kaleido.api.user.IUserOperateFacadeService;
import com.xiaoo.kaleido.api.user.request.UserRegisterRequest;
import com.xiaoo.kaleido.api.user.response.UserBasicInfoVO;
import com.xiaoo.kaleido.base.exception.BizErrorCode;
import com.xiaoo.kaleido.base.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ouyucheng
 * @date 2025/11/20
 * @description
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthController {

    @DubboReference(version = "1.0.0")
    private IUserOperateFacadeService userOperateFacadeService;


    @Operation(summary = "用户注册", description = "通过手机号和验证码注册新用户")
    @PostMapping("/register")
    public Result<UserBasicInfoVO> register(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {

        //TODO 验证码校验
        try {
            return userOperateFacadeService.register(userRegisterRequest);
        } catch (Exception e) {
            log.error("用户注册系统异常，手机号：{}", userRegisterRequest.getTelephone(), e);
            return Result.error(BizErrorCode.UNKNOWN_ERROR);
        }

    }
}
