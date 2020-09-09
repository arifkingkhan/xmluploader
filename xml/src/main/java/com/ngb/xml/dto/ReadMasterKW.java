//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ngb.xml.dto;

import com.google.gson.annotations.Expose;
import java.math.BigDecimal;

public class ReadMasterKW {
    @Expose
    private BigDecimal meterMd;
    @Expose
    private BigDecimal multipliedMd;
    @Expose
    private BigDecimal billingDemand;

    public ReadMasterKW(BigDecimal meterMd, BigDecimal multipliedMd, BigDecimal billingDemand) {
        this.meterMd = meterMd;
        this.multipliedMd = multipliedMd;
        this.billingDemand = billingDemand;
    }

    public BigDecimal getMeterMd() {
        return this.meterMd;
    }

    public BigDecimal getMultipliedMd() {
        return this.multipliedMd;
    }

    public BigDecimal getBillingDemand() {
        return this.billingDemand;
    }
}
