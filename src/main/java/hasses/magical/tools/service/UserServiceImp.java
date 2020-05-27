package hasses.magical.tools.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import hasses.magical.helpers.StringHelper;
import hasses.magical.helpers.ValidationHelper;
import hasses.magical.tools.model.Role;
import hasses.magical.tools.model.User;
import hasses.magical.tools.repository.RoleRepository;
import hasses.magical.tools.repository.UserRepository;

@Service
public class UserServiceImp implements UserService {

	private static final Logger LOGGER = Logger.getLogger(UserServiceImp.class);
	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	RoleRepository rolerepository;

	@Autowired
	UserRepository userrepository;

	@Override
	public void saveUser(User user) {
		
		String unencodedpswrd = user.getPassword();
		String encodedpswrd = encoder.encode(unencodedpswrd);
		LOGGER.debug("saves user with pswrd unencoded: "+user.getPassword()+" encodedpswrd: "+encodedpswrd);
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
		if(user==null||confpswrd==null||(!confpswrd.equals(user.getConfirmation()))) {
			return false;
		}
		user.setStatus("VERIFIED");
		userrepository.save(user);
		return true;
	}

}
