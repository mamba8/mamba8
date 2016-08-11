package com.mamba.application;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;

@ConditionalOnProperty("enable-scheduling") // 配置文件控制是否开启定时任务
@EnableScheduling
public class ApplicationScheduling {
}
