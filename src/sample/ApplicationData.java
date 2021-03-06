package sample;
import java.util.Date;

/**
 * Abstract Class for Application Data.
 */
public class ApplicationData extends SubmissionForm{

    private int ttbid;
    private int repid;
    private String serial;
    private String address;
    private String fancyName;
    private String formula;

    private int permit_no;
    private String infoOnBottle;
    private String source_of_product;
    private String type_of_product;
    private String brand_name;
    private String phone_number;
    private String email;
    private String date;
    public String applicantName;
    private String alcoholType;
    private String alcoholContent;

    public ApplicationData(int formID, AcceptanceInformation acceptanceInfo, int ttbid, int repid, String serial, String address, String fancyName,
                           String formula, int permit_no,
                           String infoOnBottle, String source_of_product, String type_of_product,
                           String brand_name, String phone_number, String email, String date,
                           String applicantName, String alcoholType, String alcoholContent) {
        super(formID, acceptanceInfo);
        this.ttbid = ttbid;
        this.repid = repid;
        this.serial = serial;
        this.address = address;
        this.fancyName = fancyName;
        this.formula = formula;
        this.permit_no = permit_no;
        this.infoOnBottle = infoOnBottle;
        this.source_of_product = source_of_product;
        this.type_of_product = type_of_product;
        this.brand_name = brand_name;
        this.phone_number = phone_number;
        this.email = email;
        this.date = date;
        this.applicantName = applicantName;
        this.alcoholType = alcoholType;
        this.alcoholContent = alcoholContent;
    }

    public String getSource_of_product() {
        return source_of_product;
    }

    public String getType_of_product() {
        return type_of_product;
    }

    public String getAlcoholType() {
        return alcoholType;
    }
    public int getTTbId() {
        return ttbid;
    }


    public int getPermit_no() {
        return permit_no;
    }


    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getAlcoholContent() {
        return alcoholContent;
    }

    public void setAlcoholContent(String alcoholContent) {
        this.alcoholContent = alcoholContent;
    }

    public int getTtbid() {
        return ttbid;
    }

    public int getRepid() {
        return repid;
    }

    public String getSerial() {
        return serial;
    }

    public String getAddress() {
        return address;
    }

    public String getFancyName() {
        return fancyName;
    }

    public String getFormula() {
        return formula;
    }

    public String getInfoOnBottle() {
        return infoOnBottle;
    }
}
