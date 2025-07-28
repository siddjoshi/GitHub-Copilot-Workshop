package com.megabank.banking.repository;

import com.megabank.banking.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Legacy Account Repository
 * 
 * This repository demonstrates legacy Spring Data patterns that need modernization:
 * - Uses deprecated query patterns
 * - Missing pagination and sorting
 * - No reactive support
 * - Uses raw JPQL instead of modern query methods
 * 
 * TODO: Use GitHub Copilot to modernize this repository:
 * - "@workspace modernize this Spring Data repository to use modern patterns"
 * - "/fix replace custom JPQL queries with method query derivation"
 * - "@workspace add pagination, sorting, and reactive support"
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    // Legacy: Raw JPQL queries instead of method derivation
    @Query("SELECT a FROM Account a WHERE a.accountNumber = :accountNumber")
    Account findByAccountNumber(@Param("accountNumber") String accountNumber);
    
    @Query("SELECT a FROM Account a WHERE a.customerId = :customerId")
    List<Account> findByCustomerId(@Param("customerId") Long customerId);
    
    @Query("SELECT a FROM Account a WHERE a.accountType = :accountType")
    List<Account> findByAccountType(@Param("accountType") String accountType);
    
    @Query("SELECT a FROM Account a WHERE a.status = :status")
    List<Account> findByStatus(@Param("status") String status);
    
    // Legacy: Complex JPQL that could be simplified
    @Query("SELECT a FROM Account a WHERE a.balance >= :minBalance AND a.balance <= :maxBalance")
    List<Account> findAccountsByBalanceRange(@Param("minBalance") BigDecimal minBalance, 
                                           @Param("maxBalance") BigDecimal maxBalance);
    
    // Legacy: Missing pagination
    @Query("SELECT a FROM Account a WHERE a.customerId = :customerId AND a.status = 'ACTIVE'")
    List<Account> findActiveAccountsByCustomer(@Param("customerId") Long customerId);
    
    // Legacy: Native SQL instead of JPQL
    @Query(value = "SELECT * FROM accounts WHERE balance > ?1 ORDER BY balance DESC", nativeQuery = true)
    List<Account> findHighBalanceAccounts(BigDecimal minBalance);
    
    // Legacy: Should use method naming conventions
    @Query("SELECT COUNT(a) FROM Account a WHERE a.accountType = :accountType")
    Long countAccountsByType(@Param("accountType") String accountType);
    
    // Legacy: No support for Optional return types
    @Query("SELECT a FROM Account a WHERE a.id = :id AND a.status = 'ACTIVE'")
    Account findActiveAccountById(@Param("id") Long id);
}

/*
 * MODERNIZATION GUIDE FOR COPILOT:
 * 
 * 1. Method Query Derivation:
 *    - Replace @Query annotations with method naming conventions
 *    - Use findBy, countBy, existsBy patterns
 *    - Add Optional return types for single results
 * 
 * 2. Pagination and Sorting:
 *    - Add Pageable parameters to list methods
 *    - Use Page<Account> return types
 *    - Add Sort parameters where needed
 * 
 * 3. Modern Query Features:
 *    - Use @Query with JPQL instead of native SQL
 *    - Add custom repository implementations
 *    - Use Specifications for complex queries
 * 
 * 4. Reactive Support (if migrating to WebFlux):
 *    - Extend ReactiveJpaRepository
 *    - Use Flux<Account> and Mono<Account> return types
 *    - Add reactive query methods
 * 
 * 5. Validation and Security:
 *    - Add @Valid annotations
 *    - Use @PreAuthorize for method security
 *    - Add audit logging
 */