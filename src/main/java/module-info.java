module it.polimi.ingsw.am52 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens it.polimi.ingsw.am52 to javafx.fxml;
    exports it.polimi.ingsw.am52;
}