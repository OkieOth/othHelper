/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.othsoft.helper.jetty;

import de.othsoft.helper.base.Identifier;
import java.util.Date;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author eiko
 */
public class Test_StopJettyTimerThread {
    @BeforeClass
    public static void setUpClass() {
        Identifier.init(Test_StopJettyTimerThread.class);
    }

    public Test_StopJettyTimerThread() {
    }

    @Test
    public void testTimeout() {
        StopJettyTimerThread x = new StopJettyTimerThread(null, 2);
        long start = new Date().getTime();
        x.run();
        long end = new Date().getTime();
        long diff = end - start;
        
        assertTrue("wrong time to wait (2 s): "+diff,(diff>2000) && (diff<2500));
    }
}
