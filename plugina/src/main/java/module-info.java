/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

module net.clementlevallois.javafxtest.plugin.a {
    requires net.clementlevallois.javafxtest.logic;
    requires guava;

    provides net.clementlevallois.javafxtest.logic.ReturnText
            with net.clementlevallois.javafxtest.plugin.CommentFromPlugin;

    exports net.clementlevallois.javafxtest.plugin;

}
