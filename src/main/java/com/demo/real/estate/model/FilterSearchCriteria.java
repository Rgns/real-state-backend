package com.demo.real.estate.model;

public class FilterSearchCriteria {
    private FilterRange rentalPrice;
    private FilterRange salesPrice;
    private String realEstateType;

    public FilterRange getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(FilterRange rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public FilterRange getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(FilterRange salesPrice) {
        this.salesPrice = salesPrice;
    }

    public String getRealEstateType() {
        return realEstateType;
    }

    public void setRealEstateType(String realEstateType) {
        this.realEstateType = realEstateType;
    }
}
