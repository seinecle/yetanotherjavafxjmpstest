/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.javafxtest.plugin;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

/**
 *
 * @author LEVALLOIS
 */
public class TestComputingUsingGuava {

    public String doTest() {
        Multiset<String> guavaTest = HashMultiset.create();
        guavaTest.add("one");
        System.out.println("guavaTest size is: " + guavaTest.size());
        return guavaTest.toString();

    }

}