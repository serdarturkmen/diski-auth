package gov.diski.diskiauth.repository;

import gov.diski.diskiauth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Moritz Schulze
 */
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

}
