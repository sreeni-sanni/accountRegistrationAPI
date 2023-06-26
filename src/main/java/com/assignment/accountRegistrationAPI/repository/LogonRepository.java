package com.assignment.accountRegistrationAPI.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.assignment.accountRegistrationAPI.model.Customer;

@Repository
public interface LogonRepository extends JpaRepository<Customer, UUID> {
	
	@Query("SELECT c FROM Customer c WHERE c.userName = ?1 and c.password=?2")
	Optional<Customer> findByUserNameAndPassword(String userName,String password);

}
