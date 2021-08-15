package com.dvessey.inventorymanagementsystemsoftware;

/**
 * InHouse class is a child of Part class
 * @author Damon Vessey
 */
public class InHouse extends Part{
    private  int machineId;

    /**
     * InHouse constructor calls constructor of part class, and adds machine id
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * getMachineId gets the machine's id
     * @return machine id
     */
    public  int getMachineId() {
        return machineId;
    }

    /**
     * setMachineId sets the machine's id
     * @param machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
