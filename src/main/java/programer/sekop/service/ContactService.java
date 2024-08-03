package programer.sekop.service;

import jakarta.transaction.Transactional;
import programer.sekop.model.User;
import programer.sekop.utill.dataTransfer.contact.*;

public interface ContactService {
	@Transactional
	ContactResponse create(User user, CreateContactRequest request);
	
	@Transactional
	ContactResponse getAll (User user, String id);
	
	ContactResponse update (User user, UpdateContactRequest request);
	
	void delete (User user, String contactId);
}
