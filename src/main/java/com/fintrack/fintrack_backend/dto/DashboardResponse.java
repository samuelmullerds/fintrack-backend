package com.fintrack.fintrack_backend.dto;
import java.math.BigDecimal;

public class DashboardResponse {
    private BigDecimal income;
    private BigDecimal expense;
    private BigDecimal balance;

    public DashboardResponse(BigDecimal balance, BigDecimal income, BigDecimal expense) {
        this.balance = balance;
        this.income = income;
        this.expense = expense;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getExpense(){
        return expense;
    }

    public void setExpense(BigDecimal expense){
        this.expense = expense;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
}
