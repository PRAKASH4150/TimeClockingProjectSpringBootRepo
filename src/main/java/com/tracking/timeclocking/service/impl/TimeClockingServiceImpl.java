package com.tracking.timeclocking.service.impl;

import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tracking.timeclocking.entity.TimeClockingDetails;
import com.tracking.timeclocking.entity.TimeClockingUserDetails;
import com.tracking.timeclocking.repository.TimeClockingRepository;
import com.tracking.timeclocking.repository.TimeClockingUserRepository;
import com.tracking.timeclocking.service.TimeClockingService;
import com.tracking.timeclocking.util.EmailService;

@Service
public class TimeClockingServiceImpl implements TimeClockingService {

	@Autowired
	private TimeClockingRepository timeClockingRepository;
	
	@Autowired
	private TimeClockingUserRepository timeClockingUserRepository;
	
	
	
	@Autowired
    private EmailService emailService;

	@Override
	public List<TimeClockingDetails> getAllTimeClockRecords() {

		return timeClockingRepository.findAll();
	}

	@Override
	public TimeClockingDetails insertTimeClockDetails(TimeClockingDetails timeClockingDetails) {
		Date date = new Date(timeClockingDetails.getDateWorked().getTime());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		timeClockingDetails
				.setDayWorked(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));

		Duration duration = Duration.between(timeClockingDetails.getCheckIn().toLocalTime(),
				timeClockingDetails.getCheckOut().toLocalTime());
		timeClockingDetails.setTotalHoursWorked(Time.valueOf(String.format("%02d:%02d:00", duration.toHours(),
				duration.minusHours(duration.toHours()).toMinutes())));
		timeClockingRepository.save(timeClockingDetails);
		return timeClockingDetails;
	}

	@Override
	public List<TimeClockingDetails> getTimeClockingDetailsByUserName(TimeClockingDetails timeClockingDetails) {
		return timeClockingRepository.getTimeClockingDetailsByUserName(timeClockingDetails.getUserName());
	}

	@Override
	public List<TimeClockingDetails> getTimeClockingDetailsByDate(TimeClockingDetails timeClockingDetails) {
		return timeClockingRepository.getTimeClockingDetailsByDate(timeClockingDetails.getUserName(),
				timeClockingDetails.getStartDate(), timeClockingDetails.getEndDate());
	}

	@Override
	public List<TimeClockingDetails> calculateWagesByDateRange(TimeClockingDetails timeClockingDetails) {
		List<TimeClockingDetails> timeClockingDetailsRangeList = getTimeClockingDetailsByDate(timeClockingDetails);
		int totalEffortsInMinutes;
		for (TimeClockingDetails tcd : timeClockingDetailsRangeList) {
			totalEffortsInMinutes = timeToMinutes(tcd.getTotalHoursWorked());
			tcd.setWagePerHour(timeClockingDetails.getWagePerHour());
			tcd.setTotalWageAmount((totalEffortsInMinutes * timeClockingDetails.getWagePerHour()) / 60);
		}
		return timeClockingDetailsRangeList;
	}
	
	@Override
	public TimeClockingDetails deleteRecordById(TimeClockingDetails timeClockingDetails) {
		timeClockingRepository.deleteById(timeClockingDetails.getSerialNo());
		return timeClockingDetails;
	}


	@Override
	public ByteArrayOutputStream generatePDFReport(TimeClockingDetails timeClockingDetails) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			List<TimeClockingDetails> timeClockingDetailsRangeList = calculateWagesByDateRange(timeClockingDetails);
			Document document = new Document(PageSize.A4);
			Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.BLUE);
			Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.BLUE);
			PdfWriter.getInstance(document, baos);
			document.open();
			document.add(new Paragraph(printDashes(), headerFont));
			document.add(new Paragraph(StringUtils.repeat(" ", 48) + "TIME SHEET MANAGEMENT REPORT", headerFont));
			document.add(new Paragraph("USER:" + timeClockingDetails.getUserName() + StringUtils.repeat(" ", 30)
					+ "WAGE PER HR:" + timeClockingDetails.getWagePerHour() + "$" + StringUtils.repeat(" ", 30)
					+ "RUN ON:" + LocalDate.now(), headerFont));
			document.add(new Paragraph(printDashes(), headerFont));
			document.add(new Paragraph());

			PdfPTable table = new PdfPTable(7);

			table.setWidthPercentage(100);
			table.setSpacingBefore(10f);
			table.setSpacingAfter(10f);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(new PdfPCell(new Phrase("DATE WORKED", headerFont)));
			table.addCell(new PdfPCell(new Phrase("DAY WORKED", headerFont)));
			table.addCell(new PdfPCell(new Phrase("CHECK IN", headerFont)));
			table.addCell(new PdfPCell(new Phrase("CHECK OUT", headerFont)));
			table.addCell(new PdfPCell(new Phrase("LOCATION", headerFont)));
			table.addCell(new PdfPCell(new Phrase("HRS WORKED", headerFont)));
			table.addCell(new PdfPCell(new Phrase("TOTAL WAGE($)", headerFont)));

			for (TimeClockingDetails tcd : timeClockingDetailsRangeList) {
				table.addCell(new PdfPCell(new Phrase(tcd.getDateWorked() + "", bodyFont)));
				table.addCell(new PdfPCell(new Phrase(tcd.getDayWorked(), bodyFont)));
				table.addCell(new PdfPCell(new Phrase(tcd.getCheckIn() + "", bodyFont)));
				table.addCell(new PdfPCell(new Phrase(tcd.getCheckOut() + "", bodyFont)));
				table.addCell(new PdfPCell(new Phrase(tcd.getLocation() + "", bodyFont)));
				table.addCell(new PdfPCell(new Phrase(tcd.getTotalHoursWorked() + "", bodyFont)));
				table.addCell(new PdfPCell(new Phrase(tcd.getTotalWageAmount() + "", bodyFont)));
			}
			document.add(table);
			document.add(new Paragraph(StringUtils.repeat(" ", 60) + "*****END OF REPORT*****", headerFont));
			document.close();
					
		} catch (Exception e) {
			System.out.println("Error generating PDF: " + e.getMessage());
		}

		return baos;

	}
	
	@Override
	public boolean emailPDFReport(TimeClockingDetails timeClockingDetails) {
		TimeClockingUserDetails timeClockingUserDetailsObject=timeClockingUserRepository.chcekForDuplicateUsers(timeClockingDetails.getUserName()).get(0);
		emailService.sendEmailWithAttachment(timeClockingUserDetailsObject.getEmail(),"Your Wage Report","Hi "+timeClockingUserDetailsObject.getUserName()+",\n\nPlease find your wage report for the period specified.\nThanks & Regards,\n\nAdministrator.",generatePDFReport(timeClockingDetails),"WageReport.pdf");
		return true;	
	}

	
	public int timeToMinutes(Time time) {
		LocalTime localTime = time.toLocalTime();
		int hours = localTime.getHour();
		int minutes = localTime.getMinute();
		int totalMinutes = hours * 60 + minutes;
		return totalMinutes;
	}

	@Override
	public String printDashes() {
		return "----------------------------------------------------------------------------------------------------------------------------------------------";
	}


}
