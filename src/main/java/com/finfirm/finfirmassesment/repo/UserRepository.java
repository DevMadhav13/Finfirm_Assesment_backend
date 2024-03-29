package com.finfirm.finfirmassesment.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finfirm.finfirmassesment.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	 Optional<User>  findByUsername (String username);
     Optional<User> findByEmail(String email);

}
