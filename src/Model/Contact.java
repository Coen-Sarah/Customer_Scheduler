package Model;

public class Contact {
    int contactId;
    String name;
    String email;

    public Contact(int contactId,String name, String email) {
        this.contactId = contactId;
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
