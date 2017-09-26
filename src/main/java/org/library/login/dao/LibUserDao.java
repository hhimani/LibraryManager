package org.library.login.dao;

import org.library.login.model.LibUser;
import org.library.login.model.LibUserDetails;

public interface LibUserDao {
	boolean createUser(LibUser user,LibUserDetails libUserDetails);// give error if given username matches with existing username
	boolean validateLogin(LibUser user);
	String UserMembership(LibUser user);
	void deleteUser(LibUser user);
	void signOut();
	void setFine(LibUser user,long fine);
	
	//fetch Lib User if name is given
	LibUser fetchLibUser(String userName);
	//fetch Lib User Details
	LibUserDetails fetchLibUserDetails(int libUserId);
	//save LibUserDetails for objects which have not update account details on login in previous version of App
	void saveLibUserDetails(LibUserDetails libUserDetails);
	//update LibUserDetails of users exisitng in the Lib_User_Details table
	void updateLibUserDetails(LibUserDetails libUserDetails);
}
