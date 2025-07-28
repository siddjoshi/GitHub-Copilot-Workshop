package com.megabank.banking.controller;

import com.megabank.banking.model.Account;
import com.megabank.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Legacy Account REST Controller
 * 
 * This controller demonstrates legacy Spring MVC patterns that need modernization:
 * - No proper REST conventions
 * - Missing error handling
 * - No validation
 * - No security
 * - No OpenAPI documentation
 * - Blocking operations
 * 
 * TODO: Use GitHub Copilot to modernize this controller:
 * - "@workspace modernize this REST controller to use Spring Boot 3.2 patterns"
 * - "/fix add proper REST conventions and error handling"
 * - "@workspace add validation, security, and OpenAPI documentation"
 * - "@workspace implement reactive patterns with WebFlux"
 */
@RestController
@RequestMapping("/api") // Legacy: Should be /api/v1/accounts
public class AccountController {
    
    // Legacy: Field injection
    @Autowired
    private AccountService accountService;
    
    // Legacy: Non-RESTful endpoint naming
    @PostMapping("/createAccount")
    public Account createAccount(@RequestParam String accountNumber,
                               @RequestParam String accountType,
                               @RequestParam Long customerId) {
        // Legacy: No validation, no error handling
        return accountService.createAccount(accountNumber, accountType, customerId);
    }
    
    // Legacy: Missing path variables, poor naming
    @GetMapping("/getAccount")
    public Account getAccount(@RequestParam String accountNumber) {
        // Legacy: Returns null instead of 404
        return accountService.getAccountByNumber(accountNumber);
    }
    
    // Legacy: Non-standard endpoint structure
    @GetMapping("/customer/accounts")
    public List<Account> getCustomerAccounts(@RequestParam Long customerId) {
        // Legacy: No pagination, no error handling
        return accountService.getCustomerAccounts(customerId);
    }
    
    // Legacy: POST for non-creation operation
    @PostMapping("/transfer")
    public String transferFunds(@RequestParam String fromAccount,
                              @RequestParam String toAccount,
                              @RequestParam BigDecimal amount) {
        try {
            // Legacy: No validation of transfer rules
            accountService.transferFunds(fromAccount, toAccount, amount);
            return "Transfer successful"; // Legacy: String response instead of proper status
        } catch (Exception e) {
            // Legacy: Poor error handling
            return "Transfer failed: " + e.getMessage();
        }
    }
    
    // Legacy: Administrative endpoint without security
    @PostMapping("/processInterest")
    public String processInterest() {
        // Legacy: Blocking operation that could timeout
        accountService.processInterest();
        return "Interest processed"; // Legacy: No proper status response
    }
    
    // Legacy: GET with side effects
    @GetMapping("/balance/total")
    public BigDecimal getTotalBalance(@RequestParam Long customerId) {
        // Legacy: No caching, inefficient calculation
        return accountService.getTotalBalance(customerId);
    }
    
    // Legacy: No proper HTTP status codes
    @GetMapping("/highBalance")
    public List<Account> getHighBalanceAccounts(@RequestParam BigDecimal minBalance) {
        // Legacy: No pagination for potentially large results
        return accountService.getHighBalanceAccounts(minBalance);
    }
    
    // Legacy: PUT used for deletion operation
    @PutMapping("/deactivate")
    public String deactivateAccount(@RequestParam String accountNumber) {
        boolean success = accountService.deactivateAccount(accountNumber);
        // Legacy: String response instead of proper HTTP status
        return success ? "Account deactivated" : "Account not found";
    }
}

/*
 * MODERNIZATION GUIDE FOR COPILOT:
 * 
 * 1. REST Conventions:
 *    □ Use proper HTTP methods (GET, POST, PUT, DELETE)
 *    □ Use path variables instead of request parameters
 *    □ Follow RESTful resource naming (/api/v1/accounts/{id})
 *    □ Return proper HTTP status codes (200, 201, 404, 400, 500)
 * 
 * 2. Request/Response Handling:
 *    □ Use @Valid for request body validation
 *    □ Create proper DTO classes
 *    □ Use ResponseEntity for proper status codes
 *    □ Add request/response examples
 * 
 * 3. Error Handling:
 *    □ Add @ControllerAdvice for global error handling
 *    □ Create custom exception classes
 *    □ Return proper error responses with details
 *    □ Add logging for error tracking
 * 
 * 4. Security:
 *    □ Add @PreAuthorize annotations
 *    □ Implement JWT token validation
 *    □ Add rate limiting
 *    □ Validate user permissions
 * 
 * 5. Documentation:
 *    □ Add OpenAPI/Swagger annotations
 *    □ Document request/response schemas
 *    □ Add example values
 *    □ Include error response documentation
 * 
 * 6. Reactive Patterns (if using WebFlux):
 *    □ Return Mono<Account> for single results
 *    □ Return Flux<Account> for collections
 *    □ Use reactive repositories
 *    □ Handle backpressure properly
 * 
 * 7. Modern Features:
 *    □ Add pagination with Pageable
 *    □ Use constructor injection
 *    □ Add metrics and monitoring
 *    □ Implement HATEOAS for API discoverability
 */