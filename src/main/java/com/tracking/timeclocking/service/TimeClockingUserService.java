package com.tracking.timeclocking.service;

import com.tracking.timeclocking.entity.TimeClockingUserDetails;

public interface TimeClockingUserService {
	
	public TimeClockingUserDetails saveUserDetails(TimeClockingUserDetails timeClockingUserDetails);
	
	public TimeClockingUserDetails authenticateUser(TimeClockingUserDetails timeClockingUserDetails);
	
	public TimeClockingUserDetails checkUserExistence(TimeClockingUserDetails timeClockingUserDetails);
	
	public TimeClockingUserDetails verifyOtp(TimeClockingUserDetails timeClockingUserDetails);
	
	public TimeClockingUserDetails updatePassword(TimeClockingUserDetails timeClockingUserDetails);
}
