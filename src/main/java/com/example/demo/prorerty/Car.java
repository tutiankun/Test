package com.example.demo.prorerty;


public abstract class Car implements carInterface {
    private String brand;
    private Integer price;
    private ConnectionFactory connectionFactory;

    @Override
    public void setBrand(String brand) {
        this.brand=brand;
    }

    @Override
    public String getBrand() {
        return this.brand;
    }

    @Override
    public void setPrice(Integer price) {
        this.price=price;
    }

    @Override
    public Integer getPrice() {
        return this.price;
    }

    @Override
    public ConnectionFactory getConnectionFactory() {
        return this.connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

}
