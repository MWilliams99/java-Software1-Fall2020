package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Product class simulates a Product.
 * It allows for Parts to be associated with a Product.
 * @author Mary Williams
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * The constructor creates a Product.
     * @param id The integer to be given to the id of the Product.
     * @param name The string to be given to the name of the Product.
     * @param price The double to be given to the price of the Product.
     * @param stock The integer to be given to the stock of the Product.
     * @param min The integer to be given to the min of the Product.
     * @param max The integer to be given to the max of the Product.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * The getId method returns the id.
     * @return The value in the id field.
     */
    public int getId() {
        return id;
    }

    /**
     * The setId method sets the id of the Product.
     * @param id The integer to be given to the id of the Product.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * The getName method returns the name.
     * @return The value in the name field.
     */
    public String getName() {
        return name;
    }

    /**
     * The setName method sets the name of the Product.
     * @param name The string to be given to the name of the Product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The getPrice method returns the price.
     * @return The value in the price field.
     */
    public double getPrice() {
        return price;
    }

    /**
     * The setPrice methods sets the price of the Product.
     * @param price The double to be given to the price of the Product.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * The getStock method returns the stock.
     * @return The value in the stock field.
     */
    public int getStock() {
        return stock;
    }

    /**
     * The setStock method sets the stock of the Product.
     * @param stock The integer to be given to the stock of the Product.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * The getMin method returns the min.
     * @return The value in the min field.
     */
    public int getMin() {
        return min;
    }

    /**
     * The setMin method sets the min of the Product.
     * @param min The integer to be given to the min of the Product.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * The getMax method returns the max.
     * @return The value in the max field.
     */
    public int getMax() {
        return max;
    }

    /**
     * The setMax method sets the max of the Product.
     * @param max The integer to be given to the max of the Product.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * The addAssociatedPart method adds a Part to the associatedParts Observable List.
     * @param part The part to be added to the associatedParts Observable List.
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * The deleteAssociatedPart method removes a Part from the associatedParts Observable List.
     * @param selectedAssociatedPart The part to be removed from the associatedParts Observable List.
     * @return Returns true if the Part could successfully be removed from the associatedParts Observable List, otherwise it returns false.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if(associatedParts.remove(selectedAssociatedPart)){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * The getAllAssociatedParts method returns the associatedParts Observable List.
     * @return The list of all Parts in the associatedParts Observable List.
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
