package com.example.android.uiplay.expandabletoolbar;

import java.util.Date;

@SuppressWarnings("unused")
public class Detail {
    private String description;
    private int amount;
    private Date date;

    public Detail() { }

    public Detail(String description, int amount, Date date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Detail{" +
                "description='" + description + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
