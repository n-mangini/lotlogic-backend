package app.security.model.response;

public class LoginResponse {
    private TokenResponse tokenResponse;
    private String firstName;

    public LoginResponse(TokenResponse tokenResponse, String firstName) {
        this.tokenResponse = tokenResponse;
        this.firstName = firstName;
    }

    public TokenResponse getTokenResponse() {
        return this.tokenResponse;
    }

    public void setTokenResponse(TokenResponse tokenResponse) {
        this.tokenResponse = tokenResponse;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
