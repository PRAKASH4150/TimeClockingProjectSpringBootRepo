package com.tracking.timeclocking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tracking.timeclocking.entity.TimeClockingUserDetails;


public interface TimeClockingUserRepository extends JpaRepository<TimeClockingUserDetails,Integer>  {
	
	@Query("SELECT tcud FROM TimeClockingUserDetails tcud where tcud.userName=:userName")
	public List<TimeClockingUserDetails> chcekForDuplicateUsers(@Param("userName") String userName);

}
