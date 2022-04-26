package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
 * The addProductFormController class is used as the Controller for addProductFormScreen.fxml.
 * @author Mary Williams
 */
public class addProductFormController implements Initializable {
    //Text Fields
    @FXML
    private TextField addProductIDTextF;
    @FXML
    private TextField addProductNameTextF;
    @FXML
    private TextField addProductInvTextF;
    @FXML
    private TextField addProductPriceTextF;
    @FXML
    private TextField addProductMaxTextF;
    @FXML
    private TextField addProductMinTextF;
    //Search Text Field
    @FXML
    private TextField addProductSearchField;

    //Associate/Remove/Save/Cancel Buttons
    @FXML
    private Button addProductAssociateButton;
    @FXML
    private Button addProductRemoveButton;
    @FXML
    private Button addProductSaveButton;
    @FXML
    private Button addProductCancelButton;

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


    //Observable List to hold working associated Parts before fully creating Product
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    //Section to initialize table views with data/lack thereof
    /**
     * The initialize method sets the allParts and associatedParts tables to the allParts and associatedParts Observable Lists.
     * It starts the allParts table with all Parts in the system, not filtering them to any search.
     * Table placeholders are set in case there are no Parts in the system, or no Parts associated with the Product.
     * @param url Resource pointer used by initialize.
     * @param resourceBundle Locale-specific objects used by initialize.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
     * @param event The KeyEvent that triggers this method. Every time the user types or deletes in the addProductSearch Text Field this method is called.
     */
    @FXML
    void partSearchTextF(KeyEvent event) {
        if(Inventory.getAllParts().isEmpty() || addProductSearchField.getText().trim().isEmpty()){
          allPartsTableView.setItems(Inventory.getAllParts());
            allPartsTableView.setPlaceholder(new Label("There are no Parts in the system."));
        }
        else{
            ObservableList<Part> searchAllParts = FXCollections.observableArrayList();
            int searchPartID;
            String searchPartString;

            try{
                searchPartID = Integer.parseInt(addProductSearchField.getText().trim());
            }
            catch(NumberFormatException e){
                searchPartID = 0;
            }

            if(!(Inventory.lookupPart(searchPartID) == null)){
                searchAllParts.add(Inventory.lookupPart(searchPartID));
            }

            searchPartString = addProductSearchField.getText().trim().toLowerCase();
            searchAllParts.addAll(Inventory.lookupPart(searchPartString));

            allPartsTableView.setItems(searchAllParts);
            allPartsTableView.setPlaceholder(new Label("There are no Part IDs or Part Names that match that search."));
        }
    }

    //Clear Part search text field when it regains focus and reset to allParts.
    /**
     * The clearPartSearch method clears the addProductSearch Text Field and resets the allPartsTableView list to allParts.
     * Table placeholder is set in case there are no parts in the system.
     * @param event The MouseEvent that triggers this method. When the user clicks back into the addProductSearch Text Field this method is called.
     */
    @FXML
    void clearPartSearch(MouseEvent event) {
        addProductSearchField.clear();
        allPartsTableView.setItems(Inventory.getAllParts());
        allPartsTableView.setPlaceholder(new Label("There are no Parts in the system."));
    }

    //Add a Part to associated parts list - and show in bottom tableview
    /**
     * The addProductAssociateButton method associates a Part with the Product, adding it to the associatedParts Observable List, displaying it in the associatedPartsTableView.
     * If there are no parts in the system to select from, or if there is nothing selected, this method instead shows a dialog box with a related error message.
     * @param event The MouseEvent that triggers this method. When the user clicks on the 'Add' Button this method is called.
     */
    @FXML
    void addProductAssociateButton(MouseEvent event) {
        Part partToAssociate = (Part) allPartsTableView.getSelectionModel().getSelectedItem();
        //Do not attempt to associated a part if there are none to associate or nothing is selected.
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

    //Remove a Part from associated parts list - and remove from bottom tableview
    /**
     * The addProductDisassociateButton method removes an associated Part from the Product, removing it from the associatedParts Observable List and from the associatedPartsTableView.
     * If there are no associated parts, or if there is nothing selected, this method instead shows a dialog box with a related error message.
     * It confirms that the user would like to remove a Part from being associated with the Product.
     * @param event The MouseEvent that triggers this method. When the user clicks on the 'Remove Associated Part' Button this method is called.
     */
    @FXML
    void addProductDisassociateButton(MouseEvent event) {
        Part partToDisassociate = (Part) associatedPartsTableView.getSelectionModel().getSelectedItem();
        //Do not attempt to remove an associated part if there are none to remove or nothing is selected.
        if(associatedParts.isEmpty()){
            JOptionPane.showMessageDialog(null,"There are no Parts associated with the Product to remove.");
            return;
        }
        else if(partToDisassociate == null){
            JOptionPane.showMessageDialog(null,"Please select a Part to remove association with the Product.");
            return;
        }
        else{
            //Confirm associated part removal.
            int dialogResult = JOptionPane.showConfirmDialog(null, "Remove the association of this Part from the Product?", "Confirm", JOptionPane.YES_NO_OPTION);
            if(!(dialogResult == JOptionPane.YES_OPTION)){
                return;
            }

            associatedParts.remove(partToDisassociate);
        }
    }

    //Intake data and add new product to the system
    /**
     * The addProductSaveButton method checks that the user input valid data into each Text Field, creates a new Product, associates selected Parts with the new Product, and returns to mainFormScreen.fxml.
     * Data validation checks performed include checking if any fields are empty, that reasonable doubles or integers are input into the correct fields, and logical checks for Price, Inv, Min, and Max.
     * Dialog boxes with relevant error messages show up if the user has input anything incorrectly.
     * @param event The MouseEvent that triggers this method. When the user clicks on the 'Save' Button this method is called.
     * @exception IOException Failed to load mainFormScreen.fxml.
     */
    @FXML
    void addProductSaveButton(MouseEvent event) throws IOException {
        int productId;
        String productName;
        double productPrice;
        int productInv;
        int productMin;
        int productMax;

        //Take total number of all Products ever in Inventory and add +100 for unique ID
        productId = Inventory.getTotalProductsEver()+100;
        //Check if any text fields are empty, if so give a dialog box informing user to fill in every text field.
        if(addProductNameTextF.getText().trim().isEmpty() ||
           addProductPriceTextF.getText().trim().isEmpty() ||
           addProductInvTextF.getText().trim().isEmpty() ||
           addProductMinTextF.getText().trim().isEmpty() ||
           addProductMaxTextF.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null,"Please input data for each field.");
            return;
        }

        //"The user may associate zero, one, or more parts with a product." -> no need to check if associated parts is empty or not here.
        //Get Product Name and put it into string var
        productName = addProductNameTextF.getText().trim();

        //Check that Price Text Field has a valid number that can be parsed to double - else send dialog box
        try{
            productPrice = Double.parseDouble(addProductPriceTextF.getText().trim());
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

        //Check that Inv Text Field has a valid int that can be parsed - else send dialog box.
        try{
            productInv = Integer.parseInt(addProductInvTextF.getText().trim());
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null,"Please enter a valid whole number into the Inventory Field.");
            return;
        }

        //Check that Max Text Field has a valid int that can be parsed - else send dialog box.
        try{
            productMax = Integer.parseInt(addProductMaxTextF.getText().trim());
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null,"Please enter a valid whole number into the Maximum Field.");
            return;
        }

        //Check that Min Text Field has a valid int that can be parsed - else send dialog box.
        try{
            productMin = Integer.parseInt(addProductMinTextF.getText().trim());
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

        //Create new Product with gathered values -
        //Constructor parameter order: int id, String name, double price, int stock, int min, int max
        Product newProduct = new Product(productId, productName, productPrice, productInv, productMin, productMax);

        //Enhanced for loop to associate parts from associatedParts with the new Product
        if(!(associatedParts.isEmpty())){
            for(Part x : associatedParts){
                newProduct.addAssociatedPart(x);
            }
        }

        //Add Product to the Inventory allProducts List
        Inventory.addProduct(newProduct);

        //Once a new Product has been created and saved - return to Main Form Screen
        Parent parent = FXMLLoader.load(getClass().getResource("../view/mainFormScreen.fxml"));
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.hide();
        primaryStage.setScene(new Scene(parent));
        primaryStage.setTitle("Main Form");
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    //Close form and return to Main Form Screen
    /**
     * The addProductCloseButton method closes the addProductFormScreen.fxml and returns the user to mainFormScreen.fxml.
     * This method checks if the user has input anything into the text fields, sending them a confirmation that they would like to return without saving the product.
     * @param event The MouseEvent that triggers this method. When the user clicks on the 'Cancel' Button this method is called.
     * @exception IOException Failed to load mainFormScreen.fxml.
     */
    @FXML
    void addProductCloseButton(MouseEvent event) throws IOException {
        //Check if anything has been input into the text fields. This will counteract accidentally clicking cancel and losing typed in data.
        if(!(addProductNameTextF.getText().trim().isEmpty() &&
             addProductPriceTextF.getText().trim().isEmpty() &&
             addProductInvTextF.getText().trim().isEmpty() &&
             addProductMaxTextF.getText().trim().isEmpty() &&
             addProductMinTextF.getText().trim().isEmpty())){
            int dialogResult = JOptionPane.showConfirmDialog(null, "Cancel without saving this Product?", "Confirm", JOptionPane.YES_NO_OPTION);
            if(!(dialogResult == JOptionPane.YES_OPTION)){
                return;
            }
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
