package app.model.form;

public class UserEditForm {
    private String dni;
    private String firstName;
    private String lastName;
    private String password;

    public UserEditForm() {

    }

    public UserEditForm(String dni, String firstName, String lastName, String password) {
        this.dni = dni;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public String getDni() {
        return this.dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
