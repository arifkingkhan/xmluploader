package com.ngb.xml.dto;

public class PostMeterReplacement {
    private ConsumerMeterMapping consumerMeterMapping;
    private Read finalRead;
    private Read startRead;
    private Read currentRead;
    private String meterCtrMapping;

    public PostMeterReplacement(ConsumerMeterMapping consumerMeterMapping, Read finalRead, Read startRead, Read currentRead, String meterCtrMapping) {
        this.consumerMeterMapping = consumerMeterMapping;
        this.finalRead = finalRead;
        this.startRead = startRead;
        this.currentRead = currentRead;
        this.meterCtrMapping = meterCtrMapping;
    }

    public ConsumerMeterMapping getConsumerMeterMapping() {
        return consumerMeterMapping;
    }

    public void setConsumerMeterMapping(ConsumerMeterMapping consumerMeterMapping) {
        this.consumerMeterMapping = consumerMeterMapping;
    }

    public Read getFinalRead() {
        return finalRead;
    }

    public void setFinalRead(Read finalRead) {
        this.finalRead = finalRead;
    }

    public Read getStartRead() {
        return startRead;
    }

    public void setStartRead(Read startRead) {
        this.startRead = startRead;
    }

    public Read getCurrentRead() {
        return currentRead;
    }

    public void setCurrentRead(Read currentRead) {
        this.currentRead = currentRead;
    }
}
