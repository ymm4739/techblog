package com.zhumingbei.techblog.common;

import com.zhumingbei.techblog.config.ZooKeeperConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@Component
@Slf4j
public class DistributedLock implements Watcher {

    private ZooKeeper zk;
    private final String locksRoot = "/lock";
    private final String type;
    private ThreadLocal<String> waitNode = new ThreadLocal<>();
    private ThreadLocal< String>  lockNode = new ThreadLocal<>();
    private CountDownLatch latch;
    private CountDownLatch connectedLatch = new CountDownLatch(1);
    private int sessionTimeout = 30000;

    public DistributedLock() {
        try {
            type = "X";
            String address = "127.0.0.1:2182";
            zk = new ZooKeeper(address, sessionTimeout, this);
            connectedLatch.await();
        } catch (IOException e) {
            throw new LockException(e);
        }  catch (InterruptedException e) {
            throw new LockException(e);
        }
    }

    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            connectedLatch.countDown();
            // return;
        }

        if (this.latch != null) {
            this.latch.countDown();
        }
    }

    public void acquire() {
        try {
            if (this.tryLock()) {
                return;
            } else {
                waitForLock(waitNode.get(), sessionTimeout);
            }
        } catch (KeeperException e) {
            throw new LockException(e);
        } catch (InterruptedException e) {
            throw new LockException(e);
        }
    }

    public boolean tryLock() {
        try {

            lockNode.set( zk.create(locksRoot + "/" + type , new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL));

            // 看看刚创建的节点是不是最小的节点
            List<String> locks = zk.getChildren(locksRoot, false);
            Collections.sort(locks);
            log.debug("{}, {}", lockNode.get(), locks);
            if(lockNode.get().equals(locksRoot+"/"+ locks.get(0))){
                //如果是最小的节点,则表示取得锁
                log.debug("获取到锁, {}, {}", lockNode.get(), Thread.currentThread());
                return true;
            }

            //如果不是最小的节点，找到比自己小1的节点
            int previousLockIndex = -1;
            for(int i = 0; i < locks.size(); i++) {
                if(lockNode.get().equals(locksRoot + "/" + locks.get(i))) {
                    previousLockIndex = i - 1;
                    break;
                }
            }

            this.waitNode.set(locks.get(previousLockIndex));
            log.debug("等待前一个节点被删除, {}, {}", waitNode.get(), lockNode.get());
        } catch (KeeperException e) {
            throw new LockException(e);
        } catch (InterruptedException e) {
            throw new LockException(e);
        }
        return false;
    }

    private boolean waitForLock(String waitNode, long waitTime) throws InterruptedException, KeeperException {
        Stat stat = zk.exists(locksRoot + "/" + waitNode, true);
        if (stat != null) {
            this.latch = new CountDownLatch(1);
            this.latch.await(waitTime, TimeUnit.MILLISECONDS);
            this.waitNode.remove();
            this.latch = null;
        }
        log.debug("获取到锁, {}, {}", lockNode.get(), Thread.currentThread());
        return true;
    }

    public void release() {
        try {

            log.debug("unlock {}, {}", lockNode.get(), Thread.currentThread());
            zk.delete(lockNode.get(), -1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    public class LockException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public LockException(String e) {
            super(e);
        }

        public LockException(Exception e) {
            super(e);
        }
    }
}
