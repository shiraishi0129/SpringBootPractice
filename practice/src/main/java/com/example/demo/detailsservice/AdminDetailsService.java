
package com.example.demo.detailsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AdminContact;
import com.example.demo.repository.AdminContactRepository;

@Service
public class AdminDetailsService implements UserDetailsService {

	
	private AdminContactRepository adminContactRepositry;
	
	@Autowired
	public void AdminDataService(AdminContactRepository adminContactRepositry,PasswordEncoder passwordEncoder) {
		this.adminContactRepositry = adminContactRepositry;
		}
	
	@Override
	 public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		AdminContact admincontact = adminContactRepositry.findByEmail(email);
				if (admincontact == null) {
		            throw new UsernameNotFoundException("User not found with email: " + email);
		        }
	return User.builder()
			.username(admincontact.getEmail())
			.password(admincontact.getPassword())
			.roles("USER")
			.build();

	}
}