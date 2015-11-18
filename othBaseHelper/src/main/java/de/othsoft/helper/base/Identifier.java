/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.othsoft.helper.base;

/**
 *
 * @author eiko
 */
public class Identifier {
    private static Identifier inst;
    private final String nameStr;
    
    private Identifier(Class mainClass) {
        nameStr = mainClass.getName();
    }
    
    private Identifier(Class mainClass,String ext) {
        nameStr = mainClass.getName()+"_"+ext;        
    }
    
    public static void init(Class mainClass) {
        inst = new Identifier(mainClass);
    }
    
    public static void init(Class mainClass,String ext) {
        inst = new Identifier(mainClass,ext);
    }

    public static Identifier getInst() {
        return inst;
    }
    
    public String getName() {
        return nameStr;
    }
}
