package sample;


import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sun.java2d.pipe.AlphaPaintPipe;

import java.lang.reflect.*;
import java.sql.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * Controller for Application Review Screen.
 */
public class ApplicationReviewController extends DatabaseUtil{
    @FXML
    private Button approve, reject, goBack;
    @FXML
    private TextField repID, registryNo, prodSource, prodType, address,  phoneNo, email, dateApp, nameApp;
    @FXML private  TextArea commentsField;

    private Connection conn = connect();
    private DatabaseUtil dbUtil = new DatabaseUtil();
    private ScreenUtil screenUtil = new ScreenUtil();
    private AccountsUtil accountsUtil = new AccountsUtil();
    private String username = accountsUtil.getUsername();
    int numberOfApps;
    private ApplicationData thisForm;


    //for when switching to this scene from inbox
    @FXML
    public void initialize() throws SQLException{
        List<ApplicationData> listForms = dbUtil.searchFormWithGovId(dbUtil.getAccountAid(username));
        numberOfApps = listForms.size();
        thisForm = listForms.get(0);
        repID.setText(Integer.toString(thisForm.getRepid()));
        registryNo.setText(Integer.toString(thisForm.getPermit_no()));
        prodSource.setText(thisForm.getSource_of_product());
        prodType.setText(thisForm.getAlcoholType());
        address.setText(thisForm.getAddress());
        phoneNo.setText(thisForm.getPhone_number());
        email.setText(thisForm.getEmail());
        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        dateApp.setText(thisForm.getDate());
        nameApp.setText(thisForm.getApplicantName());
}



    @FXML
    void setGoBack(ActionEvent event){
        screenUtil.switchScene("WorkFlow.fxml", "Main Menu");
    }

    @FXML
    //TODO: Fix setApprove - needs correct query fields for sql
    /**
     * Sets an Application status to "APPROVED" and adds comments to the Application.
     */

    void setAccepted() throws SQLException{

        Statement stm;
        String sql;
        stm = conn.createStatement();
        //get comments
        String comments = commentsField.getText();
        //update alcohol status
        int FID = thisForm.getFormID();
        sql = "UPDATE FORM SET FORM.STATUS = 'ACCEPTED' WHERE FORM.FID = " + FID;
        stm.executeUpdate(sql);

        stm.close();
        conn.close();

        ApplicationData appData = dbUtil.searchFormWithFid(FID).get(0);

        dbUtil.addAlcohol(appData.getBrand_name(), "", "", 0, 0, "", 0, 1, "", 0,"", 1, "", "");

        nextApplication();
    }

    @FXML
    /**
     * Sets an Application status to "REJECTED" and adds comments to the Application.
     */

    public void setReject(ActionEvent event) throws SQLException {
        Statement stm;
        String sql;
        stm = conn.createStatement();
        //get comments
        String comments = commentsField.getText();
        //update alcohol status
        int FID = thisForm.getFormID();
        sql = "UPDATE FORM SET FORM.STATUS = 'REJECTED' WHERE FORM.FID = " + FID;
        stm.executeUpdate(sql);

        stm.close();
        conn.close();

        nextApplication();
    }

    public void nextApplication(){

        if(numberOfApps <= 1){
           screenUtil.createAlertBox("No more assigned forms", "There are no more forms assigned to you.");
        }else{
           screenUtil.switchScene("ApplicationReview.fxml","Application Review");
        }
    }

    /**
     * Gets a list of all Applications that have the status "UNASSIGNED".
     *
     * @return Returns an ArrayList of the IDs of the unassigned Applications. The IDs are represented
     * by Strings.
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    /*
    private static ArrayList<String> getUnassigForms() throws ClassNotFoundException, SQLException {
        Connection conn = connect();
        Statement stm;
        stm = conn.createStatement();
        String sql = "SELECT * FROM ALCOHOL WHERE ALCOHOL.STATUS = 'Unassigned'"; // Use Select _ from _ Where _ format and set this statement = sql
        ArrayList<String> unassforms = new ArrayList<>();
        ResultSet unassAlc = stm.executeQuery(sql);
        ResultSetMetaData rsmd = unassAlc.getMetaData();
        int columnCount = rsmd.getColumnCount();
        while(unassAlc.next()){
            int i = 1;
            while(i <= columnCount){
                unassforms.add(unassAlc.getString("id"));
            }
        }
        return unassforms;
    }
    */
    /**
     * Finds the government account in the database with the least number of applications in its
     * inbox.
     *
     * @return Returns the government Account with the smallest number of applications in its
     * inbox.
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    /*
    Account getSmallWorker() throws ClassNotFoundException, SQLException{//TODO: find out fields + name for govt. worker
        Statement stm;
        stm = conn.createStatement();
        String sql = "SELECT AID.MIN(CNT(FID)) FROM REVIEWS";
        ResultSet smallWorker = stm.executeQuery(sql);
        Account worker = new Account(smallWorker.getString("id"), 0,
                ArrayToArrayList((String[]) smallWorker.getArray("inbox").getArray()));
        return worker;
    }
    */

    /**
     * Converts an Array object to an ArrayList object. The datatype stored is String.
     *
     * @param input The Array of Strings to be converted to an ArrayList.
     * @return Returns an ArrayList of Strings.
     */
/*

    ArrayList<String> ArrayToArrayList(String[] input){
        ArrayList<String> returnThing = new ArrayList<String>();
        for(int i=0; i<input.length; i++){
            returnThing.add(input[i]);
        }
        return returnThing;
    }
*/

    /**
     * Takes a government account and an application ID and assigns the application to
     * the specified account by adding the ID string to the account's inbox.
     *
     * @param w          Government account that the application will be assigned to.
     * @param apptoassgn String representing the ID of the application to be assigned.
     * @throws ClassNotFoundException
     * @throws SQLException
     */

    /*
    void addToInbox(Account w, String apptoassgn) throws ClassNotFoundException, SQLException{
        Statement stm;
        stm = conn.createStatement();
        //update alcohol status
        String sql = "UPDATE ALCOHOL SET status = 'assigned', aid = " + w.getUsername() + "WHERE FORM.FID = "+ apptoassgn;
        stm.executeUpdate(sql);
        //update inbox for worker
        sql = "UPDATE REVIEWS SET inbox = " + w.getInbox().add(apptoassgn) +" WHERE id = " + w.getUsername() +"";//TODO: Check syntax on set inbox
        stm.executeUpdate(sql);
    }
    */
    /**
     * Gets a list of unassigned forms then assigns them to government account inboxes.
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    /*
    void addAllUnassigned() throws ClassNotFoundException, SQLException{
        ArrayList<String> unassigForms = getUnassigForms();
        for (int i = 0; i < unassigForms.size(); i++) {
            addToInbox(getSmallWorker(), unassigForms.get(i));
        }
    }
    */


}
