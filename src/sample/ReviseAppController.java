package sample;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.event.ChangeListener;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Kien on 4/11/2017.
 */
public class ReviseAppController {
    ScreenUtil su = new ScreenUtil();
    DatabaseUtil du = new DatabaseUtil();
    @FXML private TextField form;
    @FXML private TextField ID1;
    @FXML private TextField RepID1;
    @FXML private TextField PlantReg1;
    @FXML private TextField SerialNo1;
    @FXML private TextField ApplicantName1;
    @FXML private TextField Varietal1;
    @FXML private TextField Appellation1;
    @FXML private TextField BrandName1;
    @FXML private TextField Name1;
    @FXML private TextField Formula1;
    @FXML private TextField PhoneNumber1;
    @FXML private TextField EmailAddress1;
    @FXML private CheckBox dom1111;
    @FXML private CheckBox imp1;
    @FXML private CheckBox wine1;
    @FXML private CheckBox beer1;
    @FXML private CheckBox other1;
    @FXML private TextField Vintage1;
    @FXML private TextField pH1;
    @FXML private TextField Address1;
    @FXML private TextField MailingAddress1;
    @FXML private Button Submit;
    @FXML private Button back;
    @FXML private Button find;
    @FXML private ChoiceBox formChoiceBox;

    private ArrayList<ApplicationData> formsFound = new ArrayList<>();
    private ObservableList<Integer> formsObservableList;

    private DatabaseUtil databaseUtil =  new DatabaseUtil();
    private AccountsUtil accountsUtil = new AccountsUtil();
    private ScreenUtil screenUtil = new ScreenUtil();

    @FXML
    public void initialize()throws SQLException{
        formsObservableList = FXCollections.observableArrayList();
        formsFound = databaseUtil.searchFormWithAid(databaseUtil.getAccountAid(accountsUtil.getUsername()));

        for(int i = 0; i < formsFound.size(); i ++){
            formsObservableList.add(formsFound.get(i).getFormID());
        }

        /*formChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new javafx.beans.value.ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                autoFillSelectedForm(new ActionEvent(formChoiceBox, (Node) formChoiceBox));
            }
        });*/

        formChoiceBox.setItems(formsObservableList);


        formChoiceBox.getSelectionModel().selectFirst();

        int fid = Integer.valueOf(formChoiceBox.getValue().toString().trim());
        String type;
        WineApplicationData wine;
        BeerApplicationData beer;
            type = databaseUtil.checkforType(fid);
            if (type.equals("WINE")) {
                wine = databaseUtil.fillSubmittedWineForm(fid);
                ID1.setText(Integer.toString(wine.getTtbid()));
                RepID1.setText(Integer.toString(wine.getRepid()));
                PlantReg1.setText(Integer.toString(wine.getPermit_no()));
                SerialNo1.setText(wine.getSerial());
                ApplicantName1.setText(wine.getApplicantName());
                Varietal1.setText(wine.getGrape_varietal());
                Appellation1.setText(wine.getAppellation());
                BrandName1.setText(wine.getBrand_name());
                Name1.setText(wine.getFancyName());
                Formula1.setText(wine.getFormula());
                PhoneNumber1.setText(wine.getPhone_number());
                EmailAddress1.setText(wine.getEmail());
                Vintage1.setText(Integer.toString(wine.getVintage_date()));
                pH1.setText(Double.toString(wine.getPh_level()));
                Address1.setText(wine.getAddress());
                MailingAddress1.setText(wine.getAddress());
            } else if (type.equals("BEER")) {
                beer = databaseUtil.fillSubmittedBeerForm(fid);
                ID1.setText(Integer.toString(beer.getTtbid()));
                RepID1.setText(Integer.toString(beer.getRepid()));
                PlantReg1.setText(Integer.toString(beer.getPermit_no()));
                SerialNo1.setText(beer.getSerial());
                ApplicantName1.setText(beer.getApplicantName());
                BrandName1.setText(beer.getBrand_name());
                Name1.setText(beer.getFancyName());
                Formula1.setText(beer.getFormula());
                PhoneNumber1.setText(beer.getPhone_number());
                EmailAddress1.setText(beer.getEmail());
                Address1.setText(beer.getAddress());
                MailingAddress1.setText(beer.getAddress());
            } else {
                beer = databaseUtil.fillSubmittedBeerForm(fid);
                ID1.setText(Integer.toString(beer.getTtbid()));
                RepID1.setText(Integer.toString(beer.getRepid()));
                PlantReg1.setText(Integer.toString(beer.getPermit_no()));
                SerialNo1.setText(beer.getSerial());
                ApplicantName1.setText(beer.getApplicantName());
                BrandName1.setText(beer.getBrand_name());
                Name1.setText(beer.getFancyName());
                Formula1.setText(beer.getFormula());
                PhoneNumber1.setText(beer.getPhone_number());
                EmailAddress1.setText(beer.getEmail());
                Address1.setText(beer.getAddress());
                MailingAddress1.setText(beer.getAddress());
            }
    }

    @FXML
    private void handledomBox(){
        if(dom1111.isSelected()){
            imp1.setSelected(false);
        }
    }
    @FXML
    private void handleimpBox(){
        if(imp1.isSelected()){
            dom1111.setSelected(false);
        }
    }

    @FXML
    private void handlewineBox(){
        if(wine1.isSelected()){
            beer1.setSelected(false);
            other1.setSelected(false);

        }
    }

    @FXML
    private void handlebeerBox(){
        if(beer1.isSelected()){
            wine1.setSelected(false);
            other1.setSelected(false);
        }
    }

    @FXML
    private void handleotherBox(){
        if(other1.isSelected()){
            beer1.setSelected(false);
            wine1.setSelected(false);
        }
    }
    public void goBack (javafx.event.ActionEvent event){
        screenUtil.switchScene("MainMenu.fxml", "Main Menu");

    }
    @FXML public void autoFillSelectedForm(javafx.event.ActionEvent event)throws SQLException{

        int fid = Integer.valueOf(formChoiceBox.getValue().toString().trim());
        String type;
        WineApplicationData wine;
        BeerApplicationData beer;
        if(event.getSource() == find) {
            type = databaseUtil.checkforType(fid);
            if (type.equals("WINE")) {
                wine = databaseUtil.fillSubmittedWineForm(fid);
                ID1.setText(Integer.toString(wine.getTtbid()));
                RepID1.setText(Integer.toString(wine.getRepid()));
                PlantReg1.setText(Integer.toString(wine.getPermit_no()));
                SerialNo1.setText(wine.getSerial());
                ApplicantName1.setText(wine.getApplicantName());
                Varietal1.setText(wine.getGrape_varietal());
                Appellation1.setText(wine.getAppellation());
                BrandName1.setText(wine.getBrand_name());
                Name1.setText(wine.getFancyName());
                Formula1.setText(wine.getFormula());
                PhoneNumber1.setText(wine.getPhone_number());
                EmailAddress1.setText(wine.getEmail());
                Vintage1.setText(Integer.toString(wine.getVintage_date()));
                pH1.setText(Double.toString(wine.getPh_level()));
                Address1.setText(wine.getAddress());
                MailingAddress1.setText(wine.getAddress());
            } else if (type.equals("BEER")) {
                beer = databaseUtil.fillSubmittedBeerForm(fid);
                ID1.setText(Integer.toString(beer.getTtbid()));
                RepID1.setText(Integer.toString(beer.getRepid()));
                PlantReg1.setText(Integer.toString(beer.getPermit_no()));
                SerialNo1.setText(beer.getSerial());
                ApplicantName1.setText(beer.getApplicantName());
                BrandName1.setText(beer.getBrand_name());
                Name1.setText(beer.getFancyName());
                Formula1.setText(beer.getFormula());
                PhoneNumber1.setText(beer.getPhone_number());
                EmailAddress1.setText(beer.getEmail());
                Address1.setText(beer.getAddress());
                MailingAddress1.setText(beer.getAddress());
            } else {
                beer = databaseUtil.fillSubmittedBeerForm(fid);
                ID1.setText(Integer.toString(beer.getTtbid()));
                RepID1.setText(Integer.toString(beer.getRepid()));
                PlantReg1.setText(Integer.toString(beer.getPermit_no()));
                SerialNo1.setText(beer.getSerial());
                ApplicantName1.setText(beer.getApplicantName());
                BrandName1.setText(beer.getBrand_name());
                Name1.setText(beer.getFancyName());
                Formula1.setText(beer.getFormula());
                PhoneNumber1.setText(beer.getPhone_number());
                EmailAddress1.setText(beer.getEmail());
                Address1.setText(beer.getAddress());
                MailingAddress1.setText(beer.getAddress());
            }
        }
    }

    public void submitAgain()throws SQLException{
        int fid = Integer.valueOf(formChoiceBox.getValue().toString().trim());
        int ttbid = Integer.parseInt(ID1.getText());
        int repid = Integer.parseInt(RepID1.getText());
        String serial = SerialNo1.getText();
        String address = Address1.getText();
        String fancyName = Name1.getText();
        String formula = Formula1.getText();
        int permit_no = Integer.parseInt(PlantReg1.getText());
        String source_of_product = "";
        String type_of_product = "";
        String brand_name = BrandName1.getText();
        String phone_number = PhoneNumber1.getText();
        String email = EmailAddress1.getText();
        String applicantName = ApplicantName1.getText();
        String alcoholType = "";
        String date ="";
        AcceptanceInformation ac = null;
        String info = "";
        String content = "";

        if (dom1111.isSelected()) {
            source_of_product = "DOMESTIC";
        } else if (imp1.isSelected()) {
            source_of_product = "IMPORTED";
        }if(!(dom1111.isSelected()) && !(imp1.isSelected())) {
            su.createAlertBox("ERROR", "Please select Domestic or Imported");

        }

        if (wine1.isSelected()) {
            type_of_product = "WINE";
            alcoholType = "WINE";
            int vintage_date = Integer.parseInt(Vintage1.getText());
            double ph_level = Double.parseDouble(pH1.getText());
            String grape_varietal = Varietal1.getText();
            String appellation = Appellation1.getText();
            WineApplicationData a = new WineApplicationData(fid, ac, ttbid, repid, serial, address, fancyName, formula, grape_varietal, appellation, permit_no, info,
                    source_of_product, type_of_product, brand_name, phone_number, email, date, applicantName, alcoholType, content, vintage_date, ph_level);
            du.resubmitWine(fid, a);
        } else if (beer1.isSelected()) {
            type_of_product = "MALT BEVERAGES";
            alcoholType = "MALT BEVERAGES";
            BeerApplicationData a = new BeerApplicationData(fid, ac, ttbid, repid, serial, address, fancyName, formula, permit_no, info,
                    source_of_product, type_of_product, brand_name, phone_number, email, date, applicantName, alcoholType, content);
            du.resubmitBeer(fid, a);
        } else if (other1.isSelected()) {
            type_of_product = "DISTILLED SPIRITS";
            alcoholType = "DISTILLED SPIRITS";
            BeerApplicationData a = new BeerApplicationData(fid, ac, ttbid, repid, serial, address, fancyName, formula, permit_no, info,
                    source_of_product, type_of_product, brand_name, phone_number, email, date, applicantName, alcoholType, content);
            du.resubmitBeer(fid, a);
        }else{
            su.createAlertBox("ERROR", "Please select the type of product");
        }



    }



}