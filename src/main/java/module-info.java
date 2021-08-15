module com.dvessey.inventorymanagementsystemsoftware {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.dvessey.inventorymanagementsystemsoftware to javafx.fxml;
    exports com.dvessey.inventorymanagementsystemsoftware;
}