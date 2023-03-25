package com.tracking.timeclocking.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tracking.timeclocking.entity.TimeClockingUserDetails;


public interface TimeClockingUserRepository extends JpaRepository<TimeClockingUserDetails,Integer>  {
	
	@Query("SELECT tcud FROM TimeClockingUserDetails tcud where tcud.userName=:userName")
	public List<TimeClockingUserDetails> chcekForDuplicateUsers(@Param("userName") String userName);
	
	@Query("SELECT tcud FROM TimeClockingUserDetails tcud where tcud.userName=:userName and tcud.email=:email")
	public List<TimeClockingUserDetails> checkUserExistence(@Param("userName") String userName,@Param("email") String email);
	
	 @Transactional
	 @Modifying
	 @Query("UPDATE TimeClockingUserDetails tcud SET tcud.otp=:otp WHERE tcud.userName=:userName and tcud.email=:email")
	    void updateOtp(@Param("otp") String otp,@Param("userName") String userName,@Param("email") String email);
	 
	 @Transactional
	 @Modifying
	 @Query("UPDATE TimeClockingUserDetails tcud SET tcud.password=:password WHERE tcud.userName=:userName and tcud.email=:email")
	    void updatePassword(@Param("password") String password,@Param("userName") String userName,@Param("email") String email);

}
