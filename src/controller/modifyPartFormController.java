package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The modifyPartFormController class is used as the Controller for modifyPartFormScreen.fxml.
 * @author Mary Williams
 */
public class modifyPartFormController implements Initializable {

    @FXML
    private AnchorPane modifyPartForm;
    @FXML
    private Label modifyPartMachineOrCompanyLabel;
    @FXML
    private TextField modifyPartIDTextF;
    @FXML
    private TextField modifyPartNameTextF;
    @FXML
    private TextField modifyPartInvTextF;
    @FXML
    private TextField modifyPartPriceTextF;
    @FXML
    private TextField modifyPartMaxTextF;
    @FXML
    private TextField modifyPartMachineOrCompanyTextF;
    @FXML
    private TextField modifyPartMinTextF;

    @FXML
    private RadioButton modifyPartInhouseRadButton;
    @FXML
    private ToggleGroup modifyPartToggleG;
    @FXML
    private RadioButton modifyPartOutsourcedRadButton;

    @FXML
    private Button modifyPartSaveButton;
    @FXML
    private Button modifyPartCancelButton;


    //Way to pass which part from MainForm Table View to modify.
    Part partToModify;
    int partIndex;
    /**
     * The modifyPartFormController method is used to create a Part within the Controller to work with as modifications are made.
     * @param modifySelectedPart The Part selected in mainFormScreen.fxml that is passed when modifyPartFormController is called, used to initialize Text Fields and gather the Part ID.
     */
    public modifyPartFormController(Part modifySelectedPart) {
        partToModify = modifySelectedPart;
        partIndex = Inventory.getAllParts().indexOf(modifySelectedPart);
    }


    //Section to initialize TextFields with data from partToModify - The selected Part from Main Form Screen Part Table
    /**
     * The initialize method takes data from the partToModify to initialize values into the Text Fields and determine which Radio Button is selected.
     * @param url Resource pointer used by initialize.
     * @param resourceBundle Locale-specific objects used by initialize.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(partToModify instanceof InHouse){
            //Set In-houseRadButton to selected and Change Label to Machine ID
            modifyPartInhouseRadButton.setSelected(true);
            modifyPartMachineOrCompanyLabel.setText("Machine ID");

            //Set modifyPartMachineOrCompanyTextF to In-house Machine ID
            int partMachineID = ((InHouse) partToModify).getMachineId();
            modifyPartMachineOrCompanyTextF.setText(Integer.toString(partMachineID));
        }
        if(partToModify instanceof Outsourced){
            //Set OutsourcedRadButton to selected and Change Label to Company Name
            modifyPartOutsourcedRadButton.setSelected(true);
            modifyPartMachineOrCompanyLabel.setText("Company Name");

            //Set modifyPartMachineOrCompanyTextF to Outsourced Company name
            String partCompanyName = ((Outsourced) partToModify).getCompanyName();
            modifyPartMachineOrCompanyTextF.setText(partCompanyName);
        }

        //Outside of the if statements - set all other TextFields to data - shared between In-house/outsourced
        //int id, String name, double price, int stock, int min, int max
        int partId = partToModify.getId();
        String partName = partToModify.getName();
        double partPrice = partToModify.getPrice();
        int partInv = partToModify.getStock();
        int partMin = partToModify.getMin();
        int partMax = partToModify.getMax();

        modifyPartIDTextF.setText(Integer.toString(partId));
        modifyPartNameTextF.setText(partName);
        modifyPartPriceTextF.setText(Double.toString(partPrice));
        modifyPartInvTextF.setText(Integer.toString(partInv));
        modifyPartMinTextF.setText(Integer.toString(partMin));
        modifyPartMaxTextF.setText(Integer.toString(partMax));

    }


    //Intake data and update part in the system
    /**
     * The modifyPartSave method checks that the user input valid data into each Text Field, confirms the user would like to save the modifications, updates the Part, and returns to mainFormScreen.fxml.
     * Data validation checks performed include checking if any fields are empty, that reasonable doubles or integers are input into the correct fields, and logical checks for Price, Inv, Min, and Max.
     * Dialog boxes with relevant error messages show up if the user has input anything incorrectly.
     * This method detects if In-house or Outsourced Radio Button is selected, and creates either an InHouse Part or Outsourced Part, replacing the old Part in the Inventory once created.
     * A confirmation dialog box appears confirming that the user would like to save modifications to the Part.
     * @param event The MouseEvent that triggers this method. When the user clicks on the 'Save' Button this method is called.
     * @exception IOException Failed to load mainFormScreen.fxml.
     */
    @FXML
    void modifyPartSave(MouseEvent event) throws IOException {
        int partID = partToModify.getId();
        String partName;
        double partPrice;
        int partInv;
        int partMin;
        int partMax;
        //Check if any text fields are empty, if so give a dialog box informing user to fill in every text field.
        if( modifyPartNameTextF.getText().trim().isEmpty() ||
            modifyPartPriceTextF.getText().trim().isEmpty() ||
            modifyPartInvTextF.getText().trim().isEmpty() ||
            modifyPartMaxTextF.getText().trim().isEmpty() ||
            modifyPartMinTextF.getText().trim().isEmpty() ||
            modifyPartMachineOrCompanyTextF.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "Please input data for each field.");
            return;
        }

        partName = modifyPartNameTextF.getText().trim();

        //Check that Price Text Field has a valid number that can be parsed to Double partPrice - else send dialog box.
        try{
            partPrice = Double.parseDouble(modifyPartPriceTextF.getText().trim());
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Please enter a valid number into the Price Field.");
            return;
        }

        //Check that Price Text Field is not negative - else send dialog box.
        if(partPrice <= 0){
            JOptionPane.showMessageDialog(null,"Please enter a value greater than 0 into the Price Field.");
            return;
        }

        //Check that Inv Text Field has a valid Integer that can be parsed into int partInv - else send dialog box.
        try{
            partInv = Integer.parseInt(modifyPartInvTextF.getText().trim());
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Please enter a valid whole number into the Inventory Field");
            return;
        }

        //Check that Max Text Field has a valid Integer that can be parsed into int partMax - else send dialog box.
        try{
            partMax = Integer.parseInt(modifyPartMaxTextF.getText().trim());
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Please enter a valid whole number into the Maximum Field");
            return;
        }

        //Check that Min Text Field has a valid Integer that can be parsed into int partMin - else send dialog box.
        try{
            partMin = Integer.parseInt(modifyPartMinTextF.getText().trim());
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Please enter a valid whole number into the Minimum Field");
            return;
        }


        //Check that Min is greater than or equal to (>=) 0. It makes no sense to have negative Inventory or to allow Inventory level to be negative.
        if(partMin < 0){
            JOptionPane.showMessageDialog(null, "Please enter a value greater than or equal to 0 into the Minimum Field.");
            return;
        }

        //Check that Min is less than Max - send dialog box with error if incorrect.
        if(!(partMin < partMax)){
            JOptionPane.showMessageDialog(null, "Minimum must be less than Maximum.");
            return;
        }

        //Check that Inv is between Min and Max - send dialog box with error if incorrect.
        if(!(partInv >= partMin && partInv <= partMax)){
            JOptionPane.showMessageDialog(null, "Inventory must be a value between Minimum and Maximum.");
            return;
        }


        //Check whether In-house or Outsourced Radio Button is selected.
        if(modifyPartInhouseRadButton.isSelected()){
            //In-house Radio Button is selected - We are making an In-house Part using a Machine ID.
            int partMachineID;
            //Check that Machine ID Text Field has a valid integer that can be parsed to Int partMachineId - else send dialog box.
            try{
               partMachineID = Integer.parseInt(modifyPartMachineOrCompanyTextF.getText().trim());
            }
            catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Please enter a valid whole number into the Machine ID Field.");
                return;
            }

            //Constructor parameter order: partID, partName, partPrice, partInv, partMin, partMax, partMachineID
            InHouse partModified = new InHouse(partID, partName, partPrice, partInv, partMin, partMax, partMachineID);

            //Confirm user wants to update/modify the part.
            int dialogResult = JOptionPane.showConfirmDialog(null,"Save these modifications to the Part?","Confirm",JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION){
                //Save Changes to the part using inventory.updatePart(index, part)
                Inventory.updatePart(partIndex, partModified);

                //Switch back to Main Form Screen
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
        else if(modifyPartOutsourcedRadButton.isSelected()){
            //Out-sourced Radio Button is selected - We are making an Outsourced Part using a Company Name.
            //Put Company Name from the Text Field into String partCompanyName.
            String partCompanyName = modifyPartMachineOrCompanyTextF.getText();

            //Constructor parameter order: partID, partName, partPrice, partInv, partMin, partMax, partCompanyName
            Outsourced partModified = new Outsourced(partID, partName, partPrice, partInv, partMin, partMax, partCompanyName);

            //Confirm user wants to update/modify the part.
            int dialogResult = JOptionPane.showConfirmDialog(null,"Save these modifications to the Part?","Confirm",JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION){
                //Save Changes to the part using inventory.updatePart(index, part)
                Inventory.updatePart(partIndex, partModified);

                //Switch back to Main Form Screen
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
    }


    /**
     * The modifyPartCloseButton method closes the modifyPartFormScreen.fxml and returns the user to mainFormScreen.fxml.
     * This method confirms that the user would like to return without saving or modifying the part.
     * @param event The MouseEvent that triggers this method. When the user clicks on the 'Cancel' Button this method is called.
     * @exception IOException Failed to load mainFormScreen.fxml.
     */
    @FXML
    void modifyPartCloseButton(MouseEvent event) throws IOException {
        //Confirm the user would like to exit without modifying the part or saving any modifications made to the part.
        int dialogResult = JOptionPane.showConfirmDialog(null, "Cancel without modifying this Part?", "Confirm", JOptionPane.YES_NO_OPTION);
        if(!(dialogResult == JOptionPane.YES_OPTION)){
            return;
        }

        //Close - return back to Main Form.
        Parent parent = FXMLLoader.load(getClass().getResource("../view/mainFormScreen.fxml"));
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.hide();
        primaryStage.setScene(new Scene(parent));
        primaryStage.setTitle("Main Form");
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    //Switch label based on radio button selection.
    /**
     * The modifyPartInhouseRadShow method changes the text of modifyPartMachineOrCompanyLabel to 'Machine ID'.
     * @param event The MouseEvent that triggers this method. When the user clicks on the 'In-House' Radio Button this method is called.
     */
    @FXML
    void modifyPartInhouseRadShow(MouseEvent event) {
        modifyPartMachineOrCompanyLabel.setText("Machine ID");
    }

    /**
     * The modifyPartOutsourcedRadShow method changes the text of modifyPartMachineOrCompanyLabel to 'Company Name'.
     * @param event The MouseEvent that triggers this method. When the user clicks on the 'Outsourced' Radio Button this method is called.
     */
    @FXML
    void modifyPartOutsourcedRadShow(MouseEvent event) {
        modifyPartMachineOrCompanyLabel.setText("Company Name");
    }


}
