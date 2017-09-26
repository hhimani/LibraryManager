package org.library.login.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity(name="Lib_User_Details")
public class LibUserDetails {
	@Id
	 @GeneratedValue(generator="newGenerator")
	@GenericGenerator(name="newGenerator",strategy="foreign",
	parameters={ @Parameter(name="property", value="libUser")})
	@Column(name="LibraryUserId")
	private int id;
	private String firstName;
	private String lastName;
	private int age;
	@Column(nullable=false)
	private String email;
	private String address;
	@Column(nullable=false)
	private long mobileNo;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="LibraryUserId")
	private LibUser libUser;
	public LibUser getLibUser() {
		return libUser;
	}
	public void setLibUser(LibUser libUser) {
		this.libUser = libUser;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public long getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}
	

	
}
