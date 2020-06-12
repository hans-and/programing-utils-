package hasses.magical.tools.service;

import javax.validation.Valid;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.servlet.ModelAndView;

import hasses.magical.tools.model.User;
import hasses.magical.tools.model.Userupdate;

public interface UserService {

	public void saveUser(User user);
	
	public boolean isUserAlreadyPrecent(User user);

	public boolean setConfirmation(String email, String confpswrd);

	public boolean updatePassword(@Valid Userupdate update, User user, ModelAndView modelAndView, String messagePlaceHolder);

	User loadUserByUsername(String username) throws UsernameNotFoundException;

	public User findUserByEmail(String userEmail);

	public void createPasswordResetTokenForUser(User user, String token);

}
