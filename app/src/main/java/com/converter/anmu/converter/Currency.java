package com.converter.anmu.converter;

public class Currency {

    String code;
    String description;
    int flagId;

    public Currency(String code, String description, int flagId){
    this.code = code;
    this.description = description;
    this.flagId = flagId;
    }

    public Currency(String code, String description){
        this.code = code;
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public int getFlagId() {return flagId;}
    public void setFlagId(int flagId) {this.flagId = flagId;}

}
