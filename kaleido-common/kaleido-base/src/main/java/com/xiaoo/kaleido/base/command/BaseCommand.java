package com.xiaoo.kaleido.base.command;

import java.io.Serial;
import java.io.Serializable;

/**
 * 基础命令类
 * 所有通过 Dubbo RPC 传输的命令类应继承此类
 *
 * @author ouyucheng
 * @date 2026/1/6
 */
public abstract class BaseCommand implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    // 可以添加通用方法，如验证方法
    public void validate() {
        // 基础验证，子类可重写
    }
}
