package hasses.magical.tools.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hasses.magical.tools.model.PasswordResetToken;


@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>{

}
