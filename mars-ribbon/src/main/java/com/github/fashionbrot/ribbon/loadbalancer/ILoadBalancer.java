package com.github.fashionbrot.ribbon.loadbalancer;

import com.github.fashionbrot.ribbon.Server;
import com.github.fashionbrot.ribbon.ping.IPing;
import com.github.fashionbrot.ribbon.rule.IRule;

import java.util.List;


public interface ILoadBalancer {



    void addServers(List<Server> newServers);

    void setServer(String serverAddress,String healthUrl);

    void setServer(String serverAddress);

    List<Server> getAllServers();

    Server chooseServer();

    void setRule(IRule rule);

    IRule getRule();

    void setPing(IPing ping);

    IPing getPing();
}
