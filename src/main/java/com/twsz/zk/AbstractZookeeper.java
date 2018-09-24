package com.twsz.zk;

import com.twsz.exception.BusinessException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public abstract class AbstractZookeeper {
    private static Log log = LogFactory.getLog(AbstractZookeeper.class);

    protected static final int SESSION_TIMEOUT = 10000;//会话失效时间

    protected ZooKeeper zooKeeper;

    protected CountDownLatch countDownLatch =new CountDownLatch(1);


    public void connect(String host, Watcher watcher) {
        try {
            zooKeeper = new ZooKeeper(host, SESSION_TIMEOUT, watcher);
            countDownLatch.await();
            log.info("connect zookeeper success");
        }
        catch (Exception e) {
            log.error("connect zookeeper fail", e);
            throw new BusinessException("connect zookeeper fail", e);
        }
    }

    public String create(String path, byte[] data, CreateMode createMode) {
        try {
            String node = zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, createMode);
            return node;
        }
        catch (Exception e) {
            log.error("create node fail", e);
            throw new BusinessException("create node fail", e);
        }
    }

    public boolean exists(String path, boolean watch) {
        try {
            Stat stat = zooKeeper.exists(path, watch);
            if (stat != null) {
                return true;
            }
        }
        catch (Exception e) {
            log.error("exists node fail", e);
            throw new BusinessException("exists node fail", e);
        }
        return false;
    }

    public boolean exists(String path, Watcher watcher) {
        try {
            Stat stat = zooKeeper.exists(path, watcher);
            if (stat != null) {
                return true;
            }
        }
        catch (Exception e) {
            log.error("exists node fail", e);
            throw new BusinessException("exists node fail", e);
        }
        return false;
    }

    public List<String> getChild(String path) {
        try {
            List<String> childList = zooKeeper.getChildren(path , null);
            return childList;
        }
        catch (Exception e) {
            log.error("get child node fail", e);
            throw new BusinessException("get child node fail", e);
        }
    }

    public String getData(String path) {
        try {
            if (exists(path, null)) {
                Stat stat = new Stat();
                byte[] data = zooKeeper.getData(path, null, stat);
                if (stat != null) {
                    return new String(data);
                }
            }
        }
        catch (Exception e) {
            log.error("get node data fail, path=" + path, e);
            throw new BusinessException("get node data fail,path=" + path, e);
        }
        return null;
    }

    public void close() {
        try {
            zooKeeper.close();
        }
        catch (Exception e) {
            log.error("close zookeeper fail", e);
            throw new BusinessException("close zookeeper fail", e);
        }
    }


}
