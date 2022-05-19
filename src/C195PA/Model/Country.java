package C195PA.Model;

/**
 * Provides access to Country information and associated methods
 * */
public class Country {
    /**
     * The country's id number
     * */
    int countryId;
    /**
     * The country name
     * */
    String countryName;

    /**
     * Initializes a coutnry object;
     * */
    public Country(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    /**
     * @return countryId the country Id
     * */
    public int getCountryId() {
        return countryId;
    }
    /**
     * @param countryId sets the countryId
     * */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
    /**
     * @return countryName the country Id
     * */
    public String getCountryName() {
        return countryName;
    }
    /**
     * @param countryName sets the country name
     * */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * @return the country object as a string;
     * */
    public String toString(){
        return countryName;
    }
}
