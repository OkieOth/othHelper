/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.othsoft.helper.base;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author eiko
 */
public class Test_Identifier {
    
    public Test_Identifier() {
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

    @Test
    public void testIdentifier() {
        Identifier.init(this.getClass());
        assertEquals("wrong Identifier name 1","Test_Identifier",Identifier.getInst().getName());
        Identifier.init(this.getClass(),"02");
        assertEquals("wrong Identifier name 2","Test_Identifier_02",Identifier.getInst().getName());
    }
}
