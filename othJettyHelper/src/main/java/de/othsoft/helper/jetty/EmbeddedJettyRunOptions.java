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
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author eiko
 */
public class EmbeddedJettyRunOptions {
    private final Options options;
    private CommandLine cmd;
    private String mainClassName;
    
    private boolean preventExit=false;
    
    public EmbeddedJettyRunOptions(String mainClassName) {
        options = createOptions();
        this.mainClassName = mainClassName;
    }
    
    /**
     * only for test cases
     * @param b 
     */
    void setPreventExit(boolean b) {
        this.preventExit = b;
    }
    
    
    void printUsage() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java "+mainClassName, options);        
    }
    
    private void printUsageAndExit() {
        printUsage();
        if (!this.preventExit)
            System.exit(1);
    }

    private Options createOptions() {
        Options options = new Options();
        Option option = new Option("p", "port", true, "port the server listen on");
        option.setRequired(true);
        option.setType(Integer.class);
        options.addOption(option);
        options.addOption("a", "address", true, "the address the server is bind on (default all)");
        options.addOption("i", "identifier", true, "optional identifier for log output");
        options.addOption("f", "pidfile", true, "pid file to use");
        options.addOption("t", "time", true, "number of seconds until the server close itself");
        options.addOption("?", "help", false, "show this message");
        return options;
    }
    
    /**
     * 
     * @param args command line arguments
     */
    public void parse(String[] args) {
        try {
            CommandLineParser parser = new DefaultParser();
            cmd = parser.parse(options, args);
            if (hasOption("?"))
                printUsageAndExit();
        }
        catch(MissingOptionException|MissingArgumentException e) {
            printUsageAndExit();            
        }
        catch(Exception e) {
            logger.error("<<{}>> {}: {}", Identifier.getInst().getName(), e.getClass().getName(), e.getMessage());
            if (!this.preventExit)
                System.exit(1);
        }
    }
    
    public void ifMissOptionPrintUsageAndExit(String optionStr) {
        if (cmd==null) {
            logger.error("<<{}>> cmd is null, maybe the options are not parsed", Identifier.getInst().getName());
        }        
        if (cmd==null || (!cmd.hasOption(optionStr))) {
            logger.error("<<{}>> missing option {}, so I exit", Identifier.getInst().getName(),optionStr);
            printUsageAndExit();
        }
    }
    
    public String getValue(String optionStr) {
        if (cmd==null) {
            logger.error("<<{}>> cmd is null, maybe the options are not parsed", Identifier.getInst().getName());
        }
        return cmd==null ? null : cmd.getOptionValue(optionStr);
    }

    public boolean hasOption(String optionStr) {
        if (cmd==null) {
            logger.error("<<{}>> cmd is null, maybe the options are not parsed", Identifier.getInst().getName());
        }
        return cmd==null? false : cmd.hasOption(optionStr);
    }

    private static Logger logger = LoggerFactory.getLogger(EmbeddedJettyRunOptions.class);
}
