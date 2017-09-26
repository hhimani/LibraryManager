package org.library.login.service;

import org.library.login.model.LibUser;
import org.library.login.model.LibUserDetails;

public interface UserService {
	boolean createUser(LibUser user,LibUserDetails libUserDetails);
	boolean validateLogin(LibUser user);
	String UserMembership(LibUser user);
	void deleteUser(LibUser user);
	void signOut();
	void setFine(LibUser user,long fine);

	LibUser fetchLibUser(String userName); 
	//LibUserDetails
	LibUserDetails fetchLibUserDetails(int libUserId);
	void saveLibUserDetails(LibUserDetails libUserDetails);
	void updateLibUserDetails(LibUserDetails libUserDetails);

}
