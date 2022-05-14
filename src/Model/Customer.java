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
     * Constructor to instantiate a Customer object.
     * */
    public Customer(int customerId, String name, String address, String postalCode, String phone) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
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
}
