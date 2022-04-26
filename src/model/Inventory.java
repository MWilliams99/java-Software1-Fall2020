package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Inventory class simulates an Inventory.
 * It allows for Parts and Products to be added, removed, searched for, and managed within the Inventory.
 * @author Mary Williams
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private static int totalPartsEver = 0;
    private static int totalProductsEver = 0;


    /**
     * The addPart method adds a Part to the allParts Observable List.
     * It also adds 1 to the totalPartsEver static int to count all Parts for use in the unique ID.
     * @param newPart The part to be added to the allParts Observable List.
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
        totalPartsEver++;
    }

    /**
     * The addProduct method adds a Product to the allProducts Observable List.
     * It also adds 1 to the totalProductsEver static int to count all Products for use in the unique ID.
     * @param newProduct The product to be added to the allProducts Observable List.
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
        totalProductsEver++;
    }


    /**
     * The lookupPart method that accepts an int parameter searches the allParts Observable List for a Part where the ID matches the parameter.
     * @param partId The integer to look for in allParts part IDs.
     * @return If a part ID matches in the search it returns the part, otherwise it returns null if no part ID matches or if there are no parts in the allParts Observable List to search from.
     */
    public static Part lookupPart(int partId){
        if(!allParts.isEmpty()){
            for(int x = 0; x < allParts.size(); x++){
                if(allParts.get(x).getId() == partId){
                    //If allParts is not empty and a match is found, return the matching part.
                    return allParts.get(x);
                }
            }
            //If allParts is not empty, but no match is found, return null.
            return null;
        }
        //If allParts is empty, return null.
        return null;
    }

    /**
     * The lookupProduct method that accepts an int parameter searches the allProducts Observable List for a Product where the ID matches the parameter.
     * @param productId The integer to look for in allProducts product IDs.
     * @return If a product ID matches in the search it returns the product, otherwise it returns null if no product ID matches or if there are no products in the allProducts Observable List to search from.
     */
    public static Product lookupProduct(int productId){
        if(!allProducts.isEmpty()){
            for(int x = 0; x < allProducts.size(); x++){
                if(allProducts.get(x).getId() == productId){
                    //If allProducts is not empty and a match is found, return the matching product.
                    return allProducts.get(x);
                }
            }
            //If allProducts is not empty, but no match is found, return null.
            return null;
        }
        //If allProducts is empty, return null.
        return null;
    }


    /**
     * The lookupPart method that accepts a string parameter searches the allParts Observable List for all Part names that contain a matching string to the parameter.
     * @param partName The string to check against allParts part names.
     * @return The list of all Parts where the name contains the search string even if none are found, otherwise it returns null if there are no parts in the allParts Observable List to search from.
     */
    public static ObservableList<Part> lookupPart(String partName){
        if(!allParts.isEmpty()){
            //An Observable List to hold any parts that match the search term.
            ObservableList<Part> searchAllParts = FXCollections.observableArrayList();
            //Enhanced for loop used to step through every Part in the allParts Observable List.
            for(Part x : allParts){
                if(x.getName().toLowerCase().contains(partName)){
                    //If a part name contains the search string, add it to the searchAllParts Observable List.
                    searchAllParts.add(x);
                }
            }
            //Return searchAllParts list whether or not any parts have been added to it.
            return searchAllParts;
        }
        //If allParts is empty, return null.
        return null;
    }

    /**
     * The lookupProduct method that accepts a string parameter searches the allProducts Observable List for all Product names that contain a matching string to the parameter.
     * @param productName The string to check against allProducts product names.
     * @return The list of all Products where the name contains the search string even if none are found, otherwise it returns null if there are no products in the allProducts Observable List to search from.
     */
    public static ObservableList<Product> lookupProduct(String productName){
        if(!allProducts.isEmpty()){
            //An Observable List to hold any products that match the search term.
            ObservableList<Product> searchAllProducts = FXCollections.observableArrayList();
            //Enhanced for loop used to step through every Product in the allProducts Observable List.
            for(Product x : allProducts){
                if(x.getName().toLowerCase().contains(productName)){
                    //If a product name contains the search string, add it to the searchAllProducts Observable List.
                    searchAllProducts.add(x);
                }
            }
            //Return searchAllProducts list whether or not any products have been added to it.
            return searchAllProducts;
        }
        //If allProducts is empty, return null.
        return null;
    }


    /**
     * The updatePart method replaces a Part in the allParts Observable List.
     * @param index The index of the part in the allParts Observable List to replace.
     * @param selectedPart The updated part to replace its older version in the allParts Observable List.
     */
    public static void updatePart(int index, Part selectedPart){
        //Replace allParts part at index with the updated selectedPart.
        allParts.set(index, selectedPart);
    }

    /**
     * The updateProduct method replaces a Product in the allProducts Observable List.
     * @param index The index of the product in the allProducts Observable List to replace.
     * @param newProduct The updated product to replace its older version in the allProducts Observable List.
     */
    public static void updateProduct(int index, Product newProduct){
        //Replace allProducts product at index with the updated newProduct.
        allProducts.set(index, newProduct);
    }


    /**
     * The deletePart method removes a Part from the allParts Observable List.
     * @param selectedPart The part to be removed from the allParts Observable List.
     * @return If the selectedPart can be removed from the allParts Observable List it returns true, otherwise it returns false.
     */
    public static boolean deletePart(Part selectedPart){
        if(allParts.remove(selectedPart)){
            //If the selectedPart can be removed from allParts, return true - used to display a success dialog box.
            return true;
        }
        else{
            //If the selectedPart can not be removed from allParts, return false - used to display an error dialog box.
            return false;
        }
    }

    /**
     * The deleteProduct method removes a Product from the allProducts Observable List, as long as it has no associated Parts.
     * @param selectedProduct The product to be removed from the allProducts Observable List.
     * @return If the selectedProduct can be removed from the allProducts Observable List it returns true, otherwise it returns false.
     */
    public static boolean deleteProduct(Product selectedProduct){
        if(selectedProduct.getAllAssociatedParts().isEmpty()){
            //If the selectedProduct does not have any associated parts, remove it from allProducts and return true - used to display a success dialog box.
            allProducts.remove(selectedProduct);
            return true;
        }
        else{
            //If the selectedProduct has associated parts, return false - used to display an error dialog box.
            return false;
        }
    }


    /**
     * The getAllParts method returns the allParts Observable List.
     * @return The list of all Parts in the allParts Observable List.
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     * The getAllProducts method returns the allProducts Observable List.
     * @return The list of all Products in the allProducts Observable List.
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }


    /**
     * The getTotalPartsEver method returns the totalPartsEver for use in creating unique IDs for Parts.
     * @return The value in the totalPartsEver field.
     */
    public static int getTotalPartsEver(){
        return totalPartsEver;
    }

    /**
     * The getTotalProductsEver method returns the totalProductsEver for use in creating unique IDs for Products.
     * @return The value in the totalProductsEver field.
     */
    public static int getTotalProductsEver(){
        return totalProductsEver;
    }


}
