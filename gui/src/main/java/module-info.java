import net.clementlevallois.javafxtest.logic.ReturnText;

module net.clementlevallois.javafxtest.gui {
    requires javafx.controls;
    requires net.clementlevallois.javafxtest.logic;
    exports net.clementlevallois.javafxtest.gui;
    
    uses ReturnText;
}


