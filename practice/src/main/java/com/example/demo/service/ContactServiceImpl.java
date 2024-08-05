package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactForm;
import com.example.demo.repository.ContactRepository;

import jakarta.transaction.Transactional;


@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
      private ContactRepository contactRepository;

    public void saveContact(ContactForm contactForm){
        Contact contact = new Contact();

        contact.setId(contactForm.getId());
        contact.setLastName(contactForm.getLastName());
        contact.setFirstName(contactForm.getFirstName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhone(contactForm.getPhone());
        contact.setZipCode(contactForm.getZipCode());
        contact.setAddress(contactForm.getAddress());
        contact.setBuildingName(contactForm.getBuildingName());
        contact.setContactType(contactForm.getContactType());
        contact.setBody(contactForm.getBody());

        contactRepository.save(contact);
    }
    	
	   public void ContactServicempl(ContactRepository contactRepositry) {
		   this.contactRepository = contactRepositry;
	   }
	   
	   public List<Contact> getAllContacts() {
			return contactRepository.findAll();
		}

	   public Optional<Contact> getContactById(Long id) {
	
		   return contactRepository.findById(id);
	   }  	
	   
	   public void updateContact(ContactForm contactForm) {
		   Contact contact = new Contact();
		   contact.setId(contactForm.getId());
		   contact.setLastName(contactForm.getLastName());
	        contact.setFirstName(contactForm.getFirstName());
	        contact.setEmail(contactForm.getEmail());
	        contact.setPhone(contactForm.getPhone());
	        contact.setZipCode(contactForm.getZipCode());
	        contact.setAddress(contactForm.getAddress());
	        contact.setBuildingName(contactForm.getBuildingName());
	        contact.setContactType(contactForm.getContactType());
	        contact.setBody(contactForm.getBody());
		   
	        contactRepository.save(contact);
	  }
	   
	   @Transactional
	    public void deleteContact(Long id) {
	        contactRepository.deleteById(id);
	    }
}
