package com.dvessey.inventorymanagementsystemsoftware;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product class
 * @author Damon Vessey
 */
public class Product {
    private  ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private  int id;
    private  String name;
    private  double price;
    private  int stock;
    private  int min;
    private  int max;

    /**
     * Product constructor
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id=id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * getAllAssociatedParts gets all parts associated with the product
     * @return list of parts associated with product
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     * addAssociatedPart method adds a part to the product
     * @param part
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * getId gets the product's id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * setId sets the products id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * getName gets the product's name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setName sets the product's name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getPrice gets the product's price
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * setPrice sets the product's price
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * getStock gets the product's inventory
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * setStock sets the product's inventory
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * getMin gets the product's minimum amount
     * @return
     */
    public int getMin() {
        return min;
    }

    /**
     * setMin sets the product's minimum amount
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * getMax gets the product's maximum amount
     * @return max
     */
    public int getMax() {
        return max;
    }

    /**
     * setMax sets the product's maximum amount
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * deleteAssociatedPart deletes the part associated with the product
     * @param selectedAssociatedPart
     * @return
     */
    public  boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if (associatedParts.contains(selectedAssociatedPart)){
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        return false;
    }


}
