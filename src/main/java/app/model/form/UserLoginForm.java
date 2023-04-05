package app.model.form;

public class UserLoginForm {
    private String dni;
    private String password;

    public UserLoginForm() {

    }

    public UserLoginForm(String dni, String password) {
        this.dni = dni;
        this.password = password;
    }

    public String getDni() {
        return this.dni;
    }

    public String getPassword() {
        return this.password;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
