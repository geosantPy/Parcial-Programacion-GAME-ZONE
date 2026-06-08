package org.example.model;

import java.time.LocalDateTime;

public class Sale {

    private String id;
    private String videoGameTitle;
    private int quantity;
    private double unitPrice;
    private double total;
    private LocalDateTime saleDate;

    public Sale(
            String id,
            String videoGameTitle,
            int quantity,
            double unitPrice,
            double total,
            LocalDateTime saleDate
    ) {
        this.id = id;
        this.videoGameTitle = videoGameTitle;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.total = total;
        this.saleDate = saleDate;
    }

    public String getId() {
        return id;
    }

    public String getVideoGameTitle() {
        return videoGameTitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getTotal() {
        return total;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id='" + id + '\'' +
                ", videoGameTitle='" + videoGameTitle + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                ", saleDate=" + saleDate +
                '}';
    }
}
