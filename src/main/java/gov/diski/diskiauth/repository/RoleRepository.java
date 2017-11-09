package gov.diski.diskiauth.repository;

import gov.diski.diskiauth.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {

}