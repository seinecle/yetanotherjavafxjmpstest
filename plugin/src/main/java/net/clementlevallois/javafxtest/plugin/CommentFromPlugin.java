/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.javafxtest.plugin;

import net.clementlevallois.javafxtest.logic.ReturnText;

/**
 *
 * @author LEVALLOIS
 */
public class CommentFromPlugin implements ReturnText {

    @Override
    public String comment() {
        return "comment from plugin!";
    }
    
}
