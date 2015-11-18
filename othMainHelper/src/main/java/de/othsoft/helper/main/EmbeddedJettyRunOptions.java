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
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author eiko
 */
public class EmbeddedJettyRunOptions {
    private Options options;
    private CommandLine cmd;
    
    public EmbeddedJettyRunOptions() {
        options = createOptions();
    }
    
    public void printUsageAndExit(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java de.othserv.examples.jetty.MemcachedServer", options);
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
        options.addOption("?", "help", false, "show help");
        return options;
    }
    
    /**
     * 
     * @param args command line arguments
     * @return false if parse goes wrong
     */
    public boolean parse(String[] args) {
        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);
            return true;
        }
        catch(Exception e) {
            logger.error("<<{}>> {}: ", Identifier.getInst().getName(), e.getClass().getName(), e.getMessage());
            return false;
        }
    }
    
    public void ifMissOptionPrintUsageAndExit(String optionStr) {
        if (cmd.hasOption(optionStr)) {
                printUsageAndExit(options);
        }
    }
    
    public String getValue(String optionStr) {
        return cmd.getOptionValue(optionStr);
    }

    public boolean hasOption(String optionStr) {
        return cmd.hasOption(optionStr);
    }

    private static Logger logger = LoggerFactory.getLogger(EmbeddedJettyRunOptions.class);
}
