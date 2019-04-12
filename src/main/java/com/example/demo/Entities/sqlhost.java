package com.example.demo.Entities;

import lombok.Data;

@Data
public class sqlhost {

    String port;
    String instance;
    String ip;


    public sqlhost() {
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}

