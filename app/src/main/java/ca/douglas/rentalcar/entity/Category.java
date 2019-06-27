package ca.douglas.rentalcar.entity;

import java.io.Serializable;

public class Category implements Serializable {

    private String id;
    private String name;
    private String priceHr;
    private String priceDay;
    private String feature;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriceHr() {
        return priceHr;
    }

    public void setPriceHr(String priceHr) {
        this.priceHr = priceHr;
    }

    public String getPriceDay() {
        return priceDay;
    }

    public void setPriceDay(String priceDay) {
        this.priceDay = priceDay;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }
}
