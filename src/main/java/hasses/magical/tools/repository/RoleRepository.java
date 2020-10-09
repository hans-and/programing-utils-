package hasses.magical.tools.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hasses.magical.tools.model.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	public Role findByRole(String role);

}
