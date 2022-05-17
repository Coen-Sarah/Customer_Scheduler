package Model;

/**
 * Provides storage for customer information
 * */
public class Customer {
    /**
     * The customer id
     * */
    int customerId;
    /**
     * The customer name
     * */
    String name;
    /**
     * The customer address
     * */
    String address;

    /**
     * The customer postal code
     * */
    String postalCode;
    /**
     * The customer phone number
     * */
    String phone;
    /**
     * The customer division
     * */
    Division division;
    /**
     * The customer country
     * */
    Country country;

    /**
     * Constructor to instantiate a Customer object.
     * */
    public Customer(int customerId, String name, String address, String postalCode, String phone,Division division,Country country) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
        this.country = country;
    }

    public Customer(String name, String address, String postalCode, String phone,Division division,Country country) {
        this.customerId = 0;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
        this.country = country;
    }

    /**
     * @return the customer's id
     * */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId sets the customerId
     * */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the customer name
     * */
    public String getName() {
        return name;
    }

    /**
     * @param name sets the customer name
     * */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the customer address
     * */
    public String getAddress() {
        return address;
    }

    /**
     * @param address sets the customer's address
     * */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the customer postal code
     * */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode  sets the customer's postal code
     * */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return the customer phone number
     * */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone sets the customer's phone number
     * */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getFullAddress(){
        return address + "\n" + division + " " + country;
    }
}
