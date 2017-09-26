package org.library.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.library.login.dao.*;
import org.library.login.model.LibUser;
import org.library.login.model.LibUserDetails;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private LibUserDaoImpl libDaoImpl;

	public LibUserDaoImpl getLibDaoImpl() {
		return libDaoImpl;
	}

	public void setLibDaoImpl(LibUserDaoImpl libDaoImpl) {
		this.libDaoImpl = libDaoImpl;
	}
	@Transactional
	public boolean createUser(LibUser user,LibUserDetails libUserDetails) {
		return libDaoImpl.createUser(user,libUserDetails);
	}
	@Transactional
	public boolean validateLogin(LibUser user) {
		return libDaoImpl.validateLogin(user);
	}
	@Transactional
	public String UserMembership(LibUser user) {
		return libDaoImpl.UserMembership(user);
	}
	@Transactional
	public void deleteUser(LibUser user) {
		libDaoImpl.deleteUser(user);
	}
	@Transactional
	public void signOut() {
		libDaoImpl.signOut();
	}
	@Transactional
	public void setFine(LibUser user, long fine) {
		libDaoImpl.setFine(user, fine);
	}
	@Transactional
	public LibUserDetails fetchLibUserDetails(int libUserId){
		return libDaoImpl.fetchLibUserDetails(libUserId);
	}
	@Transactional
	public LibUser fetchLibUser(String userName) {
		return libDaoImpl.fetchLibUser(userName);
	}
	@Transactional
	public void saveLibUserDetails(LibUserDetails libUserDetails) {
		libDaoImpl.saveLibUserDetails(libUserDetails);
	}
	@Transactional
	public void updateLibUserDetails(LibUserDetails libUserDetails) {
		libDaoImpl.updateLibUserDetails(libUserDetails);
	}

}
