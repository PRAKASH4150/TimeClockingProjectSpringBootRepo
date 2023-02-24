package com.tracking.timeclocking.service.impl;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tracking.timeclocking.entity.TimeClockingUserDetails;
import com.tracking.timeclocking.repository.TimeClockingUserRepository;
import com.tracking.timeclocking.service.TimeClockingUserService;

@Service
public class TimeClockingUserServiceImpl implements TimeClockingUserService{
	
	private static final int STRENGTH = 10;

    @Autowired 
    private TimeClockingUserRepository timeClockingUserRepository;
   

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

}
