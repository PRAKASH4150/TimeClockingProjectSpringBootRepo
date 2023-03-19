package com.tracking.timeclocking.entity;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//import com.tracking.timeclocking.util.CustomTimeDeserializer;


@Entity
@Table(name="time_clocking_details")
public class TimeClockingDetails {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer serialNo;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="day_worked")
	private String dayWorked;
	
	@Column(name="date_worked")
	private Date dateWorked;
	
	 //@JsonDeserialize(using = CustomTimeDeserializer.class)
	@Column(name="check_in")
	private Time checkIn;
	
	// @JsonDeserialize(using = CustomTimeDeserializer.class)
	@Column(name="check_out")
	private Time checkOut;
	
	@Column(name="location")
	private String location;
	
	@Column(name="total_hours_worked")
	private Time totalHoursWorked;
	
	@Transient
	private Date startDate;
	
	@Transient
	private Date endDate;
	
	@Transient
	private double wagePerHour;
	
	@Transient
	private double totalWageAmount;
	
	@Transient
	private boolean reportGeneration;
	
	@Transient 
	private boolean emailStatus;
	
	

	public TimeClockingDetails(Integer serialNo, String userName, String dayWorked, Date dateWorked, Time checkIn,
			Time checkOut, String location, Time totalHoursWorked, Date startDate, Date endDate, double wagePerHour,
			double totalWageAmount, boolean reportGeneration, boolean emailStatus) {
		super();
		this.serialNo = serialNo;
		this.userName = userName;
		this.dayWorked = dayWorked;
		this.dateWorked = dateWorked;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.location = location;
		this.totalHoursWorked = totalHoursWorked;
		this.startDate = startDate;
		this.endDate = endDate;
		this.wagePerHour = wagePerHour;
		this.totalWageAmount = totalWageAmount;
		this.reportGeneration = reportGeneration;
		this.emailStatus = emailStatus;
	}

	public TimeClockingDetails() {
		super();
	}

	public Integer getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDayWorked() {
		return dayWorked;
	}

	public void setDayWorked(String dayWorked) {
		this.dayWorked = dayWorked;
	}

	public Date getDateWorked() {
		return dateWorked;
	}

	public void setDateWorked(Date dateWorked) {
		this.dateWorked = dateWorked;
	}

	public Time getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(Time checkIn) {
		this.checkIn = checkIn;
	}

	public Time getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(Time checkOut) {
		this.checkOut = checkOut;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Time getTotalHoursWorked() {
		return totalHoursWorked;
	}

	public void setTotalHoursWorked(Time totalHoursWorked) {
		this.totalHoursWorked = totalHoursWorked;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public double getWagePerHour() {
		return wagePerHour;
	}

	public void setWagePerHour(double wagePerHour) {
		this.wagePerHour = wagePerHour;
	}

	public double getTotalWageAmount() {
		return totalWageAmount;
	}

	public void setTotalWageAmount(double totalWageAmount) {
		this.totalWageAmount = totalWageAmount;
	}

	public boolean isReportGeneration() {
		return reportGeneration;
	}

	public void setReportGeneration(boolean reportGeneration) {
		this.reportGeneration = reportGeneration;
	}

	public boolean isEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(boolean emailStatus) {
		this.emailStatus = emailStatus;
	}

	
	
}
