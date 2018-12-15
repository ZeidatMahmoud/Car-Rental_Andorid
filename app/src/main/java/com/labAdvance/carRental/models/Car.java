package com.labAdvance.carRental.models;

public class Car {
    /**
     * {"year":2004,
     * "make":"CHEVROLET",
     * "model":"TORNADO",
     * "distance":"203232 km"
     * ,"price":"20000",
     * "accidents":"true",
     * "offers":"true"},
     **/

    private int year;
    private String model;
    private String make;
    private String distance  ;
    private String price ;
    private boolean accidents ;
    private  boolean offers ;

    public Car(int year, String model, String make, String distance, String price, boolean accidents, boolean offers) {
        this.year = year;
        this.model = model;
        this.make = make;
        this.distance = distance;
        this.price = price;
        this.accidents = accidents;
        this.offers = offers;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isAccidents() {
        return accidents;
    }

    public void setAccidents(boolean accidents) {
        this.accidents = accidents;
    }

    public boolean isOffers() {
        return offers;
    }

    public void setOffers(boolean offers) {
        this.offers = offers;
    }
}
