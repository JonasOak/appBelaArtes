package com.example.belaartes.data.model.dto;

public class AdminDto {
    private long countClient;
    private long countProduct;
    private long countRequestOrders;

    public AdminDto(long countClient, long countProduct, long countRequestOrders) {
        this.countClient = countClient;
        this.countProduct = countProduct;
        this.countRequestOrders = countRequestOrders;
    }

    @Override
    public String toString() {
        return "AdminDto{" +
                "countClient=" + countClient +
                ", countProduct=" + countProduct +
                ", countRequestOrders=" + countRequestOrders +
                '}';
    }

    public long getCountClient() {
        return countClient;
    }

    public void setCountClient(long countClient) {
        this.countClient = countClient;
    }

    public long getCountProduct() {
        return countProduct;
    }

    public void setCountProduct(long countProduct) {
        this.countProduct = countProduct;
    }

    public long getCountRequestOrders() {
        return countRequestOrders;
    }

    public void setCountRequestOrders(long countRequestOrders) {
        this.countRequestOrders = countRequestOrders;
    }
}
