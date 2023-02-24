package com.tracking.timeclocking.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tracking.timeclocking.entity.TimeClockingDetails;

public interface TimeClockingRepository extends JpaRepository<TimeClockingDetails,Integer> {
	
	@Query("SELECT tcd FROM TimeClockingDetails tcd where tcd.userName=:userName")
	public List<TimeClockingDetails> getTimeClockingDetailsByUserName(@Param("userName") String userName);
	
	@Query("SELECT tcd FROM TimeClockingDetails tcd where tcd.userName=:userName AND (tcd.dateWorked>=:startDate AND tcd.dateWorked<=:endDate)")
	public List<TimeClockingDetails> getTimeClockingDetailsByDate(@Param("userName") String userName,
			@Param("startDate")Date startDate,@Param("endDate")Date endDate);
}
