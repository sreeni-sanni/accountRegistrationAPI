package com.assignment.accountRegistrationAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.accountRegistrationAPI.model.Customer;

@Repository
public interface RegisterRepository extends JpaRepository<Customer,Integer> {
	
	Boolean existsByUserName(String userName);

}
