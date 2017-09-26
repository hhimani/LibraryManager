package org.library.login.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.library.login.model.LibUser;
import org.library.login.model.LibUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LibUserDaoImpl implements LibUserDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	//Input LibUser field will not have a memberType parameter, we have to set it for all people
	//return true if unique user credentials are unique and add it to the db else false
	
	public boolean createUser(LibUser user,LibUserDetails userDetails){
		String userName=user.getUserName();
		String hql="From LibUser WHERE userName=:userName";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("userName",userName);
		LibUser dbUser;
		if(query.uniqueResult()!=null)
		dbUser=(LibUser) query.uniqueResult();
		else 
			dbUser=null;
		if(dbUser!=null && dbUser.getUserName().equals(userName))
			return false;
		else{
				user=setUserMembership(user);
				//Essential For One to One Mapping
				user.setLibUserDetails(userDetails);
				userDetails.setLibUser(user);
				
				sessionFactory.getCurrentSession().save(user);
				return true;
			}	
	}
	//helper method of createUser
	private static LibUser setUserMembership(LibUser user){
		String adminUserName="himani1349";// implement code to soft code these params also later on 
		String adminPassword="himani";
		if(user.getUserName().equals(adminUserName) && user.getPassword().equals(adminPassword))
			user.setMemberType("ADMIN");
		else
			user.setMemberType("NORMAL");
		return user;
	}
	
	public boolean validateLogin(LibUser user) {
		String userName=user.getUserName();
		String hql="From LibUser WHERE userName=:userName";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("userName",userName);
		LibUser dbUser=(LibUser) query.uniqueResult();//because query.list() returns null in case login is not created
		if(dbUser!=null && user.getUserName().equals(dbUser.getUserName()) && user.getPassword().equals(dbUser.getPassword()))
			return true;
		else 
			return false;
	}

	public String UserMembership(LibUser user) {
		String userName=user.getUserName();
		String hql="From LibUser WHERE userName=:userName";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("userName",userName);
		LibUser dbUser=(LibUser) query.list().get(0);
		return dbUser.getMemberType();
	}

	public void deleteUser(LibUser user) {
		String userName=user.getUserName();
		String hql="delete LibUser WHERE userName=:userName";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("userName",userName);
		query.executeUpdate();
		
	}
	public void signOut(){
	}

	public void setFine(LibUser user,long fine) {
		String userName=user.getUserName();
		String hql="update LibUser set fine=:fine WHERE userName=:userName";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("fine", fine);
		query.setParameter("userName",userName);
		query.executeUpdate();
		
		
	}

	public void createUserDetails(LibUserDetails libUserDetails) {
		sessionFactory.getCurrentSession().save(libUserDetails);
	}

	public LibUser fetchLibUser(String userName) {
		String hql="From LibUser WHERE userName=:userName";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("userName", userName);
		return (LibUser)query.uniqueResult();
	}
	
	public LibUserDetails fetchLibUserDetails(int LibraryUserId) {
		String hql="From Lib_User_Details WHERE LibraryUserId=:LibraryUserId";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("LibraryUserId", LibraryUserId);
		return (LibUserDetails)query.uniqueResult();
	}

	public void saveLibUserDetails(LibUserDetails libUserDetails) {
		sessionFactory.getCurrentSession().save(libUserDetails);
	}

	public void updateLibUserDetails(LibUserDetails libUserDetails) {
		int LibraryUserId=libUserDetails.getId();
		String firstName=libUserDetails.getFirstName();
		String lastName=libUserDetails.getLastName();
		int age=libUserDetails.getAge();
		String email=libUserDetails.getEmail();
		String address=libUserDetails.getAddress();
		long mobileNo=libUserDetails.getMobileNo();
		String hql="Update Lib_User_Details set firstName=:firstName,lastName=:lastName,age=:age,email=:email,address=:address"
				+ ",mobileNo=:mobileNo where LibraryUserId=:LibraryUserId";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("LibraryUserId", LibraryUserId);
		query.setParameter("firstName", firstName);
		query.setParameter("lastName", lastName);
		query.setParameter("age", age);
		query.setParameter("email", email);
		query.setParameter("address", address);
		query.setParameter("mobileNo", mobileNo);
		query.executeUpdate();
	}
	
}
