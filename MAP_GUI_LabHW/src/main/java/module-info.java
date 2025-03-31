module interpreter.map_gui_labhw {
    requires javafx.controls;
    requires javafx.fxml;


    opens interpreter.map_gui_labhw to javafx.fxml;
    exports interpreter.map_gui_labhw;
}