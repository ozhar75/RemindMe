package com.myapps.materialapplication;

/**
 * Created by ozhar on 21-04-2016.
 */
public class Medicine {
    String medname;
    String medtime;
    String medintake;
    boolean type;
    String key;

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Medicine() {

    }

    public Medicine(String medname, String medtime, String medintake, boolean type){
        this.medname = medname;
        this.medtime = medtime;
        this.medintake = medintake;
        this.type = type;

    }

    public String getMedname() {
        return medname;
    }

    public void setMedname(String medname) {
        this.medname = medname;
    }

    public String getMedtime() {
        return medtime;
    }

    public void setMedtime(String medtime) {
        this.medtime = medtime;
    }

    public String getMedintake() {
        return medintake;
    }

    public void setMedintake(String medintake) {
        this.medintake = medintake;
    }
}
