package com.ngb.xml.dto;

import java.math.BigDecimal;
import java.util.Date;

public class MeterMaster {





    private String identifier;


    private String serialNo;



    private String make;


    private String meterOwner;



    private String capacity;



    private BigDecimal mf;



    private String description;



    private String phase;



    private String code;


    private boolean isPrepaid;



    private String historyNo;







    public MeterMaster( String identifier, String serialNo, String make, String meterOwner, String capacity, BigDecimal mf, String description, String phase, String code, boolean isPrepaid, String historyNo) {

        this.identifier = identifier;
        this.serialNo = serialNo;
        this.make = make;
        this.meterOwner = meterOwner;
        this.capacity = capacity;
        this.mf = mf;
        this.description = description;
        this.phase = phase;
        this.code = code;
        this.isPrepaid = isPrepaid;
        this.historyNo = historyNo;

    }



    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getMeterOwner() {
        return meterOwner;
    }

    public void setMeterOwner(String meterOwner) {
        this.meterOwner = meterOwner;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getMf() {
        return mf;
    }

    public void setMf(BigDecimal mf) {
        this.mf = mf;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isPrepaid() {
        return isPrepaid;
    }

    public void setPrepaid(boolean prepaid) {
        isPrepaid = prepaid;
    }

    public String getHistoryNo() {
        return historyNo;
    }

    public void setHistoryNo(String historyNo) {
        this.historyNo = historyNo;
    }



}
