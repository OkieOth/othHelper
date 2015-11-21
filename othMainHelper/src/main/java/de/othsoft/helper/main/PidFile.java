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
package de.othsoft.helper.main;

import de.othsoft.helper.base.Identifier;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.management.ManagementFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author eiko
 */
public class PidFile {
    private PidFile inst = null;
    private String pathToPidFile;
    
    private PidFile() {
        String identifierName = Identifier.getInst().getName();
        this.pathToPidFile = identifierName + ".pid";
    }

    private PidFile(String pidFileDirectory) {
        String identifierName = Identifier.getInst().getName();
        this.pathToPidFile = pidFileDirectory + "/" + identifierName + ".pid";
    }
    
    private PidFile(String pidFileDirectory, String pidFileName) {
        this.pathToPidFile = pidFileDirectory + "/" + pidFileName;
    }

    public static PidFile getInst() {
        return new PidFile();
    }

    public static PidFile getInst(String pidFileDirectory) {
        return new PidFile(pidFileDirectory);
    }
    
    public static PidFile getInst(String pidFileDirectory, String pidFileName) {
        return new PidFile(pidFileDirectory,pidFileName);
    }

    /**
     * this funtion stop the execution of program if the given pid file already
     * exists
     */
    public void createPidFileAndExitWhenAlreadyExist() {
        File f = new File(this.pathToPidFile);
        if (f.exists()) {
            logger.error("<<{}>> error pid file ({}) exists, does the process already running?",
                    Identifier.getInst().getName(),this.pathToPidFile);
            System.exit(1);
        }
        createFileNow(f);
    }

    /**
     * create a new pid file
     */
    public void createPidFile() {
        File f = new File(this.pathToPidFile);
        createFileNow(f);
    }
    
    private void createFileNow(File f) {
        try {
            FileOutputStream fout = new FileOutputStream(f);
            f.deleteOnExit();
            String pid = getPid();
            fout.write(pid.getBytes());
            fout.flush();
            fout.close();
        }
        catch(Exception e) {
            logger.error("<<{}>> error while create pid file ({}): {}, {}",
                    Identifier.getInst().getName(),this.pathToPidFile,e.getClass().getName(),e.getMessage());
        }        
    } 
    
    public static String getPid() {
        String pidAndStuff = ManagementFactory.getRuntimeMXBean().getName();
        int indexAt = pidAndStuff.indexOf("@");
        String pid = pidAndStuff.substring(0,indexAt);
        logger.info("<<{}>> pid={}", Identifier.getInst().getName(),pid);
        return pid;
    }
    private static Logger logger = LoggerFactory.getLogger(PidFile.class);
}
