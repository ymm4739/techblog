package com.zhumingbei.techblog.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Data
@Slf4j
@Configuration
@ConfigurationProperties("zookeeper")
public class ZooKeeperConfig {
    private static String address;
    private static int timeout;
    private final static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static ZooKeeper zkClient() {
        ZooKeeper zooKeeperClient = null;
        try {
            zooKeeperClient = new ZooKeeper(address, timeout, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
                        log.debug("zookeeper client is created, session is established");
                        countDownLatch.countDown();
                    }
                }
            });
        }catch (IOException e) {
            log.error("Error: zkClient is not created, " + e.getMessage());
        }
        try {
            countDownLatch.await();
        }catch (Exception e) {

        }
        return zooKeeperClient;
    }
}
