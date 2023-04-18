package app.model.form;

public class UserLoginForm {
    private final String dni;
    private final String password;

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
}
