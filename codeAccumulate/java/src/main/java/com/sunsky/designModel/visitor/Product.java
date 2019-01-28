package com.sunsky.designModel.visitor;

import java.time.LocalDate;

public class Product {

    protected String name;
    protected LocalDate produceDate;
    protected float price;

    public Product(String name, LocalDate produceDate, float price) {
        this.name = name;
        this.produceDate = produceDate;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(LocalDate produceDate) {
        this.produceDate = produceDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
