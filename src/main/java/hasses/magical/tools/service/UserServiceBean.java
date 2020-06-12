package hasses.magical.tools.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import hasses.magical.helpers.StringHelper;
import hasses.magical.helpers.ValidationHelper;
import hasses.magical.tools.model.PasswordResetToken;
import hasses.magical.tools.model.Role;
import hasses.magical.tools.model.User;
import hasses.magical.tools.model.Userupdate;
import hasses.magical.tools.repository.PasswordResetTokenRepository;
import hasses.magical.tools.repository.RoleRepository;
import hasses.magical.tools.repository.UserRepository;

@Service
public class UserServiceBean implements UserService {

	private static final Logger LOGGER = Logger.getLogger(UserServiceBean.class);
	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	RoleRepository rolerepository;

	@Autowired
	UserRepository userrepository;
	
	@Autowired
	PasswordResetTokenRepository passwordTokenRepository;

	@Override
	public void saveUser(User user) {

		String unencodedpswrd = user.getPassword();
		String encodedpswrd = encoder.encode(unencodedpswrd);
		LOGGER.debug("saves user with pswrd unencoded: " + user.getPassword() + " encodedpswrd: " + encodedpswrd);
		user.setPassword(encodedpswrd);

		Role userRole = rolerepository.findByRole("SITE_USER");
		if (userRole == null) {
			throw new RuntimeException("Unexpectedly unable to load Role of type SITE_USER");
		}
		Set<Role> roles = new HashSet<Role>();
		roles.add(userRole);
		user.setRoles(roles);

		userrepository.save(user);

	}

	@Override
	public boolean isUserAlreadyPrecent(hasses.magical.tools.model.User user) {
		validateUserEmail(user);

		return userrepository.findByEmail(user.getEmail()) != null;
	}

	private void validateUserEmail(hasses.magical.tools.model.User user) {
		if (user == null) {
			throw new IllegalArgumentException("user must not be null");
		}

		if (StringHelper.isNullOrEmptyOrBlank(user.getEmail())) {
			throw new IllegalArgumentException("user email must be not bee empty");
		}

		if (!ValidationHelper.isValidEmail(user.getEmail())) {
			throw new IllegalArgumentException("user email must be valid");
		}
	}

	@Override
	public boolean setConfirmation(String email, String confpswrd) {
		User user = userrepository.findByEmail(email);
		if (user == null || confpswrd == null || (!confpswrd.equals(user.getConfirmation()))) {
			return false;
		}
		user.setStatus("VERIFIED");
		userrepository.save(user);
		return true;
	}

	@Override
	public boolean updatePassword(@Valid Userupdate update, User user, ModelAndView modelAndView,
			String messagePlaceHolder) {
		try {
			if (isNotEmpty(user.getEmail()) && isNotEmpty(user.getPassword())
					&& user.getEmail().trim().equalsIgnoreCase(update.getEmail().trim())) {

				String unencodedpswrd = update.getNewPassword();
				String encodedpswrd = encoder.encode(unencodedpswrd);
				LOGGER.debug(
						"saves user with pswrd unencoded: " + user.getPassword() + " encodedpswrd: " + encodedpswrd);
				user.setPassword(encodedpswrd);
				userrepository.save(user);
			} else {
				modelAndView.addObject(messagePlaceHolder, "Unexpected exception during update");

				LOGGER.debug("Unexpected exception during update params:"
						+ StringHelper.toCommaString(update, user, modelAndView));

			}

		} catch (Exception e) {
			modelAndView.addObject(messagePlaceHolder, "Unexpected exception during update");
			LOGGER.error("Unexpected exception during update params:"
					+ StringHelper.toCommaString(update, user, modelAndView), e);
		}

		return true;
	}

	private static boolean isNotEmpty(String candidate) {

		return !StringHelper.isNullOrEmptyOrBlank(candidate);
	}

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		// Custom domain User object, not spring security
		User user = userrepository.findByName(username);
		if (user == null) {
			throw new UsernameNotFoundException("Unable to load user by username = '" + username + "'");
		}
		List<Role> userRoles = (List<Role>) rolerepository.findAllById(Collections.singleton(user.getId()));
		user.setRoles(new HashSet<Role>(userRoles));
		return user;
	}

	@Override
	public User findUserByEmail(String userEmail) {
		
		return userrepository.findByEmail(userEmail);
	}

	@Override
	public void createPasswordResetTokenForUser(User user, String token) {
		PasswordResetToken myToken = new PasswordResetToken(token, user);
	    passwordTokenRepository.save(myToken);
		
	}

}
