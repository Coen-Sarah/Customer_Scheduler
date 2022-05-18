package C195PA.Model;

/**
 * Provides storage for contact information
 * */
public class Contact {
    /**
     * Contact Id number
     * */
    int contactId;
    /**
     * Contact name
     * */
    String name;
    /**
     * Contact email
     * */
    String email;

    /**
     * Constructor to instantiate a Contact object.
     * */
    public Contact(int contactId,String name, String email) {
        this.contactId = contactId;
        this.name = name;
        this.email = email;
    }

    /**
     * @return the contact name
     * */
    public String getName() {
        return name;
    }

    /**
     * @param name sets the contact's name
     * */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the contact email
     * */
    public String getEmail() {
        return email;
    }

    /**
     * @param email sets the contact email
     * */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the contactId
     * */
    public int getContactId() {
        return contactId;
    }

    /**
     * @param contactId sets the contactId
     * */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * @return the contact name
     * */
    public String toString(){ return name; }
}
