package com.xiaoo.kaleido.user.trigger.controller;

import com.xiaoo.kaleido.api.user.IUserOperateFacadeService;
import com.xiaoo.kaleido.api.user.response.UserOperateVo;
import com.xiaoo.kaleido.base.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理控制器
 * 
 * @author ouyucheng
 * @date 2025/11/20
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserOperateFacadeService userOperateFacadeService;

    @Operation(summary = "获取用户信息", description = "根据用户ID查询用户详情")
    @GetMapping("/{userId}")
    public Result<UserOperateVo> getById(@PathVariable Long userId) {
        return userOperateFacadeService.getById(userId);
    }

}
