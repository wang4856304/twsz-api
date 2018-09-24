package com.twsz.utils;

import com.twsz.quarzt.ScheduledTasks;
import com.twsz.task.Task;
import com.twsz.zk.ZookeeperClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class SpringContextRefreshUtil implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ZookeeperClient zookeeperClient;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            //ZookeeperClient zookeeperClient = SpringContextUtil.getBean("zookeeperClient", ZookeeperClient.class);
            Task scheduledTasks = SpringContextUtil.getBean("scheduledTasks", ScheduledTasks.class);
            zookeeperClient.taskExecute(scheduledTasks);
        }
    }
}
