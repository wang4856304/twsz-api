package com.twsz.quarzt;


import com.twsz.utils.SpringContextUtil;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@DependsOn("springContextUtil")
public class ScheduledTasks {

    public void test() {
        System.out.println("1234567");
    }

    public void xxxx() {
        System.out.println("369");
    }

    @PostConstruct
    public void init() throws Exception {

        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        // 覆盖已存在的任务
        bean.setOverwriteExistingJobs(true);
        // 延时启动定时任务，避免系统未完全启动却开始执行定时任务的情况
        bean.setStartupDelay(15);
        bean.afterPropertiesSet();
        Scheduler scheduler = bean.getScheduler();

        MethodInvokingJobDetailFactoryBean detailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        ScheduledTasks scheduledTasks = SpringContextUtil.getBean("scheduledTasks");
        detailFactoryBean.setTargetObject(scheduledTasks);
        detailFactoryBean.setTargetMethod("test");
        detailFactoryBean.setConcurrent(false);
        detailFactoryBean.setName("123");
        detailFactoryBean.afterPropertiesSet();
        JobDetail jobDetail = detailFactoryBean.getObject();


        CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
        tigger.setJobDetail(jobDetail);
        tigger.setCronExpression("0/10 * * * * ?"); // 什么是否触发，Spring Scheduler Cron表达式
        tigger.setName("234");
        tigger.afterPropertiesSet();


        MethodInvokingJobDetailFactoryBean detailFactoryBean1 = new MethodInvokingJobDetailFactoryBean();
        detailFactoryBean1.setTargetObject(scheduledTasks);
        detailFactoryBean1.setTargetMethod("xxxx");
        detailFactoryBean1.setConcurrent(false);
        detailFactoryBean1.setName("321");
        detailFactoryBean1.afterPropertiesSet();
        JobDetail jobDetail1 = detailFactoryBean1.getObject();


        CronTriggerFactoryBean tigger1 = new CronTriggerFactoryBean();
        tigger1.setJobDetail(jobDetail1);
        tigger1.setCronExpression("0/5 * * * * ?"); // 什么是否触发，Spring Scheduler Cron表达式
        tigger1.setName("678");
        tigger1.afterPropertiesSet();

        scheduler.scheduleJob(jobDetail, tigger.getObject());
        scheduler.scheduleJob(jobDetail1, tigger1.getObject());
        scheduler.start();
    }
}
