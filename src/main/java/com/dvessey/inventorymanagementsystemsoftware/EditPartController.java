package com.dvessey.inventorymanagementsystemsoftware;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * EditPartController class to edit a part
 * @author Damon Vessey
 */
public class EditPartController implements Initializable {
    @FXML
    public Label sourceLabel;
    @FXML
    public RadioButton inHouse;
    @FXML
    public RadioButton outsourced;
    @FXML
    public TextField partId;
    @FXML
    public TextField addName;
    @FXML
    public TextField addInv;
    @FXML
    public TextField addPrice;
    @FXML
    public TextField addMax;
    @FXML
    public TextField addMin;
    @FXML
    public TextField addSource;
    @FXML
    public Button savePart;
    @FXML
    public Button cancelPart;
    @FXML
    public Label nameErrorLabel;
    @FXML
    public Label invErrorLabel;
    @FXML
    public Label priceErrorLabel;
    @FXML
    public Label minMaxErrorLabel;
    @FXML
    public Label machineIdErrorLabel;
    @FXML
    public Label maxErrorLabel;
    @FXML
    public Label minErrorLabel;
    @FXML
    public Label companyNameErrorLabel;
    @FXML
    public Label blankErrorLabel;

    private Part thePart;
    private boolean hasErrors = false;

    /**
     * onInHouse method used to set sourceLabel to Machine ID
     * @param actionEvent
     */
    public void onInHouse(ActionEvent actionEvent) {
        sourceLabel.setText("Machine ID");
    }

    /**
     * onOutsourced method used to set sourceLabel to Company Name
     * @param actionEvent
     */
    public void onOutsourced(ActionEvent actionEvent) {
        sourceLabel.setText("Company Name");
    }

    /**
     * onSavePart saves a part to inventory
     * LOGICAL ERROR - even if the form was not valid, the part was still being saved and returned to the home screen, I had to implement a flag called hasErrors
     * to check if the form has any errors, if it does not have errors, it can continue to save.
     * RUNTIME ERROR - It was not possible to cast an Inhouse part to an Outsource part or vice versa, so I had to create a new Inhouse or Outsourced part in memory in order to
     * change from on type to the other.
     * @param actionEvent
     * @throws IOException
     */
    public void onSavePart(ActionEvent actionEvent) throws IOException {
        int i = Inventory.getAllParts().indexOf(thePart);
        String checkStock = addInv.getText();
        String checkPrice = addPrice.getText();
        String checkMax = addMax.getText();
        String checkMin = addMin.getText();

        try{
            validateBlank();
        } catch(Exception e){
            System.out.println(e);
        }

        try {
            if (!hasErrors) {
                if (inHouse.isSelected()) {
                    String checkMachineId = addSource.getText();
                    try {
                        validateInhouseInput(checkStock, checkPrice, checkMax, checkMin, checkMachineId);
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                    String name = addName.getText();
                    int id = thePart.getId();
                    int stock = Integer.parseInt(addInv.getText());
                    double price = Double.parseDouble(addPrice.getText());
                    int max = Integer.parseInt(addMax.getText());
                    int min = Integer.parseInt(addMin.getText());
                    int machineId = Integer.parseInt(addSource.getText());
                    validateInventory(stock, min, max);

                    if (!hasErrors) {
                        InHouse inHouse = new InHouse(id, name, price, stock, min, max, machineId);
                        Inventory.updatePart(i, inHouse);
                        returnToHomeScreen(actionEvent);
                    }
                }

                if (outsourced.isSelected()) {
                    String name = addName.getText();
                    int id = thePart.getId();
                    int stock = Integer.parseInt(addInv.getText());
                    double price = Double.parseDouble(addPrice.getText());
                    int max = Integer.parseInt(addMax.getText());
                    int min = Integer.parseInt(addMin.getText());
                    String companyName = addSource.getText();
                    validateInventory(stock, min, max);
                    validateOutsourceInput(checkStock, checkPrice, checkMax, checkMin);

                    if (!hasErrors) {
                        Outsourced outsourced = new Outsourced(id, name, price, stock, min, max, companyName);
                        Inventory.updatePart(i, outsourced);
                        returnToHomeScreen(actionEvent);
                    }
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
     * validateOutsourceInput method validates stock, price, max and min are valid numbers
     * @param checkStock
     * @param checkPrice
     * @param checkMax
     * @param checkMin
     */
    private void validateOutsourceInput(String checkStock, String checkPrice, String checkMax, String checkMin) {
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
     * validateInhouseInput method validates stock, price, max, min, and machineId are valid numbers
     * @param checkStock
     * @param checkPrice
     * @param checkMax
     * @param checkMin
     * @param checkMachineId
     */
    private void validateInhouseInput(String checkStock, String checkPrice, String checkMax, String checkMin, String checkMachineId) {
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
        if(!checkMachineId.matches("^[\\d]+$")){
            hasErrors=true;
            machineIdErrorLabel.setVisible(true);
        }
    }

    /**
     * initialize method initializes current information of the part being edited
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        thePart = MainController.getThePart();
       Integer thePartId = thePart.getId();
        partId.setText(thePartId.toString());
        String thePartName = thePart.getName();
        addName.setText(thePartName);
        Double thePartPrice = thePart.getPrice();
        addPrice.setText(thePartPrice.toString());
        Integer thePartInventory = thePart.getStock();
        addInv.setText(thePartInventory.toString());
        Integer thePartMin = thePart.getMin();
        addMin.setText(thePartMin.toString());
        Integer thePartMax = thePart.getMax();
        addMax.setText(thePartMax.toString());
        if (thePart instanceof InHouse){
            Integer thePartMachineId = ((InHouse) thePart).getMachineId();
            sourceLabel.setText("Machine ID");
            addSource.setText(thePartMachineId.toString());
            inHouse.setSelected(true);

        }
        if (thePart instanceof Outsourced){
            String thePartCompanyName = ((Outsourced) thePart).getCompanyName();
            sourceLabel.setText("Company Name");
            addSource.setText(thePartCompanyName);
            outsourced.setSelected(true);
        }

    }

    /**
     * validateInventory method validates min is less than max, and stock is between min and max values
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
           invErrorLabel.setVisible(true);
            hasErrors = true;
        } else{
            invErrorLabel.setVisible(false);
            hasErrors = false;
        }
    }

    /**
     * returnToHomeScreen method returns to the main screen
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
     * onCancelPart method cancels the creation of a new part
     * @param actionEvent
     * @throws IOException
     */
    public void onCancelPart(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("Click ok to confirm");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            returnToHomeScreen(actionEvent);
        }
    }

    /**
     * validateBlank method validates no field entries are blank
     */
    private void validateBlank() {
        if(addName.getText().trim().isBlank() || addInv.getText().trim().isBlank() || addPrice.getText().trim().isBlank() || addMax.getText().trim().isBlank() || addSource.getText().trim().isBlank()){
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
}
