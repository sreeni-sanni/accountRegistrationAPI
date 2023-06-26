package com.assignment.accountRegistrationAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.accountRegistrationAPI.entity.CustomerInfo;

@Repository
public interface RegisterRepository extends JpaRepository<CustomerInfo,Integer> {
	
	Boolean existsByUserName(String userName);

}
