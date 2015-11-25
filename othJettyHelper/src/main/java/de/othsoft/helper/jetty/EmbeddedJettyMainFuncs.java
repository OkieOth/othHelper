/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.othsoft.helper.jetty;

import de.othsoft.helper.base.Identifier;
import java.net.InetSocketAddress;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
/**
 *
 * @author eiko
 */
public class EmbeddedJettyMainFuncs {
    public static Server buildServer(String portStr, String addressStr, Handler handler) throws Exception {
        return buildServer(portStr,addressStr,handler,null);
    }
    /**
     * extracted for testing
     */
    public static Server buildServer(String portStr, String addressStr, Handler handler,Logger logger) throws Exception {
        int port = Integer.parseInt(portStr);
        InetSocketAddress address = addressStr != null ? new InetSocketAddress(addressStr, port)
                : new InetSocketAddress(port);
        Server server = new Server(address);
        server.setHandler(handler);
        if (logger!=null) {
            if (addressStr != null) {
                logger.info("<<{}>> bind on address {}, listen on port {}", Identifier.getInst().getName(), addressStr, portStr);
            } else {
                logger.info("<<{}>> bind on all adresses, listen on port {}", Identifier.getInst().getName(), portStr);
            }
        }
        return server;
    }

    public static void runServer(Server server) throws Exception {
        runServer(server,null);
    }
    
    public static void runServer(Server server, Logger logger) throws Exception {
        server.start();
        if (logger!=null) {
            logger.info("<<{}>> server started", Identifier.getInst().getName());
        }
        server.join();
    }
    
}
