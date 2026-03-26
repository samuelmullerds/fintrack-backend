package com.fintrack.fintrack_backend.controller;

import com.fintrack.fintrack_backend.service.TransactionService;
import com.fintrack.fintrack_backend.dto.CategorySummaryResponse;
import com.fintrack.fintrack_backend.dto.CreateTransactionDTO;
import com.fintrack.fintrack_backend.dto.DashboardResponse;
import com.fintrack.fintrack_backend.dto.TransactionResponseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import jakarta.validation.Valid;    

import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/transactions")
@Tag(name = "Transactions", description = "Gerenciamento das transações financeiras do usuário")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

        private Long getAuthenticatedUserId() {
        Object principal = SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();

        if (principal instanceof Long) {
            return (Long) principal;
        }

        if (principal instanceof String) {
            return Long.parseLong((String) principal);
        }

        throw new RuntimeException("Usuário não autenticado");
    }

    @Operation(summary = "Criar nova transação", description = "Cria uma nova transação financeira para um usuário específico")
    @PostMapping
    public TransactionResponseDTO createTransaction(@RequestBody @Valid CreateTransactionDTO dto){
        Long userId = getAuthenticatedUserId();
        return transactionService.createTransaction(dto, userId);
    }

    @Operation(summary = "Deletar transação", description = "Deleta uma transação financeira específica de um usuário")
    @DeleteMapping("/{id}")
    public void deleteTransaction(@Parameter(description = "ID da transação") @PathVariable Long id) {
        Long userId = getAuthenticatedUserId();
        transactionService.deleteTransaction(id, userId);
    }

    @Operation(summary = "Atualizar transação", description = "Atualiza os dados de uma transação financeira específica")
    @PutMapping("/{id}")
    public TransactionResponseDTO updateTransaction(@Parameter(description = "ID da transação") @PathVariable Long id, @RequestBody @Valid CreateTransactionDTO dto) {
        Long userId = getAuthenticatedUserId();
        return transactionService.updateTransaction(id, dto, userId);
    }

    @Operation(summary = "Listar transações do usuário", description = "Retorna todas as transações financeiras de um usuário específico")
    @GetMapping
    public Page<TransactionResponseDTO> listTransactions(Pageable pageable) {
        Long userId = getAuthenticatedUserId();
        return transactionService.listTransactions(userId, pageable);
    }

    @Operation(summary = "Obter dashboard financeiro", description = "Retorna um resumo financeiro para o dashboard do usuário, incluindo saldo atual, total de receitas e despesas")
    @GetMapping("/dashboard")
    public DashboardResponse getDashboard(){
        Long userId = getAuthenticatedUserId();
        return transactionService.getDashboard(userId);
    }

    @Operation(summary = "Obter resumo por categoria", description = "Retorna um resumo das despesas agrupadas por categoria para um usuário específico")
    @GetMapping("/category-summary")
    public List<CategorySummaryResponse> getExpenseSummary(){
        Long userId = getAuthenticatedUserId();
        return transactionService.getExpenseSummary(userId);
    }
    }

