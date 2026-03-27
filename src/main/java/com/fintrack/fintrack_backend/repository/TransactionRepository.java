package com.fintrack.fintrack_backend.repository;

import com.fintrack.fintrack_backend.dto.CategorySummaryResponse;
import com.fintrack.fintrack_backend.dto.TransactionType;
import com.fintrack.fintrack_backend.model.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.math.BigDecimal;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserId(Long userId);

    Page<Transaction> findByUserId(Long userId, Pageable pageable);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user.id = :userId AND t.type = :type")
    BigDecimal sumByUserAndType(@Param("userId") Long userId, @Param("type") TransactionType type);

    @Query("SELECT new com.fintrack.fintrack_backend.dto.CategorySummaryResponse(t.category, SUM(t.amount)) " +
           "FROM Transaction t WHERE t.user.id = :userId AND t.type = :type GROUP BY t.category")
    List<CategorySummaryResponse> getExpenseSummaryByUser(@Param("userId") Long userId, @Param("type") TransactionType type);
}

