package com.home.app.rest.config;

import java.util.Date;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.app.entities.CategoryEntity;
import com.home.app.entities.ModeEntity;
import com.home.app.repository.CategoryRepoImpl;
import com.home.app.repository.ModeRepoImpl;

@Service
public class LoadMasterData {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoadMasterData.class);
	@Autowired
	CategoryRepoImpl categoryRepoImpl;
	
	@Autowired
	ModeRepoImpl modeRepoImpl;	
	
	
	@PostConstruct
	protected void loadMasterData(){
		
		loadCategoryData();
		loadModesOfPaymentData();
		
	}

	private void loadModesOfPaymentData() {
		String[] modesArr=new String[]{"Credit Card","Debit Card","Cash","Wallet"};
		for(String mode:modesArr){
			if(modeRepoImpl.findOne(mode)==null){
				ModeEntity newMode=new ModeEntity();
				newMode.setModeOfPayment(mode);
				modeRepoImpl.save(newMode);
			}
			
		}
		LOGGER.info("Master Data Loaded for Modes with size {}",modeRepoImpl.findAll().size());
	}

	private void loadCategoryData() {
		String[] catArr=new String[]{"Grocery","Mobile","Petrol/Diesel","Shopping","Travel","Restaurant","Bills"};
		for(String cat:catArr){
			if(categoryRepoImpl.findByCategoryName(cat)==null){
				CategoryEntity newCat=new CategoryEntity();
				newCat.setCategoryName(cat);
				newCat.setCreatedOn(new Date());
				newCat.setId(UUID.randomUUID().toString());
				categoryRepoImpl.save(newCat);
			}
			
		}
		LOGGER.info("Master Data Loaded for Category with size {}",categoryRepoImpl.findAll().size());
	}
	
	
}
