package com.home.app.rest.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
	LoginService loginRepoImpl;
	
	
	
	
	
	
	
	@GET
	@Path("deleteAll")
	public void deleteAll(){		
		billsRepoImpl.deleteAll();
	}
	
	@GET
	@Path("getBills")
	@Produces(MediaType.APPLICATION_JSON)
	public List<BillsEntity> getBillEnteries() throws UserNotFoundException{		
		LoginEntity user= this.loginRepoImpl.checkLogin(this.loginRepoImpl.getUserName());
		List<BillsEntity> list = billsRepoImpl.findByLoginEntity(user);
		list.sort((b1,b2) -> {
			if(b1.getBillDate().compareTo(b2.getBillDate()) < 0){
				return -1;
			}
			else{
				return 1;
			}
		});
		return list;
	}
	@GET
	@Path("getBillById")
	@Produces(MediaType.APPLICATION_JSON)
	public BillsEntity getBillById(@QueryParam("id") String id) throws UserNotFoundException{		
		//LoginEntity user= this.loginRepoImpl.checkLogin(this.loginRepoImpl.getUserName());
		BillsEntity b =  billsRepoImpl.findById(id);
		if(!b.getLoginEntity().getEmail().equals(this.loginRepoImpl.getUserName())){
			throw new UserNotFoundException("Bill Not Matching with User Email");
		}
		return b;
		
	}
	@POST
	@Path("device")
	public void device(@FormParam("deviceId") String deviceId){
		pushNotificationService.setDeviceToken(deviceId);
		System.out.println(deviceId +"<<<<");
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
	protected void saveBillEntry(BillsRequest billRequest) throws ParseException, UserNotFoundException{
		LoginEntity user= this.loginRepoImpl.checkLogin(this.loginRepoImpl.getUserName());		
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
		b.setLoginEntity(user);
		if(!StringUtils.isEmpty(billRequest.getId())){
			b.setId(billRequest.getId());
			HomeAppUtility.setAuditInfo(b, "U", billRequest.getEmail());
		}
		else{
			HomeAppUtility.setAuditInfo(b, "A", billRequest.getEmail());
		}
		billsRepoImpl.save(b);
	}
	
	@POST
	@Path("submit")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseMessage submitBillEnteries(@RequestParam BillsRequest billRequest){
		ResponseMessage s=new ResponseMessage();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");	
		SimpleDateFormat sf2=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		try{
			saveBillEntry(billRequest);
			String deviceId = loginRepoImpl.getDeviceId(loginRepoImpl.getUserName());
			System.out.println(deviceId);
			if(deviceId != null){
			pushNotificationService.
				submitTask(String.format(MESSAGE_FORMAT,billRequest.getAmount(),
						sf2.format(sf.parse(billRequest.getBillDateTime()))),deviceId);
			}
			s.setSuccess(true);
			System.out.println("success");
			return s;	
		}
		catch(Exception e){
			System.out.println("error");
			LOGGER.error("Error in Bill Entry",e);
			s.setMessage(e.getMessage());
			return s;	
		}
	}
	@POST
	@Path("deleteBill")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseMessage deleteBill(@RequestParam BillsRequest billEntity) throws UserNotFoundException{
		LoginEntity user= this.loginRepoImpl.checkLogin(this.loginRepoImpl.getUserName());
		BillsEntity bill= billsRepoImpl.findOne(billEntity.getId()); 
		ResponseMessage s=new ResponseMessage();
		if(bill.getLoginEntity().getEmail().equals(user.getEmail())){
			billsRepoImpl.delete(bill);
			s.setSuccess(true);
			return s;
		}
		throw new UserNotFoundException("User Email Id Mismatch");		
	}
	@POST
	@Path("updateBill")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseMessage updateBill(@RequestParam BillsRequest billEntity) throws UserNotFoundException{
		LoginEntity user= this.loginRepoImpl.checkLogin(this.loginRepoImpl.getUserName());
		BillsEntity bill= billsRepoImpl.findOne(billEntity.getId()); 
		if(bill.getLoginEntity().getEmail().equals(user.getEmail())){
			return this.submitBillEnteries(billEntity);
		}
		throw new UserNotFoundException("User Email Id Mismatch");	
	}
}
