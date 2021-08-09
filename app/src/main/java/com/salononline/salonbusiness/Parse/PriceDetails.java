package com.salononline.salonbusiness.Parse;

public class PriceDetails
{ private Double totalCost,subTotal,totalTax,hygieneCharges;

    public PriceDetails() {
    }

    public PriceDetails(Double totalCost, Double subTotal, Double totalTax, Double hygieneCharges) {
        this.totalCost = totalCost;
        this.subTotal = subTotal;
        this.totalTax = totalTax;
        this.hygieneCharges = hygieneCharges;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(Double totalTax) {
        this.totalTax = totalTax;
    }

    public Double getHygieneCharges() {
        return hygieneCharges;
    }

    public void setHygieneCharges(Double hygieneCharges) {
        this.hygieneCharges = hygieneCharges;
    }
}
