package com.home.app.utility;

import java.util.Date;

import com.home.app.entities.AbstractAuditEntity;

public class HomeAppUtility {

	public static void setAuditInfo(final AbstractAuditEntity entity,final String action,String userId){
		
		switch(action){			
			case "A":
				entity.setCreatedBy(userId);
				entity.setCreatedOn(new Date());
				break;
			case "U":
				entity.setUpdatedBy(userId);
				entity.setUpdatedOn(new Date());
				break;
		}
	}
}
