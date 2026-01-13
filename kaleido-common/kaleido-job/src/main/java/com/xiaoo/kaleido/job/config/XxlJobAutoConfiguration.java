package com.xiaoo.kaleido.job.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@Import({MyXxlJobExecutor.class})
@AutoConfiguration
public class XxlJobAutoConfiguration {
}
