package com.example.demo.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "admins")
public class AdminContact {

	 @Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
	 @Column(name = "id")
	 private Long id;
	 
	 @Column(name = "last_name", nullable = false)
	 private String lastName;

	 @Column(name = "first_name", nullable = false)
	 private String firstName;

	 @Column(name = "email", nullable = false)
	 private String email;

	 @Column(name = "password", nullable = false)
	 private String password;
}
