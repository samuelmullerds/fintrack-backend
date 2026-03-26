package com.fintrack.fintrack_backend.dto;

public class DashboardResponse {
    private double income;
    private double expense;
    private double balance;

    public DashboardResponse(double balance, double income, double expense) {
        this.balance = balance;
        this.income = income;
        this.expense = expense;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getExpense(){
        return expense;
    }

    public void setExpense(double expense){
        this.expense = expense;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    
}
