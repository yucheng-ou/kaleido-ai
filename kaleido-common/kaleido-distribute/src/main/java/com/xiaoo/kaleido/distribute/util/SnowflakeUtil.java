package com.xiaoo.kaleido.distribute.util;

import cn.hutool.core.util.IdUtil;
import com.xiaoo.kaleido.distribute.worker.WorkerIdHolder;

/**
 * @author ouyucheng
 * @date 2025/12/15
 * @description 雪花算法生成工具
 */
public class SnowflakeUtil {

    //生成有一个信息雪花算法id
    public static String newSnowflakeId() {
        return IdUtil.getSnowflake(WorkerIdHolder.WORKER_ID).nextIdStr();
    }
}
