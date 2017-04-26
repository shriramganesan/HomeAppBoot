package com.home.app.rest.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.home.app.entities.BillsEntity;
import com.home.app.entities.CategoryEntity;
import com.home.app.entities.LoginEntity;
import com.home.app.exception.UserNotFoundException;
import com.home.app.repository.BillsRepoImpl;
import com.home.app.repository.CategoryRepoImpl;
import com.home.app.repository.LoginHistoryRepoImpl;
import com.home.app.repository.LoginRepoImpl;
import com.home.app.repository.ModeRepoImpl;
import com.home.app.rest.request.BillsRequest;
import com.home.app.rest.request.ResponseMessage;
import com.home.app.rest.request.Sanity;
import com.home.app.utility.HomeAppUtility;

@Service
@Path("/bills")
public class BillsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BillsService.class);
	
	private static String MESSAGE_FORMAT = "Bill saved on server for Amount %s on %s";
	@Autowired
	BillsRepoImpl billsRepoImpl;
	
	@Autowired
	CategoryRepoImpl categoryRepoImpl;
	
	@Autowired
	ModeRepoImpl modeRepoImpl;	
	
	@Autowired
	PushNotificationService pushNotificationService;
	
	@Autowired
	LoginRepoImpl loginRepoImpl;
	
	@Autowired
	LoginHistoryRepoImpl loginHistoryRepoImpl;
	
	@GET
	@Path("deleteAll")
	public void deleteAll(){		
		billsRepoImpl.deleteAll();
	}
	
	@GET
	@Path("getBills")
	@Produces(MediaType.APPLICATION_JSON)
	public List<BillsEntity> getBillEnteries(){		
		return billsRepoImpl.findAll();
	}
	
	@POST
	@Path("device")
	public void device(@FormParam("deviceId") String deviceId){
		pushNotificationService.setDeviceToken(deviceId);
		System.out.println(deviceId +"<<<<");
	}
	@POST
	@Path("deleteBill")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseMessage deleteBill(@RequestParam BillsRequest billEntity){
		ResponseMessage s=new ResponseMessage();
		billsRepoImpl.delete(billsRepoImpl.findOne(billEntity.getId()));
		s.setSuccess(true);
		return s;
	}
	@POST
	@Path("updateBill")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseMessage updateBill(@RequestParam BillsRequest billEntity){
		return this.submitBillEnteries(billEntity);
	}
	
	
	@POST
	@Path("syncLocalBilldata")
	@Produces(MediaType.APPLICATION_JSON)
	public Sanity submitBillEnteries(@RequestParam BillsRequest[] billEntities){
		Gson g=new Gson();
	
		//BillsRequest[] billEntities= g.fromJson(billEntity, BillsRequest[].class);
		for(BillsRequest b: billEntities){
			submitBillEnteries(b);
		}
		return new Sanity(true);	
	}
	protected LoginEntity checkLogin(String email) throws UserNotFoundException{
		LoginEntity user=loginRepoImpl.findByEmail(email);
		if(user == null){
			throw new UserNotFoundException("User Not Registered");
		}
		return user;
	}
	protected CategoryEntity checkCategory(String category){
		if(categoryRepoImpl.findByCategoryName(category)==null){
			CategoryEntity newCat=new CategoryEntity();
			newCat.setCategoryName(category);
			newCat.setCreatedOn(new Date());
			newCat.setId(UUID.randomUUID().toString());
			categoryRepoImpl.save(newCat);
			return newCat;
		}else{
			return categoryRepoImpl.findByCategoryName(category);
		}			
		
	}
	@Transactional
	protected void saveBillEntry(BillsRequest billRequest) throws ParseException{
		CategoryEntity cat=this.checkCategory(billRequest.getCategory());
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");	
		BillsEntity b= new BillsEntity();
		b.setAmount(Double.parseDouble(billRequest.getAmount()));
		b.setCreatedOn(new Date());
		b.setDescription(billRequest.getDescription());
		b.setShopName(billRequest.getShopName());
		b.setCategoryId(cat);		
		b.setModeOfPayment(modeRepoImpl.findOne(billRequest.getModeOfPayment()));
		b.setBillImage(billRequest.getBillImage());
		b.setLocation(billRequest.getLocation());
		b.setBillDate(sf.parse(billRequest.getBillDateTime()));
		HomeAppUtility.setAuditInfo(b, "A", billRequest.getEmail());
		billsRepoImpl.save(b);
	}
	
	@POST
	@Path("submit")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseMessage submitBillEnteries(@RequestParam BillsRequest billEntity){
		ResponseMessage s=new ResponseMessage();
		try{
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");	
			SimpleDateFormat sf2=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");	
			BillsEntity b= new BillsEntity();
			b.setAmount(Double.parseDouble(billEntity.getAmount()));
			b.setCreatedOn(new Date());
			b.setDescription(billEntity.getDescription());
			b.setShopName(billEntity.getShopName());
			if(categoryRepoImpl.findByCategoryName(billEntity.getCategory())==null){
				CategoryEntity newCat=new CategoryEntity();
				newCat.setCategoryName(billEntity.getCategory());
				newCat.setCreatedOn(new Date());
				newCat.setId(UUID.randomUUID().toString());
				categoryRepoImpl.save(newCat);
				b.setCategoryId(newCat);
			}else{
				b.setCategoryId(categoryRepoImpl.findByCategoryName(billEntity.getCategory()));
			}			
			b.setModeOfPayment(modeRepoImpl.findOne(billEntity.getModeOfPayment()));
			b.setBillImage(billEntity.getBillImage());
			b.setLocation(billEntity.getLocation());
			b.setBillDate(sf.parse(billEntity.getBillDateTime()));
			if(!StringUtils.isEmpty(billEntity.getId())){
				b.setId(billEntity.getId());
			}			
			LOGGER.debug(billEntity.toString());
			billsRepoImpl.save(b);			
			pushNotificationService.submitTask(String.format(MESSAGE_FORMAT,billEntity.getAmount(),sf2.format(b.getBillDate())));
			s.setSuccess(true);
			System.out.println("success");
			return s;	
		}
		catch(Exception e){
			System.out.println("error");
			//LOGGER.error("Error in Bill Entry",e);
			s.setMessage(e.getMessage());
			return s;	
		}
	}
}
