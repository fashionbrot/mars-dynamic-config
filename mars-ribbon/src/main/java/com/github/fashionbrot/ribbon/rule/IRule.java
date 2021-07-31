package com.github.fashionbrot.ribbon.rule;


import com.github.fashionbrot.ribbon.Server;
import com.github.fashionbrot.ribbon.loadbalancer.ILoadBalancer;


public interface IRule {

    /**
     * Choose a server from load balancer.
     * @return
     */
    Server choose(ILoadBalancer lb);

}
