package org.library.login.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="LibraryUser")
public class LibUser {
	
	@Id  @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="LibraryUserId")
	private int id;
	private String userName;
	private String password;
	private String memberType;
	private long fine;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="LibraryUserId")
	private LibUserDetails libUserDetails;
	
	
	public LibUserDetails getLibUserDetails() {
		return libUserDetails;
	}
	public void setLibUserDetails(LibUserDetails libUserDetails) {
		this.libUserDetails = libUserDetails;
	}
	public long getFine() {
		return fine;
	}
	public void setFine(long fine) {
		this.fine = fine;
	}
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
