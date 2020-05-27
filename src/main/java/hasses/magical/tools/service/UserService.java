package hasses.magical.tools.service;

import hasses.magical.tools.model.User;

public interface UserService {

	public void saveUser(User user);
	
	public boolean isUserAlreadyPrecent(User user);

	public boolean setConfirmation(String email, String confpswrd);
}
