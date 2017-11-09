package gov.diski.diskiauth.service;

import java.util.Locale;

import gov.diski.diskiauth.domain.User;
import gov.diski.diskiauth.domain.UserNotActivatedException;
import gov.diski.diskiauth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class JdbcUserDetailsService implements UserDetailsService {
	
	private final Logger log = LoggerFactory.getLogger(JdbcUserDetailsService.class);

	@Autowired
	private UserRepository credentialsRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		log.debug("Authenticating {}", email);
		String lowercaseLogin = email.toLowerCase(Locale.ENGLISH);
		User userFromDatabase = credentialsRepository.findByEmail(email);
		if (!userFromDatabase.isEnabled()) {
			throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
		}
		return userFromDatabase;
	}
}
