package com.fintrack.fintrack_backend.controller;

import com.fintrack.fintrack_backend.model.Transaction;
import com.fintrack.fintrack_backend.service.TransactionService;
import com.fintrack.fintrack_backend.dto.CategorySummaryResponse;
import com.fintrack.fintrack_backend.dto.CreateTransactionDTO;
import com.fintrack.fintrack_backend.dto.DashboardResponse;
import com.fintrack.fintrack_backend.dto.TransactionResponseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import jakarta.validation.Valid;    

import org.springframework.web.bind.annotation.*;

import java.security.Security;
import java.util.List;
@RestController
@RequestMapping("/transactions")
@Tag(name = "Transactions", description = "Gerenciamento das transações financeiras do usuário")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Criar nova transação", description = "Cria uma nova transação financeira para um usuário específico")
    @PostMapping("/user/{userId}")
    public Transaction createTransaction(@RequestBody @Valid CreateTransactionDTO dto, @Parameter(description = "ID do usuário") @PathVariable Long userId) {
        return transactionService.createTransaction(dto, userId);
    }

    @Operation(summary = "Deletar transação", description = "Deleta uma transação financeira específica de um usuário")
    @DeleteMapping("/{id}")
    public void deleteTransaction(@Parameter(description = "ID da transação") @PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }

    @Operation(summary = "Atualizar transação", description = "Atualiza os dados de uma transação financeira específica")
    @PutMapping("/{id}")
    public Transaction updateTransaction(@Parameter(description = "ID da transação") @PathVariable Long id, @RequestBody Transaction transaction) {
        return transactionService.updateTransaction(id, transaction);
    }

<<<<<<< HEAD
    @GetMapping
    public List<Transaction> getTransactions(){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return transactionService.getTransactionsByEmail(email);
=======
    @Operation(summary = "Listar transações por usuário", description = "Retorna todas as transações financeiras de um usuário específico")
    @GetMapping("/user/{userId}")
    public List<Transaction> getTransactionsByUser(@Parameter(description = "ID do usuário") @PathVariable Long userId) {
        return transactionService.getTransactionsByUser(userId);
>>>>>>> f10fc2296b7c1d4d2e93add22e8869189ae37eb4
    }

    @Operation(summary = "Obter dashboard financeiro", description = "Retorna um resumo financeiro para o dashboard do usuário, incluindo saldo atual, total de receitas e despesas")
    @GetMapping("dashboard/{userId}")
    public DashboardResponse getDashboard(@Parameter(description = "ID do usuário") @PathVariable Long userId){
        return transactionService.getDashboard(userId);
    }

    @Operation(summary = "Obter resumo por categoria", description = "Retorna um resumo das despesas agrupadas por categoria para um usuário específico")
    @GetMapping("/category-summary/{userId}")
    public List<CategorySummaryResponse> getExpenseSummary(@Parameter(description = "ID do usuário") @PathVariable Long userId) {
        return transactionService.getExpenseSummary(userId);
    }

    @Operation(summary = "Listar transações", description = "Retorna uma página de transações financeiras para um usuário específico")
    @GetMapping
    public Page<TransactionResponseDTO> listTransactions(
        @Parameter(description = "ID do usuário")
        @RequestParam Long userId,
        Pageable pageable){

            return transactionService.listTransactions(userId, pageable);
        }
    }

