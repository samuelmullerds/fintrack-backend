package com.fintrack.fintrack_backend.dto;
import java.math.BigDecimal;

public class DashboardResponse {
    private BigDecimal Income;
    private BigDecimal Expense;
    private BigDecimal Balance;

    public DashboardResponse(BigDecimal balance, BigDecimal income, BigDecimal expense) {
        this.Balance = balance;
        this.Income = income;
        this.Expense = expense;
    }

    public BigDecimal getIncome() {
        return Income;
    }

    public void setIncome(BigDecimal income) {
        Income = income;
    }

    public BigDecimal getExpense(){
        return Expense;
    }

    public void setExpense(BigDecimal expense){
        Expense = expense;
    }

    public BigDecimal getBalance() {
        return Balance;
    }

    public void setBalance(BigDecimal balance) {
        Balance = balance;
    }
    
}
