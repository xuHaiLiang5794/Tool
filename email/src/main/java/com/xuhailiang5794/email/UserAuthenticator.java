package com.xuhailiang5794.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * <pre>
 * 用户认证
 * </pre>
 *
 * @author hailiang.xu
 * @version 1.0
 * @since 2018/2/2 18:14
 */
public class UserAuthenticator extends Authenticator {
    private String userName;
    private String password;

    public UserAuthenticator(String username, String password) {
        this.userName = username;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
    }
}
