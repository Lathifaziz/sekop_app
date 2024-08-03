package programer.sekop.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import programer.sekop.model.*;
import programer.sekop.repository.ContactRepository;
import programer.sekop.service.ContactService;
import programer.sekop.utill.dataTransfer.contact.*;

import java.util.UUID;

@Service
public class ContactServiceImpl implements ContactService {
	
	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private ValidationService validationService;
	
	private ContactResponse contactResponse(Contact contact){
		return ContactResponse.builder()
				.id (contact.getId ())
				.firstname (contact.getFirstname ())
				.lastname (contact.getLastname ())
				.email (contact.getEmail ())
				.phone (contact.getPhone ())
				.build();
	}
//=====================================================================================================================>
	
	@Transactional
	@Override
	public ContactResponse create(User user, CreateContactRequest request){
		validationService.validation (request);
		Contact contact=new Contact ();
		contact.setId (UUID.randomUUID ().toString ());
		contact.setFirstname (request.getFirstname ());
		contact.setLastname (request.getLastname ());
		contact.setEmail (request.getEmail());
		contact.setPhone (request.getPhone ());
		contactRepository.save (contact);
		
		return contactResponse (contact);
	}

//=====================================================================================================================>
	
	@Override
	public ContactResponse getAll(User user, String id){
	Contact contact = contactRepository.findFirstByUserAndId (user,id).orElseThrow (
			()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"contact not found")
	);
	return contactResponse (contact);
	}

//=====================================================================================================================>
	
	@Override
	public ContactResponse update (User user, UpdateContactRequest request){
		Contact contact = contactRepository.findFirstByUserAndId (user, request.getId ()).orElseThrow (
				() -> new ResponseStatusException (HttpStatus.NOT_FOUND, "contact not found"));
		contact.setFirstname (request.getFirstname ());
		contact.setLastname (request.getLastname ());
		contact.setEmail (request.getEmail ());
		contact.setPhone (request.getPhone ());
		contactRepository.save (contact);
		
		return contactResponse (contact);
	}
	
//=====================================================================================================================>

	@Transactional
	@Override
	public void delete(User user,String contactId){
		Contact contact = contactRepository.findFirstByUserAndId (user, contactId).orElseThrow (
				() -> new ResponseStatusException (HttpStatus.NOT_FOUND, "contact not found")
		);
		
		contactRepository.delete (contact);
	}

//=====================================================================================================================>

}
