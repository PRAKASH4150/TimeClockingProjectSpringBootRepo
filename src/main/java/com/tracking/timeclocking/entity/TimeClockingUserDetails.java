package com.tracking.timeclocking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="time_clocking_users")
public class TimeClockingUserDetails {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer serialNo;
	
	@Column(name="email")
	private String email;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="password")
	private String password;
	
	@Transient
	private boolean userExistanceFlag;
	
	@Transient
	private boolean authenticationFlag;

	
	public TimeClockingUserDetails(Integer serialNo, String email, String userName, String password,
			boolean userExistanceFlag, boolean authenticationFlag) {
		super();
		this.serialNo = serialNo;
		this.email = email;
		this.userName = userName;
		this.password = password;
		this.userExistanceFlag = userExistanceFlag;
		this.authenticationFlag = authenticationFlag;
	}

	public TimeClockingUserDetails() {
		super();
	}

	public Integer getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public boolean isUserExistanceFlag() {
		return userExistanceFlag;
	}

	public void setUserExistanceFlag(boolean userExistanceFlag) {
		this.userExistanceFlag = userExistanceFlag;
	}

	public boolean isAuthenticationFlag() {
		return authenticationFlag;
	}

	public void setAuthenticationFlag(boolean authenticationFlag) {
		this.authenticationFlag = authenticationFlag;
	}
	
}
