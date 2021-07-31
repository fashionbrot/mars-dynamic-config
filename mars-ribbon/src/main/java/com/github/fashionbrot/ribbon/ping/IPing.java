package com.github.fashionbrot.ribbon.ping;


import com.github.fashionbrot.ribbon.Server;


public interface IPing {

    /**
     * Checks whether the given <code>Server</code> is "alive" i.e. should be
     * considered a candidate while loadbalancing
     */
    boolean isAlive(Server server);


}
