package programer.sekop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import programer.sekop.model.*;
import programer.sekop.repository.*;
import programer.sekop.utill.dataTransfer.WebResponse;
import programer.sekop.utill.dataTransfer.contact.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ContactControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private UserRepository userRepository;
	
	@BeforeEach
	void setUp () {
		contactRepository.deleteAll ();
		userRepository.deleteAll ();
		
		User user = new User ();
		user.setUsername ("test");
		user.setPassword ("test");
		user.setToken ("test");
		user.setTokenExpiredAt (System.currentTimeMillis () + 1000000000L);
		user.setName ("test");
		userRepository.save (user);
	}
	
//######################################################################################################################
	//create methode
//######################################################################################################################
	@Test
	void createContactBadrRequest() throws Exception {
		CreateContactRequest contactRequest = new CreateContactRequest ();
		contactRequest.setFirstname ("");
		contactRequest.setEmail ("salah");
		
		mockMvc.perform (
				post("/contacts")
						.accept (MediaType.APPLICATION_JSON)
						.contentType (MediaType.APPLICATION_JSON)
						.content (objectMapper.writeValueAsString (contactRequest))
						.header ("X-API-TOKEN","test")
		).andExpectAll (
				status().isBadRequest ()
		).andDo (result -> {
			WebResponse<String> response = objectMapper.readValue (result.getResponse ().getContentAsString (), new TypeReference<> () {
			});
			
			assertNotNull (response.getErrors ());
		});
	
	}
	
	@Test
	void createContactSuccess() throws Exception {
		CreateContactRequest contactRequest = new CreateContactRequest ();
		contactRequest.setFirstname ("test");
		contactRequest.setEmail ("test@exemple.com");
		contactRequest.setLastname ("test");
		contactRequest.setPhone ("thesh");
		
		mockMvc.perform (
				post("/contacts")
						.accept (MediaType.APPLICATION_JSON)
						.contentType (MediaType.APPLICATION_JSON)
						.content (objectMapper.writeValueAsString (contactRequest))
						.header ("X-API-TOKEN","test")
		).andExpectAll (
				status().isOk ()
		).andDo (result -> {
			WebResponse<ContactResponse> response = objectMapper.readValue (result.getResponse ().getContentAsString (), new TypeReference<> () {
			});
			
			assertNull (response.getErrors ());
		});
	
	}

//######################################################################################################################
	// get methode
//######################################################################################################################

	@Test
	void getContactNotFound() throws Exception {
		mockMvc.perform (
				get ("/contacts/1212121212121")
						.accept (MediaType.APPLICATION_JSON)
						.contentType (MediaType.APPLICATION_JSON)
						.header ("X-API-TOKEN","test")
		).andExpectAll (
				status().isNotFound ()
		).andDo (result -> {
			WebResponse<String> response = objectMapper.readValue (result.getResponse ().getContentAsString (), new TypeReference<> () {
			});
			
			assertNotNull (response.getErrors ());
		});
		
	}
	
	@Test
	void getContactSuccess() throws Exception {
		User user= userRepository.findById ("test").orElseThrow ();
		
		Contact contact = new Contact ();
		contact.setId (UUID.randomUUID ().toString ());
		contact.setFirstname ("test");
		contact.setLastname ("test");
		contact.setEmail ("test@exemple.com");
		contact.setPhone ("0o00");
		contact.setUser (user);
		contactRepository.save (contact);
		
		mockMvc.perform (
				get ("/contacts/" +contact.getId ())
						.accept (MediaType.APPLICATION_JSON)
						.contentType (MediaType.APPLICATION_JSON)
						.header ("X-API-TOKEN","test")
		).andExpectAll (
				status().isOk ()
		).andDo (result -> {
			WebResponse<ContactResponse> response = objectMapper.readValue (result.getResponse ().getContentAsString (), new TypeReference<> () {
			});
			assertNull (response.getErrors ());
			assertEquals (contact.getId (),response.getData ().getId ());
			assertEquals (contact.getFirstname (),response.getData ().getFirstname ());
			assertEquals (contact.getLastname (),response.getData ().getLastname ());
			assertEquals (contact.getEmail (),response.getData ().getEmail ());
			assertEquals (contact.getPhone (),response.getData ().getPhone ());
		});
		
	}

//######################################################################################################################

//######################################################################################################################
	

	@Test
	void updateContactSuccess() throws Exception {
		User user= userRepository.findById ("test").orElseThrow ();
		
		Contact contact = new Contact ();
		contact.setId (UUID.randomUUID ().toString ());
		contact.setFirstname ("budi");
		contact.setLastname ("test");
		contact.setEmail ("test@exemple.com");
		contact.setPhone ("0o00");
		contact.setUser (user);
		contactRepository.save (contact);
		
		UpdateContactRequest contactRequest = new UpdateContactRequest ();
		contactRequest.setFirstname ("test");
		contactRequest.setEmail ("test@exemple.com");
		contactRequest.setLastname ("test");
		contactRequest.setPhone ("thesh");
		
		mockMvc.perform (
				put ("/contacts/"+contact.getId ())
						.accept (MediaType.APPLICATION_JSON)
						.contentType (MediaType.APPLICATION_JSON)
						.content (objectMapper.writeValueAsString (contactRequest))
						.header ("X-API-TOKEN","test")
		).andExpectAll (
				status().isOk ()
		).andDo (result -> {
			WebResponse<ContactResponse> response = objectMapper.readValue (result.getResponse ().getContentAsString (), new TypeReference<> () {
			});
			
			assertNull (response.getErrors ());
		});
		
	}

//######################################################################################################################

//######################################################################################################################
	
	@Test
	void deleteContactSuccess() throws Exception {
		User user= userRepository.findById ("test").orElseThrow ();
		
		Contact contact = new Contact ();
		contact.setId (UUID.randomUUID ().toString ());
		contact.setFirstname ("test");
		contact.setLastname ("test");
		contact.setEmail ("test@exemple.com");
		contact.setPhone ("0o00");
		contact.setUser (user);
		contactRepository.save (contact);
		
		mockMvc.perform (
				delete ("/contacts/" +contact.getId ())
						.accept (MediaType.APPLICATION_JSON)
						.contentType (MediaType.APPLICATION_JSON)
						.header ("X-API-TOKEN","test")
		).andExpectAll (
				status().isOk ()
		).andDo (result -> {
			WebResponse<String> response = objectMapper.readValue (result.getResponse ().getContentAsString (), new TypeReference<> () {
			});
			assertNull (response.getErrors ());
			assertEquals ("berhasil dihapus",response.getData ());
			});
		
	}
	
	
}



