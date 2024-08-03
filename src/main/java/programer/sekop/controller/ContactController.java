package programer.sekop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import programer.sekop.model.User;
import programer.sekop.service.ContactService;
import programer.sekop.utill.dataTransfer.WebResponse;
import programer.sekop.utill.dataTransfer.contact.*;

@RestController
public class ContactController {

	@Autowired
	private ContactService contactService;
//=====================================================================================================================>
	
	@PostMapping(
			path = "/contacts",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public WebResponse<ContactResponse> create(User user, @RequestBody CreateContactRequest request){
		ContactResponse contactResponse = contactService.create (user, request);
		return WebResponse.<ContactResponse>builder().data (contactResponse).build();
	}
	
//=====================================================================================================================>
	
	@GetMapping(
			path = "/contacts/{contactId}",
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public WebResponse<ContactResponse> getAll(User user,@PathVariable("contactId") String contactId){
		ContactResponse contactResponse = contactService.getAll (user,contactId);
		return WebResponse.<ContactResponse>builder().data (contactResponse).build();
	}
	
//=====================================================================================================================>


	@PutMapping (
			path = "/contacts/{contactId}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public WebResponse<ContactResponse> update (User user,
	                                            @PathVariable("contactId") String contactId,
	                                            @RequestBody UpdateContactRequest request){
		request.setId (contactId);
		
		ContactResponse contactResponse = contactService.update (user,request);
		return WebResponse.<ContactResponse>builder().data (contactResponse).build();
		
	}

//=====================================================================================================================>
	
	@DeleteMapping(
			path = "/contacts/{contactId}",
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public WebResponse<String> delete(User user,@PathVariable("contactId") String contactId){
		 contactService.delete (user,contactId);
		return WebResponse.<String>builder().data ("berhasil dihapus").build();
	}
	
}
