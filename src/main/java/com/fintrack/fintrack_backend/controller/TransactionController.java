package com.fintrack.fintrack_backend.controller;

import com.fintrack.fintrack_backend.model.Transaction;
import com.fintrack.fintrack_backend.service.TransactionService;
import com.fintrack.fintrack_backend.dto.CategorySummaryResponse;
import com.fintrack.fintrack_backend.dto.DashboardResponse;

import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/transactions")

public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction, @PathVariable Long userId) {
        return transactionService.createTransaction(transaction, userId);
    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }

    @PutMapping("/{id}")
    public Transaction updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        transaction.setId(id);
        return transactionService.createTransaction(transaction, transaction.getUser().getId());
    }

    @GetMapping("/user/{userId}")
    public List<Transaction> getTransactionsByUser(@PathVariable Long userId) {
        return transactionService.getTransactionsByUser(userId);
    }

    @GetMapping("/transactions/dashboard/{userId}")
    public DashboardResponse getDashboard(@PathVariable Long userId){
        return transactionService.getDashboard(userId);
    }

    @GetMapping("/transactions/category-summary/{userId}")
    public List<CategorySummaryResponse> getExpenseSummary(@PathVariable Long userId) {
        return transactionService.getExpenseSummary(userId);
    }
    }

