package com.thebigscale.accountmanagementservice;

public enum AppConstants {
    HOST("localhost"), PORT("8080"), SEPERATOR("/"), PROTOCOL("http://");

    private String name;

    AppConstants(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
