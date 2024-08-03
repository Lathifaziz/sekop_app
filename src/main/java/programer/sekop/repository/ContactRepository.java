package programer.sekop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import programer.sekop.model.*;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact,String> {
	Optional<Contact> findFirstByUserAndId (User user, String id);
}
