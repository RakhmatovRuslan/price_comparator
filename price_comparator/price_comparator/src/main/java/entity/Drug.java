package entity;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Date;

/**
 * Created by RUSLAN on 04.05.2016.
 */
public class Drug {
    private String suppliyerName;

    private String name;
    private double price;
    private String producer;
    private Date expireDate;

    private boolean isBestPrice;
    private boolean isWorthPrice;

    private SimpleBooleanProperty checked = new SimpleBooleanProperty(false);
    // other columns here


    public Drug() {
        this.name = null;
        this.price = 0.0;
        this.producer = null;
        expireDate = null;
    }

    public Drug(String name) {
        this.name = name;
    }

    public Drug(String name, double price, String producer, Date expireDate) {
        this.name = name;
        this.price = price;
        this.producer = producer;
        this.expireDate = expireDate;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getSuppliyerName() {
        return suppliyerName;
    }

    public void setSuppliyerName(String suppliyerName) {
        this.suppliyerName = suppliyerName;
    }

    public boolean isBestPrice() {
        return isBestPrice;
    }

    public void setBestPrice(boolean bestPrice) {
        isBestPrice = bestPrice;
    }

    public boolean isWorthPrice() {
        return isWorthPrice;
    }

    public void setWorthPrice(boolean worthPrice) {
        isWorthPrice = worthPrice;
    }

    public SimpleBooleanProperty checkedProperty() {
        return this.checked;
    }

    public java.lang.Boolean getChecked() {
        return this.checkedProperty().get();
    }

    public void setChecked(final java.lang.Boolean checked) {
        this.checkedProperty().set(checked);
    }

    @Override
    public String toString() {
        return "Drug{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", producer='" + producer + '\'' +
                ", expireDate='" + expireDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Drug)) return false;

        Drug drug = (Drug) o;

        if (Double.compare(drug.price, price) != 0) return false;
        if (expireDate != null ? !expireDate.equals(drug.expireDate) : drug.expireDate != null) return false;
        if (name != null ? !name.equals(drug.name) : drug.name != null) return false;
        if (producer != null ? !producer.equals(drug.producer) : drug.producer != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (producer != null ? producer.hashCode() : 0);
        result = 31 * result + (expireDate != null ? expireDate.hashCode() : 0);
        return result;
    }
}
