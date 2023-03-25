package com.tracking.timeclocking.service.impl;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tracking.timeclocking.entity.TimeClockingUserDetails;
import com.tracking.timeclocking.repository.TimeClockingUserRepository;
import com.tracking.timeclocking.service.TimeClockingUserService;
import com.tracking.timeclocking.util.EmailService;

@Service
public class TimeClockingUserServiceImpl implements TimeClockingUserService{
	
	private static final int STRENGTH = 10;
	  private static final int OTP_LENGTH = 6;

    @Autowired 
    private TimeClockingUserRepository timeClockingUserRepository;
    
    @Autowired
    private EmailService emailService;
   

	@Override
	public TimeClockingUserDetails saveUserDetails(TimeClockingUserDetails timeClockingUserDetails) {
		
		if(timeClockingUserRepository.chcekForDuplicateUsers(timeClockingUserDetails.getUserName()).size()>0)
		{
			timeClockingUserDetails.setUserExistanceFlag(true);
		}
		else
		{
			 BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(STRENGTH, new SecureRandom());
			 timeClockingUserDetails.setUserExistanceFlag(false);
			 timeClockingUserDetails.setPassword(bCryptPasswordEncoder.encode(timeClockingUserDetails.getPassword()));
			timeClockingUserRepository.save(timeClockingUserDetails);
		}
		
		return timeClockingUserDetails;
	}


	@Override
	public TimeClockingUserDetails authenticateUser(TimeClockingUserDetails timeClockingUserDetails) {
		
		List<TimeClockingUserDetails> retrievedUserList=timeClockingUserRepository.chcekForDuplicateUsers(timeClockingUserDetails.getUserName());
		
		if(retrievedUserList.size()>0)
		{
			 BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(STRENGTH, new SecureRandom());
			 if(bCryptPasswordEncoder.matches(timeClockingUserDetails.getPassword(), retrievedUserList.get(0).getPassword()))
			 {
				 timeClockingUserDetails.setAuthenticationFlag(true);
			 }
			 else
			 {
				 timeClockingUserDetails.setAuthenticationFlag(false);
			 }
		}
		else
		{
			timeClockingUserDetails.setAuthenticationFlag(false);
		}
		
		 return timeClockingUserDetails;
	}


	@Override
	public TimeClockingUserDetails checkUserExistence(TimeClockingUserDetails timeClockingUserDetails) {
		List<TimeClockingUserDetails> timeClockingUserDetailsList=timeClockingUserRepository.checkUserExistence(timeClockingUserDetails.getUserName(),timeClockingUserDetails.getEmail());
		if(timeClockingUserDetailsList.size()==0)
		{
			timeClockingUserDetails.setErrorMsg("No User found in our records.");
		}
		else
		{
			Random random = new Random();
		    StringBuilder builder = new StringBuilder();
		    for (int i = 0; i < OTP_LENGTH; i++) {
		        builder.append(random.nextInt(10));
		      }
		    timeClockingUserDetails.setOtp(builder.toString());
		    timeClockingUserRepository.updateOtp(timeClockingUserDetails.getOtp(),timeClockingUserDetails.getUserName(),timeClockingUserDetails.getEmail());
		    emailService.sendSimpleMessage(timeClockingUserDetails.getEmail(),"OTP for Time Sheet Management System (Reset Password)","Hi "+
		    timeClockingUserDetails.getUserName()+",\n\nOTP for resetting your passoword is "+timeClockingUserDetails.getOtp()+".\n\nThankyou.\n\nRegards,\nAdministrator");
		    timeClockingUserDetails.setOtp(null);
		}
		return timeClockingUserDetails;
	}


	@Override
	public TimeClockingUserDetails verifyOtp(TimeClockingUserDetails timeClockingUserDetails) {
		TimeClockingUserDetails timeClockingUserDetailsObject=timeClockingUserRepository.checkUserExistence(timeClockingUserDetails.getUserName(),timeClockingUserDetails.getEmail()).get(0);
		if(timeClockingUserDetails.getOtp().compareTo(timeClockingUserDetailsObject.getOtp())==0)
		{
			timeClockingUserDetails.setErrorMsg(null);
		}
		else
		{
			timeClockingUserDetails.setErrorMsg("Invalid OTP");
		}
		return timeClockingUserDetails;
	}


	@Override
	public TimeClockingUserDetails updatePassword(TimeClockingUserDetails timeClockingUserDetails) {
		
		 BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(STRENGTH, new SecureRandom());
		 timeClockingUserDetails.setPassword(bCryptPasswordEncoder.encode(timeClockingUserDetails.getPassword()));
		 timeClockingUserRepository.updatePassword(timeClockingUserDetails.getPassword(),timeClockingUserDetails.getUserName(),timeClockingUserDetails.getEmail());
		 return timeClockingUserDetails;
	}
	
	

}
