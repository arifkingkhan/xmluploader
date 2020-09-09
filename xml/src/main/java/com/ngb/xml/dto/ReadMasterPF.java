//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ngb.xml.dto;

import com.google.gson.annotations.Expose;
import java.math.BigDecimal;

public class ReadMasterPF {
    @Expose
    private BigDecimal meterPf;
    @Expose
    private BigDecimal billingPf;

    public ReadMasterPF(BigDecimal meterPf, BigDecimal billingPf) {
        this.meterPf = meterPf;
        this.billingPf = billingPf;
    }

    public BigDecimal getMeterPf() {
        return this.meterPf;
    }

    public BigDecimal getBillingPf() {
        return this.billingPf;
    }
}
