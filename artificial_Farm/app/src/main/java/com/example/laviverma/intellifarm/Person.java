package com.example.laviverma.intellifarm;

/**
 * Created by Lavi Verma on 09-Jul-18.
 */
import com.cloudant.client.api.model.Document;
import com.cloudant.client.org.lightcouch.Attachment;

import java.util.Map;

public class Person extends Document {
private String name,email,password,phone_number,application_key,application_password,polyfarms,farmtypes,address;

    public void setPolyfarms(String polyfarms) {
        this.polyfarms = polyfarms;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFarmtypes(String farmtypes) {
        this.farmtypes = farmtypes;
    }

    public void setApplication_password(String application_password) {
        this.application_password = application_password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setApplication_key(String application_key) {
        this.application_key = application_key;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getApplication_key() {
        return application_key;
    }

    public String getFarmtypes() {
        return farmtypes;
    }

    public String getApplication_password() {
        return application_password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getPolyfarms() {
        return polyfarms;
    }
}
