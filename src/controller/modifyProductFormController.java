package controller;

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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The modifyProductFormController class is used as the Controller for modifyProductFormScreen.fxml.
 * @author Mary Williams
 */
public class modifyProductFormController implements Initializable {
    //Text Fields
    @FXML
    private TextField modifyProductIDTextF;
    @FXML
    private TextField modifyProductNameTextF;
    @FXML
    private TextField modifyProductInvTextF;
    @FXML
    private TextField modifyProductPriceTextF;
    @FXML
    private TextField modifyProductMaxTextF;
    @FXML
    private TextField modifyProductMinTextF;
    //Search Text Field
    @FXML
    private TextField modifyProductSearchField;

    //TableView/TableColumns
    @FXML
    private TableView allPartsTableView;
    @FXML
    private TableColumn allPartID;
    @FXML
    private TableColumn allPartName;
    @FXML
    private TableColumn allPartInv;
    @FXML
    private TableColumn allPartCost;

    @FXML
    private TableView associatedPartsTableView;
    @FXML
    private TableColumn associatedPartID;
    @FXML
    private TableColumn associatedPartName;
    @FXML
    private TableColumn associatedPartInv;
    @FXML
    private TableColumn associatedPartCost;

    //Associate/Remove/Save/Cancel Buttons
    @FXML
    private Button modifyProductAssociateButton;
    @FXML
    private Button modifyProductRemoveButton;
    @FXML
    private Button modifyProductSaveButton;
    @FXML
    private Button modifyProductCancelButton;


    //Way to pass which product from MainForm Table View to modify
    Product productToModify;
    int productIndex;
    //Observable List to hold all parts associated with the Product.
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * The modifyProductFormController method is used to create a Product within the Controller to work with as modifications are made.
     * @param modifySelectedProduct The Product selected in mainFormScreen.fxml that is passed when modifyProductFormController is called, used to initialize Text Fields, Table Views, and gather the Product ID.
     */
    public modifyProductFormController(Product modifySelectedProduct){
        productToModify = modifySelectedProduct;
        productIndex = Inventory.getAllProducts().indexOf(modifySelectedProduct);
        associatedParts = modifySelectedProduct.getAllAssociatedParts();
    }


    //Section to initialize text fields with product data and to initialize tableviews
    /**
     * The initialize method takes data from the productToModify to initialize values into the Text Fields, and sets the allParts and associatedParts tables to the allParts and associatedParts Observable Lists.
     * It starts the allParts table with all Parts in the system, not filtering them to any search.
     * Table placeholders are set in case there are no Parts in the system, or no Parts associated with the Product.
     * @param url Resource pointer used by initialize.
     * @param resourceBundle Locale-specific objects used by initialize.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Get data from productToModify and initialize into Text Fields
        int productId = productToModify.getId();
        String productName = productToModify.getName();
        double productPrice = productToModify.getPrice();
        int productInv = productToModify.getStock();
        int productMin = productToModify.getMin();
        int productMax = productToModify.getMax();

        modifyProductIDTextF.setText(Integer.toString(productId));
        modifyProductNameTextF.setText(productName);
        modifyProductPriceTextF.setText(Double.toString(productPrice));
        modifyProductInvTextF.setText(Integer.toString(productInv));
        modifyProductMinTextF.setText(Integer.toString(productMin));
        modifyProductMaxTextF.setText(Integer.toString(productMax));

        //Initialize allParts with Inventory.getAllParts - and associatedParts with productToModify.getAssociatedParts
        allPartsTableView.setItems(Inventory.getAllParts());
        associatedPartsTableView.setItems(associatedParts);

        allPartID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        allPartName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        allPartInv.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        allPartCost.setCellValueFactory(new PropertyValueFactory<>("Price"));
        allPartsTableView.setPlaceholder(new Label("There are no Parts in the system."));

        associatedPartID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        associatedPartInv.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        associatedPartCost.setCellValueFactory(new PropertyValueFactory<>("Price"));
        associatedPartsTableView.setPlaceholder(new Label("There are no Parts associated with the Product."));
    }


    //Parts Search Functionality
    /**
     * The partSearchTextF method searches the allParts Observable List using Inventory.lookupPart, and sets the allPartsTableView to parts that match the search.
     * Table placeholder is set in case no parts are found that match the search.
     * @param event The KeyEvent that triggers this method. Every time the user types or deletes in the modifyProductSearch Text Field this method is called.
     */
    @FXML
    void partSearchTextF(KeyEvent event) {
        if(Inventory.getAllParts().isEmpty() || modifyProductSearchField.getText().trim().isEmpty()){
            allPartsTableView.setItems(Inventory.getAllParts());
            allPartsTableView.setPlaceholder(new Label("There are no Parts in the system."));
        }
        else{
            ObservableList<Part> searchAllParts = FXCollections.observableArrayList();
            int searchPartID;
            String searchPartString;

            try{
                searchPartID = Integer.parseInt(modifyProductSearchField.getText().trim());
            }
            catch(NumberFormatException e){
                searchPartID = 0;
            }

            if(!(Inventory.lookupPart(searchPartID) == null)){
                searchAllParts.add(Inventory.lookupPart(searchPartID));
            }

            searchPartString = modifyProductSearchField.getText().trim().toLowerCase();
            searchAllParts.addAll(Inventory.lookupPart(searchPartString));

            allPartsTableView.setItems(searchAllParts);
            allPartsTableView.setPlaceholder(new Label("There are no Part IDs or Part Names that match that search."));
        }
    }

    //Clear Part search text field when it regains focus and reset to allParts.
    /**
     * The clearPartSearch method clears the modifyProductSearch Text Field and resets the allPartsTableView list to allParts.
     * Table placeholder is set in case there are no parts in the system.
     * @param event The MouseEvent that triggers this method. When the user clicks back into the modifyProductSearch Text Field this method is called.
     */
    @FXML
    void clearPartSearch(MouseEvent event) {
        modifyProductSearchField.clear();
        allPartsTableView.setItems(Inventory.getAllParts());
        allPartsTableView.setPlaceholder(new Label("There are no Parts in the system."));
    }

    //Add a part to associated parts list - and show in bottom table view
    /**
     * The modifyProductAssociateButton method associates a Part with the Product, adding it to the associatedParts Observable List and displaying it in the associatedPartsTableView.
     * If there are no parts in the system to select from, or if there is nothing selected, this method instead shows a dialog box with a related error message.
     * @param event The ActionEvent that triggers this method. When the user clicks on the 'Add' Button this method is called.
     */
    @FXML
    void modifyProductAssociateButton(ActionEvent event) {
        Part partToAssociate = (Part) allPartsTableView.getSelectionModel().getSelectedItem();
        //Do not attempt to associated a part if there are no parts to associate or nothing is selected.
        if(Inventory.getAllParts().isEmpty()){
            JOptionPane.showMessageDialog(null,"There are no Parts in the system to associate with the Product.");
            return;
        }
        else if(partToAssociate == null){
            JOptionPane.showMessageDialog(null,"Please select a Part to associate with the Product.");
            return;
        }
        else{
            associatedParts.add(partToAssociate);
        }
    }

    //Remove a part from associated parts list - and remove from bottom table view
    /**
     * The modifyProductDisassociateButton method removes an associated Part from the Product, removing it from the associatedParts Observable List and from the associatedPartsTableView.
     * If there are no associated parts, or if there is nothing selected, this method instead shows a dialog box with a related error message.
     * It confirms that the user would like to remove a Part from being associated with the Product.
     * @param event The ActionEvent that triggers this method. When the user clicks on the 'Remove Associated Part' Button this method is called.
     */
    @FXML
    void modifyProductDisassociateButton(ActionEvent event) {
        Part partToDisassociate = (Part) associatedPartsTableView.getSelectionModel().getSelectedItem();
        //Do not attempt to remove an associated part if there are no associated parts or nothing is selected.
        if(associatedParts.isEmpty()){
            JOptionPane.showMessageDialog(null,"There are no Parts associated with the Product to remove.");
            return;
        }
        else if(partToDisassociate == null){
            JOptionPane.showMessageDialog(null,"Please select a Part to remove association with the Product.");
            return;
        }
        else{
            int dialogResult = JOptionPane.showConfirmDialog(null, "Remove the association of this Part from the Product?", "Confirm", JOptionPane.YES_NO_OPTION);
            if(!(dialogResult == JOptionPane.YES_OPTION)){
                return;
            }

            associatedParts.remove(partToDisassociate);
        }
    }

    //Intake data and update product in the system
    /**
     * The modifyProductSaveButton method checks that the user input valid data into each Text Field, confirms the user would like to save the modifications, updates the Product, and returns to mainFormScreen.fxml.
     * Data validation checks performed include checking if any fields are empty, that reasonable doubles or integers are input into the correct fields, and logical checks for Price, Inv, Min, and Max.
     * Dialog boxes with relevant error messages show up if the user has input anything incorrectly.
     * A new Product is created, replacing the old Product in the Inventory.
     * A confirmation dialog box appears confirming that the user would like to save the modifications to the Product.
     * @param event The ActionEvent that triggers this method. When the user clicks on the 'Save' Button this method is called.
     * @exception IOException Failed to load mainFormScreen.fxml.
     */
    @FXML
    void modifyProductSaveButton(ActionEvent event) throws IOException {
        int productId = productToModify.getId();
        String productName;
        double productPrice;
        int productInv;
        int productMin;
        int productMax;

        //Check if any text fields are empty, if so give a dialog box informing the user to fill in every text field.
        if(modifyProductNameTextF.getText().trim().isEmpty() ||
           modifyProductPriceTextF.getText().trim().isEmpty() ||
           modifyProductInvTextF.getText().trim().isEmpty() ||
           modifyProductMinTextF.getText().trim().isEmpty() ||
           modifyProductMaxTextF.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null,"Please input data for each field.");
            return;
        }

        //"The user may associate zero, one, or more parts with a product." -> no need to check if associated parts is empty or not here.
        //Get Product Name and put it into string var
        productName = modifyProductNameTextF.getText().trim();

        //Check that Price Text Field has a valid number that can be parsed to double - else send dialog box.
        try{
            productPrice = Double.parseDouble(modifyProductPriceTextF.getText().trim());
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null,"Please enter a valid number into the Price Field.");
            return;
        }

        //Check that Price Text Field is not negative/0 - else send dialog box
        if(productPrice <= 0){
            JOptionPane.showMessageDialog(null,"Please enter a value greater than 0 into the Price Field.");
            return;
        }

        //Check that Inv Text Field has a valid int that can be parsed - else send dialog box
        try{
            productInv = Integer.parseInt(modifyProductInvTextF.getText().trim());
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null,"Please enter a valid whole number into the Inventory Field.");
            return;
        }

        //Check that Max Text Field has a valid int that can be parsed - else send dialog box
        try{
            productMax = Integer.parseInt(modifyProductMaxTextF.getText().trim());
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null,"Please enter a valid whole number into the Maximum Field.");
            return;
        }

        //Check that Min Text Field has a valid int that can be parsed - else send dialog box
        try{
            productMin = Integer.parseInt(modifyProductMinTextF.getText().trim());
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null,"Please enter a valid whole number into the Minimum Field.");
            return;
        }


        //Check that Min is greater than or equal to (>=) 0. Makes no sense to have/be able to have negative inventory - else send dialog box.
        if(productMin < 0){
            JOptionPane.showMessageDialog(null, "Please enter a value greater than or equal to 0 into the Minimum Field.");
            return;
        }
        //Check that Min is less than Max - else send dialog box.
        if(!(productMin < productMax)){
            JOptionPane.showMessageDialog(null,"Minimum must be less than Maximum.");
            return;
        }
        //Check that Inv is between Min and Max - else send dialog box.
        if(!(productInv >= productMin && productInv <= productMax)){
            JOptionPane.showMessageDialog(null,"Inventory must be a value between Minimum and Maximum.");
            return;
        }


        //Confirm user wants to update/modify the product
        int dialogResult = JOptionPane.showConfirmDialog(null, "Save these modifications to the Product?", "Confirm", JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.YES_OPTION){
            Product updateProduct = new Product(productId, productName, productPrice, productInv, productMin, productMax);

            //For each part in associatedParts, associate it with the updateProduct using for loop.
            if(!(associatedParts.isEmpty())){
                for(Part x : associatedParts){
                    updateProduct.addAssociatedPart(x);
                }
            }

            //Update product in Inventory using updateProduct(index, product)
            Inventory.updateProduct(productIndex, updateProduct);

            //Once saved, close and return to Main Form.
            Parent parent = FXMLLoader.load(getClass().getResource("../view/mainFormScreen.fxml"));
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.hide();
            primaryStage.setScene(new Scene(parent));
            primaryStage.setTitle("Main Form");
            primaryStage.setResizable(false);
            primaryStage.show();
        }
        else{
            return;
        }
    }

    //Close form and return to Main form without modifying the part.
    /**
     * The modifyProductCloseButton method closes the modifyProductFormScreen.fxml and returns the user to mainFormScreen.fxml.
     * This method confirms that the user would like to return without saving or modifying the Product.
     * @param event The ActionEvent that triggers this method. When the user clicks on the 'Cancel' Button this method is called.
     * @exception IOException Failed to load mainFormScreen.fxml.
     */
    @FXML
    void modifyProductCloseButton(ActionEvent event) throws IOException {
        //Confirm the user wants to cancel without modifying the product or saving modifications to the product.
        int dialogResult = JOptionPane.showConfirmDialog(null, "Cancel without modifying this Product?", "Confirm", JOptionPane.YES_NO_OPTION);
        if(!(dialogResult == JOptionPane.YES_OPTION)){
            return;
        }

        //Close - return to Main Form.
        Parent parent = FXMLLoader.load(getClass().getResource("../view/mainFormScreen.fxml"));
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.hide();
        primaryStage.setScene(new Scene(parent));
        primaryStage.setTitle("Main Form");
        primaryStage.setResizable(false);
        primaryStage.show();
    }


}

