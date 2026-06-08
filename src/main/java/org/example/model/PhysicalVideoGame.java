package org.example.model;

import org.example.interfaces.Displayable;
import org.example.interfaces.Sellable;

public class PhysicalVideoGame extends VideoGame
        implements Sellable, Displayable {

    private String condition;
    private String distributor;

    public PhysicalVideoGame(
            String title,
            double price,
            String platform,
            int stock,
            String genre,
            String condition,
            String distributor
    ) {

        super(title, price, platform, stock, genre);

        this.type = "physical";

        this.condition = condition;
        this.distributor = distributor;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    @Override
    public double calculateFinalPrice() {

        if (condition.equalsIgnoreCase("used")) {
            return price * 0.75;
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
                condition,
                distributor
        };
    }

    @Override
    public String toString() {
        return "PhysicalVideoGame{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", platform='" + platform + '\'' +
                ", stock=" + stock +
                ", genre='" + genre + '\'' +
                ", condition='" + condition + '\'' +
                ", distributor='" + distributor + '\'' +
                '}';
    }
}