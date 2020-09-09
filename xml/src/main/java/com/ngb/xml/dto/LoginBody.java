//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ngb.xml.dto;

import com.google.gson.annotations.Expose;

public class LoginBody {
    @Expose
    private String username;
    @Expose
    private String password;

    public LoginBody(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
