package com.assignment.accountRegistrationAPI.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.assignment.accountRegistrationAPI.model.AccountDetails;

@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetails, UUID>{
	
	@Query("SELECT new AccountDetails(a.id,a.accountNumber,a.accountType,a.balance,a.currency,a.accountCreatedTmstp) FROM AccountDetails a WHERE a.customer.customerId = ?1")
	Optional<AccountDetails> findByCustomerId(UUID customerId);

}
