package com.dvessey.inventorymanagementsystemsoftware;

/**
 * Outsourced class is a child of part
 * @author Damon Vessey
 */
public class Outsourced extends Part{

    private  String companyName;

    /**
     * Outsourced constructor calls Part constructor and adds companyName
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * getCompanyName gets the company name
     * @return companyName
     */
    public  String getCompanyName() {
        return companyName;
    }

    /**
     * setCompanyName sets the company name
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
