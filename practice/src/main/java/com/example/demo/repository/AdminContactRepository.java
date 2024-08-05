package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.AdminContact;

public interface AdminContactRepository extends JpaRepository<AdminContact ,Long>{

	AdminContact findByEmail(String email);

}
