package com.twsz.zk;

import com.twsz.exception.BusinessException;
import com.twsz.quarzt.ScheduledTasks;
import com.twsz.task.Task;
import com.twsz.utils.NetUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.List;

@Component
public class ZookeeperClient extends AbstractZookeeper implements Watcher {
    private static Log log = LogFactory.getLog(ZookeeperClient.class);

    @Value("${zookeeper.host}")
    private String host;

    private boolean master = false;

    private static final String ROOT_PATH = "/config";
    private static final String EPHEMERAL_PATH = "/come";

    private String currentNode;

    @PostConstruct
    public void init() {
        connect(host, this);
        if (!exists(ROOT_PATH, null)) {
            create(ROOT_PATH, "123".getBytes(), CreateMode.PERSISTENT);
        }
        currentNode = create(ROOT_PATH + EPHEMERAL_PATH, NetUtil.getLocalHostLANAddress().getBytes(), CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            countDownLatch.countDown();
        }
        if (watchedEvent.getType() == Event.EventType.NodeDeleted) {
            Task task = new ScheduledTasks();
            taskExecute(task);
        }
        if (watchedEvent.getState() == Event.KeeperState.Disconnected) {
            log.error("session is disconnected, reconnect now");
            init();
        }
    }

    public void taskExecute(Task... tasks) {
        List<String> nodes = getChild(ROOT_PATH);
        nodes.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                o1 = o1.substring(EPHEMERAL_PATH.length());
                o2 = o2.substring(EPHEMERAL_PATH.length());
                int a = Integer.valueOf(o1);
                int b = Integer.valueOf(o2);
                return a < b ? -1:1;
            }
        });
        String curNode = currentNode.substring(ROOT_PATH.length() + 1);
        if (curNode.equals(nodes.get(0))) {
            log.info(NetUtil.getLocalHostLANAddress() + ":" + currentNode + " is master");
            for (Task task: tasks) {
                task.execute();
            }
        }

        for (int k = 0; k < nodes.size(); k++) {
            String node = nodes.get(k);
            if (!node.equals(nodes.get(0))) {
                exists(ROOT_PATH + "/" + nodes.get(k-1), this);
            }
        }
    }

   /* public static void main(String args[]) {




        ZookeeperClient zookeeperClient = new ZookeeperClient("192.168.88.128", 2181);
        //ZookeeperClient zookeeperClient1 = new ZookeeperClient("192.168.88.128", 2181);
        zookeeperClient.init();
        //zookeeperClient1.init();
        //List<String> childList = zookeeperClient.getChild(ROOT_PATH );

        Task task = new ScheduledTasks();

        zookeeperClient.taskExecute(task);
        //zookeeperClient1.getMasterNode(zookeeperClient.getChild(ROOT_PATH ));

        while (true) {

        }
    }*/
}
