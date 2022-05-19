package C195PA.Model;

/**
 * Provides access to the the company divisions and related information.
 * */
public class Division {
    /**
     * The division's id number
     * */
    int divisionId;
    /**
     * The division's name
     */
    String divisionName;
    /**
     * The division's country
     * */
    int countryId;

    /**
     * Constructor to instantiate a Division object.
     * */
    public Division(int divisionId, String divisionName,int countryId) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String toString(){
        return divisionName;
    }
}
