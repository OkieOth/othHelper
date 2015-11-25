/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.othsoft.helper.jetty

import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import static org.junit.Assert.*
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.eclipse.jetty.server.Handler
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.AbstractHandler
import org.eclipse.jetty.server.Request;

/**
 *
 * @author eiko
 */
class ListenTests {

    public ListenTests() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * functions execute a netstat and search in output for the port to listen
     */
    public static boolean doListen(regExpPort,boolean listen) {
        boolean ret = false;
        'netstat -nat'.execute().in.eachLine {
            if ( it ==~ regExpPort ) {
                if (listen) {
                    ret = it ==~ /.*\sLISTEN\s*$/;
                }
                else
                    ret = true;
            }
        }
        return ret;
    }

    public static void startServerOnPortAndCheck(String portToListen,String addrToListen,Handler handler) {
        // test if this port is already used
        def regExpStr = ".*:$portToListen\\s.*";
        assertFalse ("port $portToListen already in use", doListen (regExpStr,false));
        Server server = EmbeddedJettyMainFuncs.buildServer(portToListen,addrToListen,handler);
        boolean threadRuns = false;
        Thread.start {
            threadRuns = true;
            EmbeddedJettyMainFuncs.runServer(server);
            threadRuns = false;
        };
        Thread.sleep(1000);
        // now a service listen on port 9090
        def regExpStr2
        if (addrToListen!=null) {
            def preparedAddrToListen = addrToListen.replaceAll('\\.','\\.');
            regExpStr2 = ".*\\s$preparedAddrToListen:$portToListen\\s.*";
        }
        else {
            regExpStr2 = ".*:::$portToListen\\s.*";
        }
        assertTrue ("can not find server listen on port $portToListen",doListen (regExpStr2,true));
        server.stop();
        while (threadRuns) {
            Thread.sleep(10);
        }
    }

    
    @Test
    public void test9090OnAllAdresses() {
        startServerOnPortAndCheck('9090',null,new TestHandler());
    }

    @Test
    public void test9092OnAllAdresses() {
        startServerOnPortAndCheck('9092',null,new TestHandler());
    }

    @Test
    public void test9095OnLocalhostAdresses() {
        startServerOnPortAndCheck('9095','127.0.0.1',new TestHandler());
    }
}

class TestHandler extends AbstractHandler {
    public void handle(String target,Request baseRequest,HttpServletRequest request,HttpServletResponse response) 
        throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        response.getWriter().println("<h1>I'm a test :-)</h1>");
    }
}
