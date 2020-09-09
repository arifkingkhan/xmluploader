package com.ngb.xml.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ConsumerMeterMapping {



   private String consumerNo;


    private String meterIdentifier;

    private String meterSerialNo;



    private String meterMake;



    private BigDecimal startRead;



    private BigDecimal finalRead;



    private String mappingStatus;



    private String installationDate;

    private String removalDate;


    public ConsumerMeterMapping(String consumerNo, String meterIdentifier, String meterSerialNo, String meterMake, BigDecimal startRead, BigDecimal finalRead, String mappingStatus, String installationDate, String removalDate) {
        this.consumerNo = consumerNo;
        this.meterIdentifier = meterIdentifier;
        this.meterSerialNo = meterSerialNo;
        this.meterMake = meterMake;
        this.startRead = startRead;
        this.finalRead = finalRead;
        this.mappingStatus = mappingStatus;
        this.installationDate = installationDate;
        this.removalDate = removalDate;
    }

    public String getConsumerNo() {
        return consumerNo;
    }

    public void setConsumerNo(String consumerNo) {
        this.consumerNo = consumerNo;
    }

    public String getMeterIdentifier() {
        return meterIdentifier;
    }

    public void setMeterIdentifier(String meterIdentifier) {
        this.meterIdentifier = meterIdentifier;
    }

    public String getMeterSerialNo() {
        return meterSerialNo;
    }

    public void setMeterSerialNo(String meterSerialNo) {
        this.meterSerialNo = meterSerialNo;
    }

    public String getMeterMake() {
        return meterMake;
    }

    public void setMeterMake(String meterMake) {
        this.meterMake = meterMake;
    }

    public BigDecimal getStartRead() {
        return startRead;
    }

    public void setStartRead(BigDecimal startRead) {
        this.startRead = startRead;
    }

    public BigDecimal getFinalRead() {
        return finalRead;
    }

    public void setFinalRead(BigDecimal finalRead) {
        this.finalRead = finalRead;
    }

    public String getMappingStatus() {
        return mappingStatus;
    }

    public void setMappingStatus(String mappingStatus) {
        this.mappingStatus = mappingStatus;
    }

    public String getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(String installationDate) {
        this.installationDate = installationDate;
    }

    public String getRemovalDate() {
        return removalDate;
    }

    public void setRemovalDate(String removalDate) {
        this.removalDate = removalDate;
    }
}
