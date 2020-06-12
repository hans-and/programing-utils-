package hasses.magical.tools.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hasses.magical.tools.model.User;
@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
	public User findByEmail(String email);
	public User findByName(String name);
}
