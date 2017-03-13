package com.rightandabove.models;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by alex on 12.03.17.
 */
public class CD {

    private String title;
    private String artist;
    private String country;
    private String company;
    private String price;
    private String year;

    public CD(String title, String artist, String country, String company, String price, String year) {
        this.title = title;
        this.artist = artist;
        this.country = country;
        this.company = company;
        this.price = price;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getCountry() {
        return country;
    }

    public String getCompany() {
        return company;
    }

    public String getPrice() {
        return price;
    }

    public String getYear() {
        return year;
    }
}
