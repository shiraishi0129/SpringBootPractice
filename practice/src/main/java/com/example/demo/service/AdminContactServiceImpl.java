package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.AdminContact;
import com.example.demo.form.AdminContactForm;
import com.example.demo.repository.AdminContactRepository;

@Service
public class AdminContactServiceImpl implements AdminContactService {

	 @Autowired
	 private AdminContactRepository adminContactRepository;
	 
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	 
	  
	@Override
	@Transactional
	public void saveAdminContact(AdminContactForm adminContactForm) {
		// TODO 自動生成されたメソッド・スタブ

        AdminContact adminContact = new AdminContact();
        
        adminContact.setLastName(adminContactForm.getLastName());
        adminContact.setFirstName(adminContactForm.getFirstName());
        adminContact.setEmail(adminContactForm.getEmail());
        adminContact.setPassword(passwordEncoder.encode(adminContactForm.getPassword()));

        adminContactRepository.save(adminContact);
	}
}
