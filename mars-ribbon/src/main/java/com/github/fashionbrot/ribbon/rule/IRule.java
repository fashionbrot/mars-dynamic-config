package com.github.fashionbrot.ribbon.rule;


import com.github.fashionbrot.ribbon.Server;
import com.github.fashionbrot.ribbon.loadbalancer.ILoadBalancer;


public interface IRule {

    /**
     * choose a server  from load balancer
     * @param lb
     * @return Server
     */
    Server choose(ILoadBalancer lb);
}
