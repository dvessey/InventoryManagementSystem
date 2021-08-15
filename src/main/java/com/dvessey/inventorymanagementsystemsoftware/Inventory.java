package com.dvessey.inventorymanagementsystemsoftware;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory class holds parts and products, also sets unique ids
 * @author Damon Vessey
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private static int partId = 0;
    private static int productId = 0;

    /**
     * getNewPartId method gets unique part ids
     * @return partId
     */
    public static int getNewPartId(){
        partId++;
        return partId;
    }

    /**
     * getNewProductId method gets unique product ids
     * @return
     */
    public static int getNewProductId(){
        productId++;
        return productId;
    }

    /**
     * addPart method adds part to Inventory
     * @param newPart
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * addProduct method adds product to inventory
     * @param newProduct
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * lookupPart looks up part by id
     * @param partId
     * @return list of parts
     */
    public static Part lookupPart(int partId) {
        if (!allParts.isEmpty()) {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getId() == partId) {
                    return allParts.get(i);
                }
            }
        }
        return null;
    }

    /**
     * lookupProduct looks up products by id
     * @param productId
     * @return list of products
     */
    public static Product lookupProduct(int productId){
        if (!allProducts.isEmpty()){
            for (int i = 0; i < allProducts.size(); i++){
                if (allProducts.get(i).getId() == productId){
                    return allProducts.get(i);
                }
            }
        }
        return null;
    }

    /**
     * lookupPart method looks up parts by name or partial name
     * @param partName
     * @return list of parts
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        if (!allParts.isEmpty()) {
            for (Part part : allParts) {
                if (part.getName().contains(partName)) {
                    foundParts.add(part);
                }
            }
        } else {
            return foundParts;
        }
        return foundParts;
    }


    /**
     * lookupProduct method looks up products by name or partial name
     * @param productName
     * @return
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        if (!allProducts.isEmpty()){
            for (Product product : allProducts){
                if (product.getName().contains(productName)){
                    foundProducts.add(product);
                }
            }
        } else{
            return foundProducts;
        }
        return foundProducts;
    }

    /**
     * updatePart method updates an existing part
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart){
        //if (allParts.contains(selectedPart)){
            allParts.set(index, selectedPart);
        //}
    }

    /**
     * updateProduct method updates an existing product
     * @param index
     * @param newProduct
     */
    public static void updateProduct(int index, Product newProduct){
        if (allProducts.contains(newProduct)){
            allProducts.set(index, newProduct);
        }
    }

    /**
     * deletePart method deletes an existing part
     * @param selectedPart
     * @return
     */
    public static boolean deletePart(Part selectedPart){
        if(allParts.contains(selectedPart)){
            allParts.remove(selectedPart);
            return true;
        }
        return false;
    }

    /**
     * deleteProduct method deletes an existing product
     * @param selectedProduct
     * @return
     */
    public static boolean deleteProduct(Product selectedProduct){
        if(allProducts.contains(selectedProduct)){
            allProducts.remove(selectedProduct);
            return true;
        }
        return false;
    }

    /**
     * getAllParts method gets all parts from inventory
     * @return list of parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * getAllProducts method get all products from inventory
     * @return list of products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
