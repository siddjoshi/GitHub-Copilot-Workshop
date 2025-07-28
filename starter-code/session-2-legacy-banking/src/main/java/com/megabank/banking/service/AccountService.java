package com.megabank.banking.service;

import com.megabank.banking.model.Account;
import com.megabank.banking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Legacy Account Service
 * 
 * This service demonstrates legacy Spring patterns that need modernization:
 * - Field injection instead of constructor injection
 * - No transaction management
 * - Synchronous blocking operations
 * - Mixed business logic and data access
 * - Poor error handling
 * - No validation
 * 
 * TODO: Use GitHub Copilot to modernize this service:
 * - "@workspace modernize this Spring service to use modern patterns"
 * - "/fix replace field injection with constructor injection"
 * - "@workspace add proper transaction management and validation"
 * - "@workspace implement reactive patterns for non-blocking operations"
 */
@Service
public class AccountService {
    
    // Legacy: Field injection (should use constructor injection)
    @Autowired
    private AccountRepository accountRepository;
    
    // Legacy: No transaction management
    public Account createAccount(String accountNumber, String accountType, Long customerId) {
        // Legacy: No validation
        Account account = new Account(accountNumber, accountType, BigDecimal.ZERO, customerId);
        
        // Legacy: Direct repository call without error handling
        return accountRepository.save(account);
    }
    
    // Legacy: No error handling for null results
    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }
    
    // Legacy: Inefficient operations
    public List<Account> getCustomerAccounts(Long customerId) {
        List<Account> accounts = accountRepository.findByCustomerId(customerId);
        
        // Legacy: Business logic mixed with data access
        for (Account account : accounts) {
            if (account.getLastModified() == null) {
                account.setLastModified(new Date());
                accountRepository.save(account); // Inefficient N+1 problem
            }
        }
        
        return accounts;
    }
    
    // Legacy: No transaction boundaries
    public void transferFunds(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber);
        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber);
        
        // Legacy: No validation or error handling
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        
        // Legacy: Separate saves (not atomic)
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }
    
    // Legacy: Synchronous blocking operation
    public void processInterest() {
        List<Account> allAccounts = accountRepository.findAll();
        
        // Legacy: Inefficient processing
        for (Account account : allAccounts) {
            if ("SAVINGS".equals(account.getAccountType()) && account.getInterestRate() != null) {
                BigDecimal interest = account.getBalance().multiply(BigDecimal.valueOf(account.getInterestRate()));
                account.setBalance(account.getBalance().add(interest));
                accountRepository.save(account); // N+1 problem
                
                // Legacy: Thread.sleep for simulation (blocking)
                try {
                    Thread.sleep(100); // Simulates slow processing
                } catch (InterruptedException e) {
                    // Legacy: Poor error handling
                    e.printStackTrace();
                }
            }
        }
    }
    
    // Legacy: No caching
    public BigDecimal getTotalBalance(Long customerId) {
        List<Account> accounts = accountRepository.findByCustomerId(customerId);
        BigDecimal total = BigDecimal.ZERO;
        
        // Legacy: Inefficient calculation
        for (Account account : accounts) {
            if (account.isActive()) {
                total = total.add(account.getBalance());
            }
        }
        
        return total;
    }
    
    // Legacy: No pagination support
    public List<Account> getHighBalanceAccounts(BigDecimal minBalance) {
        return accountRepository.findHighBalanceAccounts(minBalance);
    }
    
    // Legacy: Boolean return instead of proper status
    public boolean deactivateAccount(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        
        if (account != null) {
            account.deactivate();
            accountRepository.save(account);
            return true;
        }
        return false;
    }
}

/*
 * MODERNIZATION GUIDE FOR COPILOT:
 * 
 * 1. Dependency Injection:
 *    □ Replace @Autowired fields with constructor injection
 *    □ Make fields final
 *    □ Add @RequiredArgsConstructor if using Lombok
 * 
 * 2. Transaction Management:
 *    □ Add @Transactional annotations
 *    □ Use proper transaction boundaries
 *    □ Handle rollback scenarios
 * 
 * 3. Validation and Error Handling:
 *    □ Add @Valid annotations for method parameters
 *    □ Use Optional for nullable returns
 *    □ Implement custom exceptions
 *    □ Add proper logging
 * 
 * 4. Performance Optimization:
 *    □ Use batch operations for bulk updates
 *    □ Add caching with @Cacheable
 *    □ Implement pagination
 *    □ Use projections for read-only operations
 * 
 * 5. Reactive Patterns:
 *    □ Replace List returns with Flux
 *    □ Replace blocking operations with reactive streams
 *    □ Use WebClient for external calls
 *    □ Implement backpressure handling
 * 
 * 6. Modern Java Features:
 *    □ Use streams for collection processing
 *    □ Replace loops with functional operations
 *    □ Use Optional for null safety
 *    □ Implement records for DTOs
 */