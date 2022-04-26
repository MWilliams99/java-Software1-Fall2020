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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The mainFormController class is used as the Controller for mainFormScreen.fxml.
 * <p>
 * <b>Compatible feature suitable to the application that would extend functionality to the next version:</b>
 * I think a compatible feature that could be added in the next version of the program would be to allow for Orders to be created.
 * Similar to how Products can have associated Parts, when an Order is created it could have associated Parts and Products.
 * It makes sense to me that a system that is used for Inventory management could extend into an Orders system.
 *
 * The functionality to extend into an Orders feature would include the ability to associate Parts and Products, with a quantity for each in the Order.
 * The user could associate a business or buyer with the order.
 * It could calculate a total cost for the order by taking the Price for each Part and multiplying it by the set quantity, the same would apply to Products in the order.
 * This Orders feature would also not allow the quantity to exceed the current Inventory Level for a selected Part or Product.
 * With this, it could remove that quantity from the Inventory Level of the Part or Product that has been added to the Order once it is confirmed.
 * </p>
 * @author Mary Williams
 */
public class mainFormController implements Initializable {

    @FXML
    private AnchorPane mainForm;
    @FXML
    private TextField partSearchField;
    @FXML
    private Button partAddButton;
    @FXML
    private Button partModifyButton;
    @FXML
    private Button partDeleteButton;
    @FXML
    private TextField productSearchField;
    @FXML
    private Button productAddButton;
    @FXML
    private Button productModifyButton;
    @FXML
    private Button productDeleteButton;
    @FXML
    private Button exitButton;

    //TableView/TableColumn declarations
    @FXML
    private TableView partTableView;
    @FXML
    private TableColumn partID;
    @FXML
    private TableColumn partName;
    @FXML
    private TableColumn partInvLevel;
    @FXML
    private TableColumn partCost;
    @FXML
    private TableView productTableView;
    @FXML
    private TableColumn productID;
    @FXML
    private TableColumn productName;
    @FXML
    private TableColumn productInvLevel;
    @FXML
    private TableColumn productCost;


    //Section to initialize table views with data/lack thereof
    /**
     * The initialize method sets the part and product tables to the allParts and allProduct Observable Lists.
     * It starts the tables with lists of all parts and all products, not filtering them to any search.
     * Table placeholders are set in case there are no parts or products yet to display.
     * @param url Resource pointer used by initialize.
     * @param resourceBundle Locale-specific objects used by initialize.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partTableView.setItems(Inventory.getAllParts());
        productTableView.setItems(Inventory.getAllProducts());

        partID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        partInvLevel.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        partCost.setCellValueFactory(new PropertyValueFactory<>("Price"));

        partTableView.setPlaceholder(new Label("There are no Parts in the system."));

        productID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        productInvLevel.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        productCost.setCellValueFactory(new PropertyValueFactory<>("Price"));

        productTableView.setPlaceholder(new Label("There are no Products in the system."));

    }


    //Part Table Search Functionality
    /**
     * The partSearchTextF method searches the allParts Observable List using Inventory.lookupPart, and sets the partTableView list to parts that match the search.
     * Table placeholder is set in case no parts are found that match the search.
     * @param event The KeyEvent that triggers this method. Every time the user types or deletes in the partSearch Text Field this method will be called.
     */
    @FXML
    void partSearchTextF(KeyEvent event) {
        if(Inventory.getAllParts().isEmpty() || partSearchField.getText().trim().isEmpty()){
            partTableView.setItems(Inventory.getAllParts());
            partTableView.setPlaceholder(new Label("There are no Parts in the system."));
        }
        else{
            ObservableList<Part> searchAllParts = FXCollections.observableArrayList();
            int searchPartID;
            String searchPartString;

            try{
                searchPartID = Integer.parseInt(partSearchField.getText().trim());
            }
            catch (NumberFormatException e){
                searchPartID = 0;
            }

            if(!(Inventory.lookupPart(searchPartID) == null)){
                searchAllParts.add(Inventory.lookupPart(searchPartID));
            }

            searchPartString = partSearchField.getText().trim().toLowerCase();

            searchAllParts.addAll(Inventory.lookupPart(searchPartString));

            partTableView.setItems(searchAllParts);
            partTableView.setPlaceholder(new Label("There are no Part IDs or Part Names that match that search."));

        }
    }
    //Clear Part Search Field when it re-gains focus and reset table to allParts.
    /**
     * The clearPartSearch method clears the part search Text Field and resets the partTableView list to allParts.
     * Table placeholder is set in case there are no parts in the system.
     * @param event The MouseEvent that triggers this method. When the user clicks back into the partSearch Text Field this method will be called.
     */
    @FXML
    void clearPartSearch(MouseEvent event) {
        partSearchField.clear();
        partTableView.setItems(Inventory.getAllParts());
        partTableView.setPlaceholder(new Label("There are no Parts in the system."));
    }

    //Product Table Search Functionality
    /**
     * The productSearchTextF method searches the allProducts Observable List using Inventory.lookupProduct, and sets the productTableView list to products that match the search.
     * Table placeholder is set in case no products are found that match the search.
     * @param event The KeyEven that triggers this method. Every time the user types or deletes in the productSearch Text Field this method will be called.
     */
    @FXML
    void productSearchTextF(KeyEvent event) {
        if(Inventory.getAllProducts().isEmpty() || productSearchField.getText().trim().isEmpty()){
            productTableView.setItems(Inventory.getAllProducts());
            productTableView.setPlaceholder(new Label("There are no Products in the system."));
        }
        else{
            ObservableList<Product> searchAllProducts = FXCollections.observableArrayList();
            int searchProductID;
            String searchProductString;

            try{
                searchProductID = Integer.parseInt(productSearchField.getText().trim());
            }
            catch(NumberFormatException e){
                searchProductID = 0;
            }

            if(!(Inventory.lookupProduct(searchProductID) == null)){
                searchAllProducts.add(Inventory.lookupProduct(searchProductID));
            }

            searchProductString = productSearchField.getText().trim().toLowerCase();
            searchAllProducts.addAll(Inventory.lookupProduct(searchProductString));

            productTableView.setItems(searchAllProducts);
            productTableView.setPlaceholder(new Label("There are no Product IDs or Product Names that match that search."));
        }
    }
    //Clear Product Search Field when it re-gains focus and reset table to allProducts
    /**
     * The clearProductSearch method clears the product search Text Field and resets the productTableView list to allProducts.
     * Table placeholder is set in case there are no products in the system.
     * @param event The MouseEvent that triggers this method. Whenever the user clicks back into the productSearch Text Field this method will be called.
     */
    @FXML
    void clearProductSearch(MouseEvent event) {
        productSearchField.clear();
        productTableView.setItems(Inventory.getAllProducts());
        productTableView.setPlaceholder(new Label("There are no Products in the system."));
    }


    //Part Table view associated buttons
    /**
     * The addPartFormButton method opens the addPartFormScreen.fxml, allowing the user to add parts to the system.
     * @param event The MouseEvent that triggers this method. When the user clicks on the 'Add' Button within the Part Section this method is called.
     * @exception IOException Failed to load addPartFormScreen.fxml.
     */
    @FXML
    void addPartFormButton(MouseEvent event) throws IOException {
        //Switch to Add Part Form.
        Parent parent = FXMLLoader.load(getClass().getResource("../view/addPartFormScreen.fxml"));
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.hide();
        primaryStage.setScene(new Scene(parent));
        primaryStage.setTitle("Add Part Form");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    /**
     * The modifyPartFormButton method opens the modifyPartFormScreen.fxml and passes the selected part to modify to the modifyPartFormController.
     * If there are no parts in the system to select from, or if there is nothing selected, this method instead shows a dialog box with a related error message.
     * @param event The MouseEvent that triggers this method. When the user clicks on the 'Modify' Button within the Part Section this method is called.
     * @exception IOException Failed to load modifyPartFormScreen.fxml.
     */
    @FXML
    void modifyPartFormButton(MouseEvent event) throws IOException {
        Part modifySelectedPart = (Part) partTableView.getSelectionModel().getSelectedItem();
        //Do not attempt to modify a Part if there are none to modify or nothing is selected.
        if(Inventory.getAllParts().isEmpty()){
            JOptionPane.showMessageDialog(null, "There are no Parts in the system to modify.");
            return;
        }
        else if(modifySelectedPart == null){
            JOptionPane.showMessageDialog(null, "Please select a Part to modify.");
            return;
        }
        else{
            //Switch to Modify Part Form - sending selected Part as parameter.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/modifyPartFormScreen.fxml"));
            modifyPartFormController controller = new modifyPartFormController(modifySelectedPart);
            loader.setController(controller);
            Parent parent = loader.load();

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.hide();
            primaryStage.setScene(new Scene(parent));
            primaryStage.setTitle("Modify Part Form");
            primaryStage.setResizable(false);
            primaryStage.show();
        }
    }
    /**
     * The deletePartButton method confirms that the user wants to delete the selected part and shows a dialog box informing the user if it succeeded or failed to delete the part.
     * If there are no parts in the system to select from, or if there is nothing selected, this method instead shows a dialog box with a related error message.
     * @param event The MouseEvent that triggers this method. When the user clicks on the 'Delete' Button within the Part Section this method is called.
     */
    @FXML
    void deletePartButton(MouseEvent event) {
        Part deleteSelectedPart = (Part) partTableView.getSelectionModel().getSelectedItem();
        //Do not attempt to delete a Part if there are none to delete or nothing is selected.
        if(Inventory.getAllParts().isEmpty()){
            JOptionPane.showMessageDialog(null, "There are no Parts in the system to delete.");
            return;
        }
        else if(deleteSelectedPart == null){
            JOptionPane.showMessageDialog(null, "Please select a Part to delete.");
            return;
        }
        else{
            //Confirm deletion of Part.
            int dialogResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete this Part?", "Confirm", JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION){
                if(Inventory.deletePart(deleteSelectedPart)){
                    JOptionPane.showMessageDialog(null, "Part deleted.");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Failed to delete part.");
                }
            }
            else{
                return;
            }
        }
    }

    //Product Table view associated buttons
    /**
     * The addProductFormButton method opens the addProductFormScreen.fxml, allowing the user to add products to the system.
     * @param event The MouseEvent that triggers this method. When the user clicks on the 'Add' Button within the Product Section this method is called.
     * @exception IOException Failed to load addProductFormScreen.fxml.
     */
    @FXML
    void addProductFormButton(MouseEvent event) throws IOException {
        //Switch to Add Product Form.
        Parent parent = FXMLLoader.load(getClass().getResource("../view/addProductFormScreen.fxml"));
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.hide();
        primaryStage.setScene(new Scene(parent));
        primaryStage.setTitle("Add Product Form");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    /**
     * The modifyProductFormButton method opens the modifyProductFormScreen.fxml and passes the selected product to modify to the modifyProductFormController.
     * If there are no products in the system to select from, or if there is nothing selected, this method instead shows a dialog box with a related error message.
     * @param event The MouseEvent that triggers this method. When the user clicks on the 'Modify' Button within the Product Section this method is called.
     * @exception IOException Failed to load modifyProductFormScreen.fxml.
     */
    @FXML
    void modifyProductFormButton(MouseEvent event) throws IOException {
        Product modifySelectedProduct = (Product) productTableView.getSelectionModel().getSelectedItem();
        //Do not attempt to modify a Product if there are none to modify or nothing is selected.
        if(Inventory.getAllProducts().isEmpty()){
            JOptionPane.showMessageDialog(null, "There are no Products in the system to modify.");
            return;
        }
        else if(modifySelectedProduct == null){
            JOptionPane.showMessageDialog(null, "Please select a Product to modify.");
        }
        else{
            //Switch to Modify Product Form - sending selected Product as parameter.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/modifyProductFormScreen.fxml"));
            modifyProductFormController controller = new modifyProductFormController(modifySelectedProduct);
            loader.setController(controller);
            Parent parent = loader.load();

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.hide();
            primaryStage.setScene(new Scene(parent));
            primaryStage.setTitle("Modify Product Form");
            primaryStage.setResizable(false);
            primaryStage.show();
        }
    }
    /**
     * The deleteProductButton method confirms that the user wants to delete the selected product and shows a dialog box informing the user if it succeeded or failed to delete the product.
     * Products that have associated parts can not be deleted, the 'Failed to delete product' error message informs the user that this is the reason why it failed.
     * If there are no products in the system to select from, or if there is nothing selected, this method instead shows a dialog box with a related error message.
     * @param event The MouseEvent that triggers this method. When the user clicks on the 'Delete' Button within the Product Section this method is called.
     */
    @FXML
    void deleteProductButton(MouseEvent event) {
        Product deleteSelectedProduct = (Product) productTableView.getSelectionModel().getSelectedItem();
        //Do not attempt to delete a Product if there are none to delete or nothing is selected.
        if(Inventory.getAllProducts().isEmpty()){
            JOptionPane.showMessageDialog(null, "There are no Products in the system to delete.");
            return;
        }
        else if(deleteSelectedProduct == null){
            JOptionPane.showMessageDialog(null,"Please select a Product to delete.");
            return;
        }
        else{
            //Confirm deletion of Product.
            int dialogResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete this Product?", "Confirm", JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION){
                if(Inventory.deleteProduct(deleteSelectedProduct)){
                    JOptionPane.showMessageDialog(null, "Product deleted.");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Failed to delete product. You cannot delete a product with associated parts.");
                }
            }
            else{
                return;
            }
        }
    }


    //Exit button to close program
    /**
     * The exitProgramButton method closes the program with a confirmation from the user.
     * @param event The MouseEvent that triggers this method. When the user clicks on the 'Exit' Button this method is called.
     */
    @FXML
    void exitProgramButton(MouseEvent event) {
        int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you would like to exit the program?", "Confirm", JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.YES_OPTION){
            System.exit(0);
        }
        else{
            return;
        }
    }
}


