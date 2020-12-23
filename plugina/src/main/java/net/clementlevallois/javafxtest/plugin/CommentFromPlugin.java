/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.javafxtest.plugin;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import net.clementlevallois.javafxtest.logic.ReturnText;

/**
 *
 * @author LEVALLOIS
 */
public class CommentFromPlugin implements ReturnText {

    @Override
    public String comment() {
        try {
            //testing Guava 10 incompatibility issues, to be fixed by layers.
            Files.deleteDirectoryContents(new File("C:\\temp"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "comment from plugin A!";
    }
    
}
