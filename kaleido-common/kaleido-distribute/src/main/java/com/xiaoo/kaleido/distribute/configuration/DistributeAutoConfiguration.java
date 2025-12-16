package com.xiaoo.kaleido.distribute.configuration;

import com.xiaoo.kaleido.distribute.worker.WorkerIdHolder;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @author ouyucheng
 * @date 2025/12/15
 * @description
 */

@AutoConfiguration
@Import({WorkerIdHolder.class})
public class DistributeAutoConfiguration {

}
