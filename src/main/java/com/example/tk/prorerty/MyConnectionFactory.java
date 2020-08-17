package com.example.tk.prorerty;

import org.springframework.stereotype.Component;

@Component
public class MyConnectionFactory implements ConnectionFactory {
    private String host;

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public void setHost(String host) {
        this.host=host;
    }

}
