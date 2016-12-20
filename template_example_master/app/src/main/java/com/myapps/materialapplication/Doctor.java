package com.myapps.materialapplication;

/**
 * Created by ozhar on 21-04-2016.
 */
public class Doctor {
    String docname;
    String docspeciality;
    String docemail;
    String docphone;
    boolean type;
    String key;

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public String getDocspeciality() {
        return docspeciality;
    }

    public void setDocspeciality(String docspeciality) {
        this.docspeciality = docspeciality;
    }

    public String getDocemail() {
        return docemail;
    }

    public void setDocemail(String docemail) {
        this.docemail = docemail;
    }

    public String getDocphone() {
        return docphone;
    }

    public void setDocphone(String docphone) {
        this.docphone = docphone;
    }

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

    public Doctor() {

    }

    public Doctor(String docname, String docspeciality, String docphone, String docemail, boolean type){
        this.docname = docname;
        this.docspeciality = docspeciality;
        this.docphone = docphone;
        this.docemail = docemail;
        this.type = type;

    }

}
