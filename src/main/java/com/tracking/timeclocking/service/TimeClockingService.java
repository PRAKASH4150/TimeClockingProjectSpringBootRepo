package com.tracking.timeclocking.service;

import java.io.ByteArrayOutputStream;
import java.sql.Time;
import java.util.List;

import com.tracking.timeclocking.entity.TimeClockingDetails;

public interface TimeClockingService {

	public List<TimeClockingDetails> getAllTimeClockRecords();
	public TimeClockingDetails insertTimeClockDetails(TimeClockingDetails timeClockingDetails);
	public List<TimeClockingDetails> getTimeClockingDetailsByUserName(TimeClockingDetails timeClockingDetails);
	public List<TimeClockingDetails> getTimeClockingDetailsByDate(TimeClockingDetails timeClockingDetails);
	public List<TimeClockingDetails> calculateWagesByDateRange(TimeClockingDetails timeClockingDetails);
	public ByteArrayOutputStream generatePDFReport(TimeClockingDetails timeClockingDetails);
	public String printDashes();
	public int timeToMinutes(Time time);
}
