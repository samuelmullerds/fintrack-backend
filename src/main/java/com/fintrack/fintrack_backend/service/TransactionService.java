package com.fintrack.fintrack_backend.service;

import com.fintrack.fintrack_backend.model.Transaction;
import com.fintrack.fintrack_backend.repository.TransactionRepository;
import com.fintrack.fintrack_backend.repository.UserRepository;
import com.fintrack.fintrack_backend.model.User;
import com.fintrack.fintrack_backend.dto.CategorySummaryResponse;
import com.fintrack.fintrack_backend.dto.CreateTransactionDTO;
import com.fintrack.fintrack_backend.dto.DashboardResponse;
import com.fintrack.fintrack_backend.dto.TransactionResponseDTO;
import com.fintrack.fintrack_backend.exception.ResourceNotFoundException;
import com.fintrack.fintrack_backend.dto.TransactionType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.lang.NonNull;

import java.util.List;
import java.math.BigDecimal;
@Service

public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository){
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

        public TransactionResponseDTO createTransaction(CreateTransactionDTO dto, @NonNull Long userId){

            Transaction transaction = new Transaction();
            transaction.setDescription(dto.getDescription());
            transaction.setAmount(dto.getAmount());
            transaction.setCategory(dto.getCategory());
            transaction.setType(dto.getType());
            transaction.setDate(dto.getDate());

            User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            transaction.setUser(user);
            Transaction saved = transactionRepository.save(transaction);
            return new TransactionResponseDTO(
                saved.getId(),
                saved.getDescription(),
                saved.getAmount(),
                saved.getCategory(),
                saved.getType(),
                saved.getDate()
            );
        }
    
    public void deleteTransaction(@NonNull Long id, Long userId){
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        if (!transaction.getUser().getId().equals(userId)){
            throw new ResourceNotFoundException("Acesso negado");
        }
        transactionRepository.delete(transaction);
    }

    public TransactionResponseDTO updateTransaction(@NonNull Long id, CreateTransactionDTO dto, Long userId){
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        if (!transaction.getUser().getId().equals(userId)){
            throw new ResourceNotFoundException("Acesso negado");
        }

        transaction.setDescription(dto.getDescription());
        transaction.setAmount(dto.getAmount());
        transaction.setCategory(dto.getCategory());
        transaction.setType(dto.getType());
        transaction.setDate(dto.getDate());

        Transaction updatedTransaction = transactionRepository.save(transaction);
        return new TransactionResponseDTO(
            updatedTransaction.getId(),
            updatedTransaction.getDescription(),
            updatedTransaction.getAmount(),
            updatedTransaction.getCategory(),
            updatedTransaction.getType(),
            updatedTransaction.getDate()
        );
    }

    public DashboardResponse getDashboard(Long userId){

        BigDecimal totalIncome = transactionRepository.sumByUserAndType(userId, TransactionType.INCOME);
        BigDecimal totalExpense = transactionRepository.sumByUserAndType(userId, TransactionType.EXPENSE);

        if (totalIncome == null) totalIncome = BigDecimal.ZERO;
        if (totalExpense == null) totalExpense = BigDecimal.ZERO;

        BigDecimal balance = totalIncome.subtract(totalExpense);

        return new DashboardResponse(balance, totalIncome, totalExpense);
    }

    public List<CategorySummaryResponse> getExpenseSummary(Long userId) {

        return transactionRepository.getExpenseSummaryByUser(userId, TransactionType.EXPENSE);
    }

    public Page<TransactionResponseDTO> listTransactions(Long userId, Pageable pageable){

        return transactionRepository
                .findByUserId(userId, pageable)
                .map(t -> new TransactionResponseDTO(
                        t.getId(),
                        t.getDescription(),
                        t.getAmount(),
                        t.getCategory(),
                        t.getType(),
                        t.getDate()
                ));
}
}