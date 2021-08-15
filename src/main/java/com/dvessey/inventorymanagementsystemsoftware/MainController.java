package com.dvessey.inventorymanagementsystemsoftware;


import javafx.application.Platform;
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
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * MainController class is the main controller for the main screen
 * @author Damon Vessey
 */
public class MainController implements Initializable {
    @FXML
    public TableView<Part> allParts;
    @FXML
    public TableColumn allPartIdCol;
    @FXML
    public TableColumn allPartInvLvlCol;
    @FXML
    public TableColumn allPartsPriceCol;
    @FXML
    public TableColumn allPartNameCol;
    @FXML
    public TextField partSearch;
    @FXML
    public Button addPart;
    @FXML
    public Button editPart;
    @FXML
    public Button deletePart;
    @FXML
    public TableView<Product> allProducts;
    @FXML
    public TableColumn allProductIdCol;
    @FXML
    public TableColumn allProductNameCol;
    @FXML
    public TableColumn allProductInvLevelCol;
    @FXML
    public TableColumn allProductPriceCol;
    @FXML
    public TextField productSearch;
    @FXML
    public Button deleteProduct;
    @FXML
    public Button editProduct;
    @FXML
    public Button addProduct;
    @FXML
    public Button exit;

    private static Inventory inv = new Inventory();

    private static Part thePart = null;
    private static Product theProduct = null;

    ObservableList<Product> products = FXCollections.observableArrayList();

    /**
     * getThePart method to pass part to other controllers
     * @return Part
     */
    public static Part getThePart(){
        return thePart;
    }

    /**
     * getTheProduct method to pass product to other controllers
     * @return Product
     */
    public static Product getTheProduct(){ return theProduct;}

    /**
     * initialize method initializes the tableviews in the main screen
     * FUTURE ENHANCEMENT - it would be nice, when a user selects a product in the table to display all parts (if any) that are associated with that product
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allParts.setItems(inv.getAllParts());
        allPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        allPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        allProducts.setItems(inv.getAllProducts());
        allProductIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allProductInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * onAddPart goes to the add a part screen
     * @param actionEvent
     * @throws IOException
     */
    public void onAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 689, 685);
        stage.setTitle("Inventory Management System - Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * onEditPart goes to edit part screen
     * @param actionEvent
     * @throws IOException
     */
    public void onEditPart(ActionEvent actionEvent) throws IOException {
        if (allParts.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No part selected");
            alert.setHeaderText("Please select a part before modifying");
            alert.setContentText("Click ok to confirm");
            alert.showAndWait();
        } else {
            thePart = allParts.getSelectionModel().getSelectedItem();
            Parent root = FXMLLoader.load(getClass().getResource("EditPart.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 689, 685);
            stage.setTitle("Inventory Management System - Modify Part");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * onDeletePart deletes a part from inventory
     * @param actionEvent
     */
    public void onDeletePart(ActionEvent actionEvent) {
        if (allParts.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No part selected");
            alert.setHeaderText("Please select a part before deleting");
            alert.setContentText("Click ok to confirm");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                thePart = allParts.getSelectionModel().getSelectedItem();
                Inventory.deletePart(thePart);
                allParts.setItems(Inventory.getAllParts());
            }
        }
    }

    /**
     * onDeleteProduct deletes a product from inventory
     * @param actionEvent
     */
    public void onDeleteProduct(ActionEvent actionEvent) {
        if (allProducts.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No products");
            alert.setHeaderText("Please select a product before deleting");
            alert.setContentText("Click ok to confirm");
            alert.showAndWait();
        } else {
            theProduct = allProducts.getSelectionModel().getSelectedItem();
            if (theProduct.getAllAssociatedParts().size() == 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && (result.get() == ButtonType.OK)) {
                    theProduct = allProducts.getSelectionModel().getSelectedItem();
                    Inventory.deleteProduct(theProduct);
                    allProducts.setItems(Inventory.getAllProducts());
                }
            } else {
                //DISPLAY ERROR FOR PRODUCT CONTAINING ASSOCIATED PARTS
                Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot delete a product that contains associated parts!");
                alert.setTitle("Delete Product");
                alert.showAndWait();
            }
        }
    }

    /**
     * onEditProduct goes to edit product screen
     * @param actionEvent
     * @throws IOException
     */
    public void onEditProduct(ActionEvent actionEvent) throws IOException {
        if (allProducts.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No products");
            alert.setHeaderText("Please select a product before modifying");
            alert.setContentText("Click ok to confirm");
            alert.showAndWait();
        } else {
            theProduct = allProducts.getSelectionModel().getSelectedItem();
            Parent root = FXMLLoader.load(getClass().getResource("EditProduct.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 700);
            stage.setTitle("Inventory Management System - Modify Product");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * onAddProduct goes to add product screen
     * @param actionEvent
     * @throws IOException
     */
    public void onAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Inventory Management System - Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * onExit exits the application
     * @param actionEvent
     */
    public void onExit(ActionEvent actionEvent) {
        Platform.exit();
    }

    /**
     * onProductSearch search for a product by id, name, or partial name
     * @param actionEvent
     */
    public void onProductSearch(ActionEvent actionEvent) {
        String query = productSearch.getText();
        ObservableList<Product> foundProducts;
        foundProducts = Inventory.lookupProduct(query);

        if(foundProducts.isEmpty()){
            try {
                int search = Integer.parseInt(productSearch.getText());
                 Product foundProduct = Inventory.lookupProduct(search);
                 foundProducts.add(foundProduct);
                 allProducts.setItems(foundProducts);
            } catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No products");
                alert.setHeaderText("There are no products that match that description");
                alert.setContentText("Click ok to confirm");
                alert.showAndWait();
                allProducts.setItems(Inventory.getAllProducts());
            }
        } else{
            allProducts.setItems(foundProducts);
        }
        productSearch.setText("");
    }

    /**
     * onPartSearch search for a part by id, name, or partial name
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
}
