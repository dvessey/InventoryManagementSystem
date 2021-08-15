package com.dvessey.inventorymanagementsystemsoftware;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * AddProductController class to add a product to inventory
 * @author Damon Vessey
 */
public class AddProductController implements Initializable {
    @FXML
    public TextField productId;
    @FXML
    public TextField productName;
    @FXML
    public TextField productInv;
    @FXML
    public TextField productPrice;
    @FXML
    public TextField productMax;
    @FXML
    public TextField productMin;
    @FXML
    public TableView<Part> allParts;
    @FXML
    public TableColumn allPartIdCol;
    @FXML
    public TableColumn allPartNameCol;
    @FXML
    public TableColumn allPartInvCol;
    @FXML
    public TableColumn allPartPriceCol;
    @FXML
    public TextField partSearch;
    @FXML
    public Button addPart;
    @FXML
    public TableView<Part> allAssociatedParts;
    @FXML
    public TableColumn allProductPartIdCol;
    @FXML
    public TableColumn allProductPartNameCol;
    @FXML
    public TableColumn allProductPartInvCol;
    @FXML
    public TableColumn allProductPartPriceCol;
    @FXML
    public Button removeAssociatedPart;
    @FXML
    public Button saveProduct;
    @FXML
    public Button cancelProduct;
    @FXML
    public Label invMinMaxErrorLabel;
    @FXML
    public Label invErrorLabel;
    @FXML
    public Label priceErrorLabel;
    @FXML
    public Label minMaxErrorLabel;
    @FXML
    public Label maxErrorLabel;
    @FXML
    public Label minErrorLabel;
    @FXML
    public Label blankErrorLabel;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private boolean hasErrors = false;

    /**
     * onAddPart method adds a part to a product's associated parts
     * @param actionEvent
     */
    public void onAddPart(ActionEvent actionEvent) {
        if(allParts.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No parts");
            alert.setHeaderText("Please select a part before adding");
            alert.setContentText("Click ok to confirm");
            alert.showAndWait();
        } else {
            Part thePart = allParts.getSelectionModel().getSelectedItem();
            associatedParts.add(thePart);
            allAssociatedParts.setItems(associatedParts);
        }
    }

    /**
     * onRemoveAssociatedPart method removes a part from a product's associated parts
     * @param actionEvent
     */
    public void onRemoveAssociatedPart(ActionEvent actionEvent) {
        if(allAssociatedParts.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No parts");
            alert.setHeaderText("Please select a part before removing");
            alert.setContentText("Click ok to confirm");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove associated part");
            alert.setHeaderText("Are you sure you want to remove this part from the product?");
            alert.setContentText("Click ok to confirm");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                Part thePart = allAssociatedParts.getSelectionModel().getSelectedItem();
                associatedParts.remove(thePart);
                allAssociatedParts.setItems(associatedParts);
            }
        }
    }

    /**
     * onSaveProduct method saves new product to inventory
     * @param actionEvent
     * @throws IOException
     */
    public void onSaveProduct(ActionEvent actionEvent) throws IOException {
        String checkStock = productInv.getText();
        String checkPrice = productPrice.getText();
        String checkMax = productMax.getText();
        String checkMin = productMin.getText();

        try {
            validateBlank();
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            if (!hasErrors) {
                try {
                    validateInput(checkStock, checkPrice, checkMax, checkMin);
                } catch(Exception e) {
                    System.out.println(e);
                }

                    int id = Inventory.getNewProductId();
                    String name = productName.getText();
                    int stock = Integer.parseInt(productInv.getText());
                    double price = Double.parseDouble(productPrice.getText());
                    int max = Integer.parseInt(productMax.getText());
                    int min = Integer.parseInt(productMin.getText());

                    validateInventory(stock, min, max);
                    if(!hasErrors) {
                        Product product = new Product(id, name, price, stock, min, max);
                        for (int i = 0; i < associatedParts.size(); i++) {
                            product.addAssociatedPart(associatedParts.get(i));
                        }
                        Inventory.addProduct(product);
                        returnToHomeScreen(actionEvent);
                    }
                }
            }catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Numbers expected in fields");
            alert.setHeaderText("Inv, Price, Min, Max, MachineId all must be numbers");
            alert.setContentText("Please enter valid numbers");
            alert.showAndWait();
        }

    }

    /**
     * onCancelProduct method cancels saving a new product
     * @param actionEvent
     * @throws IOException
     */
    public void onCancelProduct(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Are you sure you want to cancel and lose all data?");
        alert.setContentText("Click ok to confirm");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            returnToHomeScreen(actionEvent);
        }
    }

    /**
     * initialize method initializes the tableview for all parts in inventory and the product's associated parts
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allParts.setItems(Inventory.getAllParts());
        allPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        allProductPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allProductPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allProductPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * returnToHomeScreen method goes back to the main screen
     * @param actionEvent
     * @throws IOException
     */
    private void returnToHomeScreen(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainForm.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * onPartSearch method searches for a part by id, name or partial name
     * @param actionEvent
     */
    public void onPartSearch(ActionEvent actionEvent) {
        String query = partSearch.getText();
        ObservableList<Part> foundParts;
        foundParts = Inventory.lookupPart(query);

        if (foundParts.isEmpty()){
            try {
                int search = Integer.parseInt(partSearch.getText());
                Part foundPart = Inventory.lookupPart(search);
                foundParts.add(foundPart);
                allParts.setItems(foundParts);
            } catch(Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No parts");
                alert.setHeaderText("There are no parts that match that description");
                alert.setContentText("Click ok to confirm");
                alert.showAndWait();
                allParts.setItems(Inventory.getAllParts());
            }
        } else{
            allParts.setItems(foundParts);
        }
        partSearch.setText("");
    }


    /**
     * validateBlank method validates all fields have values entered into them
     */
    private void validateBlank() {
        if(productName.getText().trim().isBlank() || productInv.getText().trim().isBlank() || productPrice.getText().trim().isBlank() || productMax.getText().trim().isBlank()){
            hasErrors=true;
            blankErrorLabel.setVisible(true);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Blank fields");
            alert.setHeaderText("No fields can be blank");
            alert.setContentText("Please fill out the form");
            alert.showAndWait();
        } else{
            hasErrors = false;
        }
    }

    /**
     * validateInput method validates stock, price, max, and min are valid numbers
     * @param checkStock
     * @param checkPrice
     * @param checkMax
     * @param checkMin
     */
    private void validateInput(String checkStock, String checkPrice, String checkMax, String checkMin) {
        if (!checkStock.matches("^[\\d]+$")){
            hasErrors = true;
            invErrorLabel.setVisible(true);
        }
        if(!checkPrice.matches("^[\\d+\\.]+$")){
            hasErrors=true;
            priceErrorLabel.setVisible(true);
        }
        if(!checkMax.matches("^[\\d]+$")){
            hasErrors=true;
            maxErrorLabel.setVisible(true);
        }
        if(!checkMin.matches("^[\\d]+$")){
            hasErrors=true;
            minErrorLabel.setVisible(true);
        }
    }

    /**
     * validateInventory method validates min is less than max, and inv is between min and max values
     * @param stock
     * @param min
     * @param max
     */
    private void validateInventory(int stock, int min, int max){
        if (min > max){
            minMaxErrorLabel.setVisible(true);
            hasErrors = true;
        } else{
            minMaxErrorLabel.setVisible(false);
            hasErrors = false;
        }
        if (stock < min || stock > max){
            invMinMaxErrorLabel.setVisible(true);
            hasErrors = true;
        } else{
            invMinMaxErrorLabel.setVisible(false);
            hasErrors = false;
        }
    }
}
