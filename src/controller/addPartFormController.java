package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

import javax.swing.*;
import java.io.IOException;

/**
 * The addPartFormController class is used as the Controller for addPartFormScreen.fxml.
 * @author Mary Williams
 */
public class addPartFormController {

    @FXML
    private AnchorPane addPartForm;
    @FXML
    private Label addPartMachineOrCompanyLabel;
    @FXML
    private TextField addPartIDTextF;
    @FXML
    private TextField addPartNameTextF;
    @FXML
    private TextField addPartInvTextF;
    @FXML
    private TextField addPartPriceTextF;
    @FXML
    private TextField addPartMaxTextF;
    @FXML
    private TextField addPartMachineOrCompanyTextF;
    @FXML
    private TextField addPartMinTextF;

    @FXML
    private Button addPartSaveButton;
    @FXML
    private Button addPartCancelButton;

    @FXML
    private RadioButton addPartInhouseRadButton;
    @FXML
    private ToggleGroup addPartToggleG;
    @FXML
    private RadioButton addPartOutsourcedRadButton;
    

    //Intake data and add new part to the system
    /**
     * The addPartSave method checks that the user input valid data into each Text Field, creates a new Part, and returns to mainFormScreen.fxml.
     * Data validation checks performed include checking if any fields are empty, that reasonable doubles or integers are input into the correct fields, and logical checks for Price, Inv, Min, and Max.
     * Dialog boxes with relevant error messages show up if the user has input anything incorrectly.
     * This method detects if the In-house or Outsourced Radio Button is selected, and creates either an InHouse Part or Outsourced Part, adding the Part to Inventory once created.
     * @param event The MouseEvent that triggers this method. When the user clicks on the 'Save' Button this method is called.
     * @exception IOException Failed to load mainFormScreen.fxml.
     */
    @FXML
    void addPartSave(MouseEvent event) throws IOException {
        int partId;
        String partName;
        double partPrice;
        int partInv;
        int partMin;
        int partMax;

        //Take total number of all Parts ever in Inventory and add +100 for unique ID.
        partId = (Inventory.getTotalPartsEver())+100;
        //Check if any text fields are empty, if so give a dialog box informing user to fill in every text field.
        if(addPartNameTextF.getText().trim().isEmpty() ||
           addPartPriceTextF.getText().trim().isEmpty() ||
           addPartInvTextF.getText().trim().isEmpty() ||
           addPartMaxTextF.getText().trim().isEmpty() ||
           addPartMinTextF.getText().trim().isEmpty() ||
           addPartMachineOrCompanyTextF.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null,"Please input data for each field.");
            return;
        }

        partName = addPartNameTextF.getText().trim();

        //Check that Price Text Field has a valid number that can be parsed to Double partPrice - else send dialog box.
        try{
            partPrice = Double.parseDouble(addPartPriceTextF.getText().trim());
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null,"Please enter a valid number into the Price Field.");
            return;
        }

        //Check that Price Text Field is not negative - else send dialog box.
        if(partPrice <= 0){
            JOptionPane.showMessageDialog(null,"Please enter a value greater than 0 into the Price Field.");
            return;
        }

        //Check that Inv Text Field has a valid integer that can be parsed to Int partInv - else send dialog box.
        try{
            partInv = Integer.parseInt(addPartInvTextF.getText().trim());
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null,"Please enter a valid whole number into the Inventory Field.");
            return;
        }

        //Check that Max Text Field has a valid integer that can be parsed to Int partMax - else send dialog box.
        try{
            partMax= Integer.parseInt(addPartMaxTextF.getText().trim());
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null,"Please enter a valid whole number into the Maximum Field.");
            return;
        }

        //Check that Min Text Field has a valid integer that can be parsed to Int partMin - else send dialog box.
        try{
            partMin = Integer.parseInt(addPartMinTextF.getText().trim());
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null,"Please enter a valid whole number into the Minimum Field.");
            return;
        }


        //Check that Min is greater than or equal to (>=) 0. It makes no sense to have negative Inventory or to allow Inventory level to be negative.
        if(partMin < 0){
            JOptionPane.showMessageDialog(null, "Please enter a value greater than or equal to 0 into the Minimum Field.");
            return;
        }

        //Check that Min is less than Max - send dialog box with error if incorrect.
        if(!(partMin < partMax)){
            JOptionPane.showMessageDialog(null,"Minimum must be less than Maximum.");
            return;
        }

        //Check that Inv is between Min and Max - send dialog box with error if incorrect.
        if (!(partInv >= partMin && partInv <= partMax)) {
            JOptionPane.showMessageDialog(null,"Inventory must be a value between Minimum and Maximum.");
            return;
        }


        //Check whether In-house or Outsourced Radio Button is selected.
        if(addPartInhouseRadButton.isSelected()){
            //In-house Radio Button is selected - We are making an In-house Part using a Machine ID
            int partMachineID;
            //Check that Machine ID Text Field has a valid integer that can be parsed to Int partMachineID - else send dialog box.
            try{
                partMachineID = Integer.parseInt(addPartMachineOrCompanyTextF.getText().trim());
            }
            catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Please enter a valid whole number into the Machine ID Field.");
                return;
            }

            //Once past the try..catch, partMachineID has a valid int. Now the In-house part is created.
            //Constructor parameter order: partID, partName, partPrice, partInv, partMin, partMax, partMachineID
            InHouse newInhousePart = new InHouse(partId, partName, partPrice, partInv, partMin, partMax, partMachineID);
            //Add newInhousePart to the Inventory allParts ObservableList using addPart()
            Inventory.addPart(newInhousePart);
        }
        else if(addPartOutsourcedRadButton.isSelected()){
            //Out-sourced Radio Button is selected - We are making an Outsourced Part using a Company Name
            //Put Company Name from the Text Field into String partCompanyName
            String partCompanyName = addPartMachineOrCompanyTextF.getText();

            //Constructor parameter order: partID, partName, partPrice, partInv, partMin, partMax, partCompanyName
            Outsourced newOutsourcedPart = new Outsourced(partId,partName, partPrice, partInv, partMin, partMax, partCompanyName);
            //Add newOutsourcedPart to the Inventory allParts ObservableList using addPart()
            Inventory.addPart(newOutsourcedPart);
        }


        //Once a newPart has been created and saved - return to Main Form screen
        Parent parent = FXMLLoader.load(getClass().getResource("../view/mainFormScreen.fxml"));
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.hide();
        primaryStage.setScene(new Scene(parent));
        primaryStage.setTitle("Main Form");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    // Close form and return to Main Form Screen
    /**
     * The addPartCloseButton method closes the addPartFormScreen.fxml and returns the user to mainFormScreen.fxml.
     * This method checks if the user input anything into the text fields, sending them a confirmation that they would like to return without saving the part.
     * <p>
     * <b>Detailed description of a logical or runtime error that you corrected and how you corrected it, above the line of code you are referring to:</b>
     * A logical error I had when writing my code was that I messed up the code in the following if statement that is used to check if any text fields are not empty.
     * What I was hoping to achieve was to make it check if any of the text fields were not empty, if any of them had text in them I wanted it to send the user a
     * confirmation that they would like to return to the main form without saving the Part they had started creating.
     * While this functionality was not explicitly part of the project requirements, I thought it fit in nicely with the requirement to have the user confirm when
     * they are about to remove or delete an item.
     * This helps protect the user from accidentally canceling out of the add part form, and losing data they had instead intended to save as a new Part.
     *
     * I originally wrote the if statement with logical OR operators instead of logical AND operators.
     * My mistake was thinking that this bit of code would work similarly to how I wrote the code for saving a new part, where it checks if any of the text fields are empty.
     * However, when using the logical OR operator, only one value needs to equal true for it to still run.
     * This led my program to skip over checking all other text fields if the first one was empty.
     * This works for where I wanted it to find empty text fields before executing a bit of code, but not when I wanted it to find non-empty text fields.
     * By changing the code to use logical AND operators, it checks if every text field is empty.
     * This allows me to combine it with the NOT operator, so that if any of the text fields are not empty, it prompts the user with a confirmation message.
     * If the text fields are all empty, it skips over the confirmation and does not bother the user with an unnecessary question.
     * </p>
     * @param event The MouseEvent that triggers this method. When the user clicks on the 'Cancel' Button this method is called.
     * @exception IOException Failed to load mainFormScreen.fxml.
     */
    @FXML
    void addPartCloseButton(MouseEvent event) throws IOException {
        //Check if anything has been input into the text fields before attempting to close the form. This will counteract accidentally clicking cancel and losing typed in data.
        if(!(addPartNameTextF.getText().trim().isEmpty() &&
             addPartPriceTextF.getText().trim().isEmpty() &&
             addPartInvTextF.getText().trim().isEmpty() &&
             addPartMaxTextF.getText().trim().isEmpty() &&
             addPartMinTextF.getText().trim().isEmpty() &&
             addPartMachineOrCompanyTextF.getText().trim().isEmpty())){
                int dialogResult = JOptionPane.showConfirmDialog(null, "Cancel without saving this Part?", "Confirm", JOptionPane.YES_NO_OPTION);
                if(!(dialogResult == JOptionPane.YES_OPTION)){
                    return;
                }
        }
        //Close - return back to Main Form
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
     * The addPartInhouseRadShow method changes the text of addPartMachineOrCompanyLabel to 'Machine ID'.
     * @param event The MouseEvent that triggers this method. When the user clicks on the 'In-House' Radio Button this method is called.
     */
    @FXML
    void addPartInhouseRadShow(MouseEvent event) {
        addPartMachineOrCompanyLabel.setText("Machine ID");
    }

    /**
     * The addPartOutsourcedRadShow method changes the text of addPartMachineOrCompanyLabel to 'Company Name'.
     * @param event The MouseEvent that triggers this method. When the user clicks on the 'Outsourced' Radio Button this method is called.
     */
    @FXML
    void addPartOutsourcedRadShow(MouseEvent event) {
        addPartMachineOrCompanyLabel.setText("Company Name");
    }
}

