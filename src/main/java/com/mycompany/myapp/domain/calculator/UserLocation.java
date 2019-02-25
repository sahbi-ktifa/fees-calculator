package com.mycompany.myapp.domain.calculator;

import javax.validation.constraints.NotNull;

public class UserLocation {
    @NotNull
    private String ip;

    public String getIp() {
        return ip;
    }

    public UserLocation setIp(String ip) {
        this.ip = ip;
        return this;
    }
}
