package com.tracking.timeclocking.service;

import com.tracking.timeclocking.entity.TimeClockingUserDetails;

public interface TimeClockingUserService {
	
	public TimeClockingUserDetails saveUserDetails(TimeClockingUserDetails timeClockingUserDetails);
	
	public TimeClockingUserDetails authenticateUser(TimeClockingUserDetails timeClockingUserDetails);
}
