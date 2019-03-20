package twb.conwaybrian.com.twbandroid.model;


import android.util.Base64;

import lombok.Data;

@Data
public class User {

    public User(String userId,String password,String email){
        setUserId(userId);
        setPassword(password);
        setEmail(email);
    }
    public User(){}

    private String userId;
    private String password;
    private String email;

    public String authKey(){
        String original=userId+":"+password;
        return "Basic " +Base64.encodeToString(original.getBytes(), Base64.NO_WRAP);
    }
}
