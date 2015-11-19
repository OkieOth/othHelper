/*
Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements.
See the NOTICE file distributed with this work for additional information regarding copyright ownership.  
The ASF licenses this file to you under the Apache License, Version 2.0 (the "License"); 
you may not use this file except in compliance with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
specific language governing permissions and limitations under the License.
 */
package de.othsoft.helper.jetty;

import de.othsoft.helper.base.Identifier;
import static java.lang.Thread.sleep;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author eiko
 */
public class StopJettyTimerThread extends Thread {
    private Server server;
    private long secondsToSleep;

    public StopJettyTimerThread(Server server, long secondsToSleep) {        
        this.server = server;
        this.secondsToSleep = secondsToSleep;
    }

    public void run() {
        try {
            logger.info("<<{}>> will stop server after timeout of {} seconds", Identifier.getInst().getName(), secondsToSleep);
            sleep(secondsToSleep * 1000);
        } catch (InterruptedException e) {
            logger.error("<<{}>> {} is thrown", Identifier.getInst().getName(), e.getClass());
        }
        try {
            logger.info("<<{}>> stop server regular after timeout", Identifier.getInst().getName());
            if (server != null) {
                server.stop();
            }
        } catch (Exception e) {
            logger.error("<<{}>> {} is thrown while stop server", Identifier.getInst().getName(), e.getClass());
        }
    }

    static Logger logger = LoggerFactory.getLogger(StopJettyTimerThread.class);
}

