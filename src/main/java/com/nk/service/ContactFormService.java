package com.nk.service;

import java.util.List;

import com.nk.model.ContactForm;

public interface ContactFormService {

	public ContactForm saveContactFormService(ContactForm contactForm);
	
	public List<ContactForm> readAllContactsService();
	
	public void deleteContactService(int id);
}
