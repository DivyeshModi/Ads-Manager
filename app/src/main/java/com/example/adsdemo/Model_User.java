package com.example.adsdemo;

public class Model_User {
    String Name,Phone_No;

    public Model_User(String name, String phone_No) {
        Name = name;
        Phone_No = phone_No;
    }

    public String getName() {
        return Name;
    }

    public String getPhone_No() {
        return Phone_No;
    }
}
