package C195PA.DAO;

import C195PA.Model.Country;
import C195PA.Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

import static C195PA.DAO.Query.getResult;
import static C195PA.DAO.Query.makeQuery;

/**
 * Provides access to the database for location information
 * */
public class LocationsDAO {

    /**
     * @param countryID divisionId to search for a specific division object
     * @return country returns a country object
     * */
    public static Country getCountry(int countryID) {
        Country country;
        try {
            String getCountryQuery = "SELECT * FROM Countries WHERE Country_ID = " + countryID;
            makeQuery(getCountryQuery);
            ResultSet countryResults = getResult();
            countryResults.next();
            country = new Country(
                    countryResults.getInt("Country_id"),
                    countryResults.getString("Country"));
            return country;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * @return allCountries returns an ObservableList of all country objects
     * */
    public static ObservableList<Country> getCountries(){
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        Country country;
        try{
        String getAllCountriesQuery = "SELECT * FROM COUNTRIES";
        makeQuery(getAllCountriesQuery);
        ResultSet countryResults = getResult();
        while (countryResults.next()){
            country = new Country(
                    countryResults.getInt("country_Id"),
                    countryResults.getString("country"));
            allCountries.add(country);
        }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return allCountries;
    }
    /**
     * @param divisionID divisionId to search for a specific division object
     * @return division returns a division object
     * */
    public static Division getDivision(int divisionID) {
        Division division;
        try {
            String getDivisionQuery = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE Division_ID = " + divisionID;
            makeQuery(getDivisionQuery);
            ResultSet divisionResults = getResult();
            divisionResults.next();
            division = new Division(
                    divisionResults.getInt("division_Id"),
                    divisionResults.getString("division"),
                    divisionResults.getInt("country_id"));
            return division;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * @return allDivision returns an ObservableList of all division objects
     * */
    public static ObservableList<Division> getDivisions(){
        ObservableList<Division> allDivisions = FXCollections.observableArrayList();
        Division division;
        try{
            String getAllDivisionsQuery = "SELECT * FROM FIRST_LEVEL_DIVISIONS";
            makeQuery(getAllDivisionsQuery);
            ResultSet divisionResults = getResult();
            while (divisionResults.next()){
                division = new Division(
                        divisionResults.getInt("division_Id"),
                        divisionResults.getString("division"),
                        divisionResults.getInt("country_id"));
                allDivisions.add(division);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allDivisions;
    }
}
