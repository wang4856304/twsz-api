package com.twsz.quarzt;

import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class SchedulerConfig {

    @Bean("scheduler")
    public Scheduler getScheduler() {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        // 覆盖已存在的任务
        bean.setOverwriteExistingJobs(true);
        // 延时启动定时任务，避免系统未完全启动却开始执行定时任务的情况
        bean.setStartupDelay(15);
        Scheduler scheduler = bean.getScheduler();
        return scheduler;
    }
}
