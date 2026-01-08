package com.xiaoo.kaleido.api.user.command;
import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 淇敼鏄电О鍛戒护
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeNickNameCommand extends BaseCommand {
    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    private String userId;
    /**
     * 新昵称
     */
    @NotBlank(message = "昵称不能为空")
    @Size(min = 2, max = 20, message = "昵称长度必须在2-20位之间")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9]+$", message = "昵称只能包含中文、英文和数字")
    private String nickName;
}
