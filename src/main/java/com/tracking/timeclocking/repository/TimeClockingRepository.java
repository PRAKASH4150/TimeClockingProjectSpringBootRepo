package com.tracking.timeclocking.repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tracking.timeclocking.entity.TimeClockingDetails;

public interface TimeClockingRepository extends JpaRepository<TimeClockingDetails,Integer> {
	
	@Query("SELECT tcd FROM TimeClockingDetails tcd where tcd.userName=:userName")
	public List<TimeClockingDetails> getTimeClockingDetailsByUserName(@Param("userName") String userName);
	
	@Query("SELECT tcd FROM TimeClockingDetails tcd where tcd.userName=:userName AND (tcd.dateWorked>=:startDate AND tcd.dateWorked<=:endDate)")
	public List<TimeClockingDetails> getTimeClockingDetailsByDate(@Param("userName") String userName,
			@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	
	@Modifying
    @Query("UPDATE TimeClockingDetails tcd SET tcd.dateWorked=:dateWorked, tcd.dayWorked=:dayWorked,tcd.checkIn=:checkIn,tcd.checkOut=:checkOut,"
    		+ "tcd.location=:location, tcd.totalHoursWorked=:totalHoursWorked where tcd.serialNo=:serialNo")
    void updateUserNameById(@Param("dateWorked") Date dateWorked, @Param("dayWorked") String dayWorked,@Param("checkIn")Time checkIn,
    		@Param("checkOut")Time checkOut,@Param("location") String location,@Param("totalHoursWorked")Time totalHoursWorked,@Param("serialNo") Integer serialNo);
	
}
