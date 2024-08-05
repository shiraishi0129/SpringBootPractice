package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Contact;
import com.example.demo.form.AdminContactForm;
import com.example.demo.form.ContactForm;
import com.example.demo.service.AdminContactService;
import com.example.demo.service.ContactService;
import com.example.demo.service.ContactServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminContactController {

	@Autowired
    private AdminContactService adminContactService;
	@Autowired
	private ContactServiceImpl contactServiceImpl;
	@Autowired
	private ContactService contactService;
	
	public AdminContactController(ContactServiceImpl contactServiceImpl) {
        this.contactServiceImpl = contactServiceImpl;
    }
	
	//Getメソッドが送られたらAdminContactFormの新しいインスタンスを作成してビューに渡す
	@GetMapping("/admin/signup")
    public String signup(Model model) {
        model.addAttribute("adminContactForm", new AdminContactForm());

        return "admin/signup";
    }
    
	//Postメソッドが送られたらバリテーションのチェックを行い、エラーがあった場合はもう一度登録画面に飛ばす。
	//エラーが無ければHTTPセッションから現在のセッションを取得してセッションが存在しない場合は新しく作成される。
	//続いてセッションに属性を追加します。
	//最後にsaveのサービスメソッドを使用してデータベースに保存を行う。
	 @PostMapping("/admin/signup")
	    public String signup(@Validated @ModelAttribute("adminContactForm") AdminContactForm adminContactForm, BindingResult errorResult, HttpServletRequest request) {

	        if (errorResult.hasErrors()) {
	          return "admin/signup";
	        }

	        HttpSession session = request.getSession();
	        session.setAttribute("adminContactForm", adminContactForm);
	        adminContactService.saveAdminContact(adminContactForm);

	        return "redirect:admin/signin";
	    }
	 
	 //まず、HTTPセッションから現在のセッションを取得してなければ新しくセッションを作成する。
	 //
	 @GetMapping("/admin/signin")
	    public String signin(Model model, HttpServletRequest request) {
	        HttpSession session = request.getSession();
	        model.addAttribute("contactForm", new ContactForm());
	        AdminContactForm adminContactForm = (AdminContactForm) session.getAttribute("adminContactForm");
	        model.addAttribute("adminContactForm", adminContactForm);
	        return "admin/signin";
	    }

	 @PostMapping("/admin/signin")
	 public String signin(@ModelAttribute("contactForm") ContactForm contactForm,Model model, HttpServletRequest request){
		 model.addAttribute("contactForm", new ContactForm());
		 HttpSession session = request.getSession();
	     session.setAttribute("contactForm", contactForm);
		 return "redirect:admin/contacts";
	 }
	 
	@GetMapping("/admin/contacts")
	public String contacts(Model model) {
		List<Contact> contacts = contactServiceImpl.getAllContacts();
        model.addAttribute("contacts", contacts);
		return "admin/contacts";
    }

	

	@GetMapping("/admin/contacts/:id")
	public String id(Model model,@RequestParam("id") Long id){
		Optional<Contact> optionalContacts = contactServiceImpl.getContactById(id);
		   if (optionalContacts.isPresent()) {
	            Contact contacts = optionalContacts.get();
	            model.addAttribute("contacts", contacts); // `contact`オブジェクトを渡す
	            return "admin/id";
	        } 
		   else {
			   return null;
		   }
	}

	
	////
	
	 @GetMapping("/admin/contacts/:id/edit")
	    public String findContactById(Model model,@RequestParam("id") Long id,HttpServletRequest request){
		 Optional<Contact> optionalContact = contactServiceImpl.getContactById(id);
		    if (optionalContact.isPresent()) {
			   Contact contact = optionalContact.get();
		        ContactForm contactForm = new ContactForm();
		        
		        // contactのフィールドをcontactFormにコピー
		        contactForm.setId(contact.getId());
		        contactForm.setLastName(contact.getLastName());
		        contactForm.setFirstName(contact.getFirstName());
		        contactForm.setEmail(contact.getEmail());
		        contactForm.setPhone(contact.getPhone());
		        contactForm.setZipCode(contact.getZipCode());
		        contactForm.setAddress(contact.getAddress());
		        contactForm.setBuildingName(contact.getBuildingName());
		        contactForm.setContactType(contact.getContactType());
		        contactForm.setBody(contact.getBody());

		        model.addAttribute("contactForm", contactForm);
	            return "admin/edit";
	        } 
		   else {
			   model.addAttribute("message", "Contact not found");
			   return "/admin/contacts";
		   }

	    }
	 
/*	 @GetMapping("/admin/edit/complete")
	 public String editupdate(Model model, HttpServletRequest request){
		 HttpSession session = request.getSession();
		 ContactForm contactForm = (ContactForm) session.getAttribute("contactForm");
	        model.addAttribute("contactForm", contactForm);
		 return "/admin/edit/complete";
	 }*/
	 
	 
	 @PostMapping("/admin/edit/{id}/complete")
	 public String editContact(@PathVariable("id") Long id, @ModelAttribute ContactForm contactForm, Model model){
		 Optional<Contact> Contacts = contactServiceImpl.getContactById(id);
		   if (Contacts.isPresent()) {
				 contactServiceImpl.updateContact(contactForm);
	        } 
		   else {
			   
		   }
		   model.addAttribute("contact", contactForm);
		   return "admin/signin";
	 }
	 
	 @PostMapping("/admin/{id}/delete")
	 public String deleteContact(@PathVariable("id") Long id) {
		 contactServiceImpl.deleteContact(id);
		 return "/admin/signin";
	 }

/////////////////////////////////////////////
	//ログアウト機能
	@PostMapping("/admin/logout")
	public String logout(){
		
		
		return "admin/signin";
		
	}
}
