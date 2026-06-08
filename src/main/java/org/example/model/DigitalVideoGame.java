package org.example.model;

import org.example.interfaces.Displayable;
import org.example.interfaces.Sellable;

public class DigitalVideoGame extends VideoGame implements Sellable, Displayable {

    private double sizeGB;
    private String downloadPlatform;

    public DigitalVideoGame(
            String title,
            double price,
            String platform,
            int stock,
            String genre,
            double sizeGB,
            String downloadPlatform
    )
    {

        super(title, price, platform, stock, genre);

        this.type = "digital";

        this.sizeGB = sizeGB;
        this.downloadPlatform = downloadPlatform;

    }

    public double getSizeGB() {
        return sizeGB;
    }

    public void setSizeGB(double sizeGB) {
        this.sizeGB = sizeGB;
    }

    public String getDownloadPlatform() {
        return downloadPlatform;
    }

    public void setDownloadPlatform(String downloadPlatform) {
        this.downloadPlatform = downloadPlatform;
    }

    @Override
    public double calculateFinalPrice() {
        if (sizeGB > 50) {
            return price + 5000;
        }
        return price;
    }

    @Override
    public double sell(int qty) {

        if (qty > stock) {
            return 0;
        }

        stock -= qty;

        return calculateFinalPrice() * qty;
    }

    @Override
    public String getDisplayInfo() {
        return title + " | " +
                platform + " | " +
                genre + " | $" +
                calculateFinalPrice();
    }

    @Override
    public Object[] toTableRow() {
        return new Object[]{
                title,
                platform,
                genre,
                calculateFinalPrice(),
                stock,
                sizeGB,
                downloadPlatform
        };
    }

    @Override
    public String toString() {
        return "DigitalVideoGame{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", platform='" + platform + '\'' +
                ", stock=" + stock +
                ", genre='" + genre + '\'' +
                ", sizeGB=" + sizeGB +
                ", downloadPlatform='" + downloadPlatform + '\'' +
                '}';
    }
}