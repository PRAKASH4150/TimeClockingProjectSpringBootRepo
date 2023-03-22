package com.tracking.timeclocking.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tracking.timeclocking.entity.TimeClockingDetails;
import com.tracking.timeclocking.entity.TimeClockingUserDetails;
import com.tracking.timeclocking.exception.NoRecordFoundException;
import com.tracking.timeclocking.exception.NoUserFoundException;
import com.tracking.timeclocking.service.TimeClockingService;
import com.tracking.timeclocking.service.TimeClockingUserService;


@RestController
@RequestMapping("/timeclocking")
@CrossOrigin(origins = {"http://localhost:4200","http://timeclcokingapplicationftnd-env.eba-wd3dqwjn.us-east-1.elasticbeanstalk.com/"})
public class TimeClockingContoller {
	
	@Autowired
	private TimeClockingService timeClockingService;
	@Autowired
	private TimeClockingUserService timeClockingUserService;
	
	@PutMapping("/signup")
	public ResponseEntity<TimeClockingUserDetails> insertTimeClockDetails(@RequestBody TimeClockingUserDetails timeClockingUserDetails)
	{
		return new ResponseEntity<>(timeClockingUserService.saveUserDetails(timeClockingUserDetails),new HttpHeaders(),HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<TimeClockingUserDetails> authenticateUser(@RequestBody TimeClockingUserDetails timeClockingUserDetails)
	{
		return new ResponseEntity<>(timeClockingUserService.authenticateUser(timeClockingUserDetails),new HttpHeaders(),HttpStatus.OK);
	}
	
	@GetMapping("/getallrecs")
	public ResponseEntity<List<TimeClockingDetails>> getAllTimeClockRecords()
	{
		return new ResponseEntity<>(timeClockingService.getAllTimeClockRecords(),new HttpHeaders(),HttpStatus.OK);
	}
	
	@PutMapping("/insertrecord")
	public ResponseEntity<TimeClockingDetails> insertTimeClockDetails(@RequestBody TimeClockingDetails timeClcokingDetails)
	{
		return new ResponseEntity<>(timeClockingService.insertTimeClockDetails(timeClcokingDetails),new HttpHeaders(),HttpStatus.CREATED);
	}
	
	@PostMapping("/getbyusername")
	public ResponseEntity<List<TimeClockingDetails>> getTimeClockingDetailsByUserName(@RequestBody TimeClockingDetails timeClcokingDetails)
	{
		List<TimeClockingDetails> timeClockingDetailsOutput=timeClockingService.getTimeClockingDetailsByUserName(timeClcokingDetails);
		if(timeClockingDetailsOutput.isEmpty())
		{
			throw new NoUserFoundException();
		}
		else
		{
			return new ResponseEntity<>(timeClockingDetailsOutput,new HttpHeaders(),HttpStatus.OK);
		}
		
	}
	
	@PostMapping("/deletebyid")
	public ResponseEntity<TimeClockingDetails> deleteRecordById(@RequestBody TimeClockingDetails timeClcokingDetails)
	{
		TimeClockingDetails timeClockingDetailsOutput=timeClockingService.deleteRecordById(timeClcokingDetails);
		
			return new ResponseEntity<>(timeClockingDetailsOutput,new HttpHeaders(),HttpStatus.OK);	
	}
	
	@PostMapping("/getbydate")
	public ResponseEntity<List<TimeClockingDetails>> getTimeClockingDetailsByDate(@RequestBody TimeClockingDetails timeClcokingDetails)
	{
		List<TimeClockingDetails> timeClockingDetailsOutput=timeClockingService.getTimeClockingDetailsByDate(timeClcokingDetails);
		if(timeClockingDetailsOutput.isEmpty())
		{
			throw new NoRecordFoundException();
		}
		else
		{
			return new ResponseEntity<>(timeClockingDetailsOutput,new HttpHeaders(),HttpStatus.OK);
		}
		
	}

	
	@PostMapping("/getWages")
	public ResponseEntity<List<TimeClockingDetails>> calculateWagesByDateRange(@RequestBody TimeClockingDetails timeClcokingDetails)
	{
		List<TimeClockingDetails> timeClockingDetailsOutput=timeClockingService.calculateWagesByDateRange(timeClcokingDetails);
		if(timeClockingDetailsOutput.isEmpty())
		{
			throw new NoRecordFoundException();
		}
		else
		{
			return new ResponseEntity<>(timeClockingDetailsOutput,new HttpHeaders(),HttpStatus.OK);
		}
		
	}
	
	  @PostMapping(value = "/generatereport")
	    public ResponseEntity<byte[]> generateReport(@RequestBody TimeClockingDetails timeClcokingDetails){
		  byte[] pdfBytes =  timeClockingService.generatePDFReport(timeClcokingDetails).toByteArray();
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_PDF);
		    headers.setContentDisposition(ContentDisposition.builder("inline").filename("report.pdf").build());
		    return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
	    }
	    
	    @PostMapping(value = "/emailreport")
	    public boolean emailPDFReport(@RequestBody TimeClockingDetails timeClcokingDetails){
		return  timeClockingService.emailPDFReport(timeClcokingDetails);	    
	    }
	    @GetMapping("/test")
		public String testEndPoint()
		{
			return "It is working!!";
		}
	   
}
