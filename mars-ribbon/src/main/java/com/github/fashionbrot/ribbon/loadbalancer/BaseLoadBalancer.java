package com.github.fashionbrot.ribbon.loadbalancer;

import com.github.fashionbrot.ribbon.Server;
import com.github.fashionbrot.ribbon.ping.IPing;
import com.github.fashionbrot.ribbon.ping.PingUrl;
import com.github.fashionbrot.ribbon.rule.IRule;
import com.github.fashionbrot.ribbon.rule.RoundRobinRule;
import com.github.fashionbrot.util.CollectionUtil;
import com.github.fashionbrot.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


@Slf4j
public class BaseLoadBalancer implements ILoadBalancer {

    protected volatile List<Server> allServerList = Collections.synchronizedList(new ArrayList<Server>());

    protected ReadWriteLock allServerLock = new ReentrantReadWriteLock();

    protected IRule rule = new RoundRobinRule();

    protected IPing ping = new PingUrl();

    @Override
    public void addServers(List<Server> newServers) {
        if (CollectionUtil.isNotEmpty(newServers)){
            ArrayList<Server> newList = new ArrayList<Server>();
            newList.addAll(allServerList);
            newList.addAll(newServers);
            setServersList(newList);
        }
    }

    @Override
    public void setServer(String serverAddress) {
        setServer(serverAddress,null);
    }

    @Override
    public void setServer(String serverAddress,String healthUrl) {

        if (StringUtil.isNotEmpty(serverAddress)) {
            String[] server = serverAddress.split(",");
            List<Server> serverList = new ArrayList<>(server.length);
            for (String s : server) {
                String[] svr = s.split(":");
                int port = 80;
                if (svr.length == 2) {
                    port = StringUtil.parseInteger(svr[1], 80);
                }
                serverList.add(Server.builder()
                        .host(svr[0])
                        .port(port)
                        .path(healthUrl)
                        .build());
            }
            this.addServers(serverList);
        }
    }

    @Override
    public List<Server> getAllServers() {
        return allServerList;
    }

    @Override
    public Server chooseServer() {
        Lock lock = allServerLock.readLock();
        lock.lock();
        try {
            return rule.choose(this);
        }finally {
            lock.unlock();
        }

    }

    @Override
    public void setRule(IRule rule) {
        if (rule!=null){
            this.rule = rule;
        }
    }

    @Override
    public IRule getRule() {
        return this.rule;
    }

    @Override
    public void setPing(IPing ping) {
        if (ping!=null){
            this.ping = ping;
        }
    }

    @Override
    public IPing getPing() {
        return this.ping;
    }



    /**
     * Set the list of servers used as the server pool. This overrides existing
     * server list.
     */
    public void setServersList(List<Server> lsrv) {
        Lock writeLock = allServerLock.writeLock();

        writeLock.lock();
        try {
            ArrayList<Server> allServers = new ArrayList<Server>();
            for (Server server : lsrv) {
                if (server == null) {
                    continue;
                }
                allServers.add(server);
            }

            // This will reset readyToServe flag to true on all servers
            // regardless whether
            // previous priming connections are success or not
            allServerList = allServers;
        } finally {
            writeLock.unlock();
        }
    }

}
