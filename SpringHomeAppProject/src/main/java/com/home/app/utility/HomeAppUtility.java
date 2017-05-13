package com.home.app.utility;

import java.util.Calendar;
import java.util.TimeZone;

import com.home.app.entities.AbstractAuditEntity;

public class HomeAppUtility {

	public static void setAuditInfo(final AbstractAuditEntity entity,final String action,String userId){
		Calendar cSchedStartCal = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		switch(action){			
			case "A":
				entity.setCreatedBy(userId);
				entity.setCreatedOn(cSchedStartCal.getTime());
				break;
			case "U":
				entity.setUpdatedBy(userId);
				entity.setUpdatedOn(cSchedStartCal.getTime());
				break;
		}
	}
}
