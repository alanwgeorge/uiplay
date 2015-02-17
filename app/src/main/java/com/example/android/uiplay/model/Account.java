package com.example.android.uiplay.model;

@SuppressWarnings("UnusedDeclaration")
public class Account {
    public String name;
    public int balance;
    public String balanceDescription;
    public String maskedAccountNumber;

    public Account() { }

    public Account(String name, int balance, String balanceDescription, String maskedAccountNumber) {
        this.name = name;
        this.balance = balance;
        this.balanceDescription = balanceDescription;
        this.maskedAccountNumber = maskedAccountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getBalanceDescription() {
        return balanceDescription;
    }

    public void setBalanceDescription(String balanceDescription) {
        this.balanceDescription = balanceDescription;
    }

    public String getMaskedAccountNumber() {
        return maskedAccountNumber;
    }

    public void setMaskedAccountNumber(String maskedAccountNumber) {
        this.maskedAccountNumber = maskedAccountNumber;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                ", balanceDescription='" + balanceDescription + '\'' +
                ", maskedAccountNumber='" + maskedAccountNumber + '\'' +
                '}';
    }
}
