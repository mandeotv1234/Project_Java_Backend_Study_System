package com.DuanJava.ProjectJavaFirst.dto.response;

public class AuthenticationResponse {
   private boolean authenticated;
  private    String token;
    public boolean isAuthenticated() {
        return authenticated;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    // ✅ Constructor mới phù hợp với lỗi
    public AuthenticationResponse(String token) {
        this.token = token;
        this.authenticated = true; // ✅ Mặc định là true
    }
    public AuthenticationResponse(String token, boolean authenticated){
        this.authenticated=authenticated;
        this.token=token;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
