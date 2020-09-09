package com.ngb.xml.bean;

public class ExcelConsumer {

    String consumerNo;
    String serialNo;
    String meterMakeCode;

    public ExcelConsumer(String consumerNo, String serialNo, String meteMakeCode) {
        this.consumerNo = consumerNo;
        this.serialNo = serialNo;
        this.meterMakeCode = meteMakeCode;
    }

    public ExcelConsumer()
    {

    }

    public String getConsumerNo() {
        return consumerNo;
    }

    public void setConsumerNo(String consumerNo) {
        this.consumerNo = consumerNo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getMeterMakeCode() {
        return meterMakeCode;
    }

    public void setMeterMakeCode(String meteMakeCode) {
        this.meterMakeCode = meteMakeCode;
    }

    @Override
    public String toString() {
        return "ExcelConsumer{" +
                "consumerNo='" + consumerNo + '\'' +
                ", serialNo='" + serialNo + '\'' +
                ", meteMakeCode='" + meterMakeCode + '\'' +
                '}';
    }
}
