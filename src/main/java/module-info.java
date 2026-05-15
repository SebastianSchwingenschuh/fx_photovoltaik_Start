module at.htl.photovoltaik {
    requires javafx.controls;
    requires javafx.fxml;


    opens at.htl.photovoltaic to javafx.fxml;
    exports at.htl.photovoltaic;
    exports at.htl.photovoltaic.controller;
    opens at.htl.photovoltaic.controller to javafx.fxml;
}
