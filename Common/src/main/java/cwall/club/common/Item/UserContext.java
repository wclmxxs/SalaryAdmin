package cwall.club.common.Item;

import lombok.Data;

import javax.servlet.http.HttpSession;

@Data
public class UserContext {
    String uid;
    String cid;
    Long id;
    String phone;
    HttpSession session;
    String  pwd;
    boolean isLogin;
    String token; //用于内部调用
    public UserContext(){
        isLogin = false;
    }
    public static ThreadLocal<UserContext> userContext = new ThreadLocal<>();

    public static UserContext getUser(){
        return userContext.get();
    }

    public static void setUser(UserContext user){
        userContext.set(user);
    }
}
