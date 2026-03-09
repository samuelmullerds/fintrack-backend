package com.fintrack.fintrack_backend.dto;

import com.fintrack.fintrack_backend.model.Transaction;
import java.util.List;

public class UserOverviewResponse {

    private Double balance;
    private Double income;
    private Double expense;
    private List<CategorySummaryResponse> topCategories;
    private List<Transaction> recentTransactions;

    public UserOverviewResponse(
            Double balance,
            Double income,
            Double expense,
            List<CategorySummaryResponse> topCategories,
            List<Transaction> recentTransactions) {

        this.balance = balance;
        this.income = income;
        this.expense = expense;
        this.topCategories = topCategories;
        this.recentTransactions = recentTransactions;
    }

    public Double getBalance() {
        return balance;
    }

    public Double getIncome() {
        return income;
    }

    public Double getExpense() {
        return expense;
    }

    public List<CategorySummaryResponse> getTopCategories() {
        return topCategories;
    }

    public List<Transaction> getRecentTransactions() {
        return recentTransactions;
    }
}