package com.home.app.rest.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.home.app.entities.BillsEntity;
import com.home.app.entities.CategoryEntity;
import com.home.app.entities.ModeEntity;
import com.home.app.repository.BillsRepoImpl;
import com.home.app.repository.CategoryRepoImpl;
import com.home.app.repository.ModeRepoImpl;
import com.mongodb.BasicDBObject;

@Service
@Path("/static")
public class StaticService {

	@Autowired
	CategoryRepoImpl categoryRepoImpl;
	@Autowired
	ModeRepoImpl modeRepoImpl;	
	@Autowired
	BillsRepoImpl billsRepoImpl;
	@GET
	@Path("category")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CategoryEntity> getCategoryData(@QueryParam("keyword") String keyword){	
		return categoryRepoImpl.findAll();
	}
	
	@GET
	@Path("modeOfPayment")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ModeEntity> getModeOfPayment(){	
		return modeRepoImpl.findAll();
	}
	
	@GET
	@Path("categoryHints")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getCategoryHints(@QueryParam("keyword") String keyword,@QueryParam("size") int size){	
		PageRequest request = new PageRequest(0, size, new Sort(Sort.Direction.DESC, "createdOn"));
		Page<CategoryEntity> pages= categoryRepoImpl.findAll(request);
		return pages.getContent().stream().map(CategoryEntity::getCategoryName)
				.filter(s-> !StringUtils.isEmpty(s)).distinct().collect(Collectors.toList());
	}
	
	@GET
	@Path("locationHints")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> locationHints(@QueryParam("keyword") String keyword,@QueryParam("size") int size){	
		PageRequest request = new PageRequest(0, size, new Sort(Sort.Direction.DESC, "billDate"));
		Page<BillsEntity> pages= billsRepoImpl.findAll(request);
		return pages.getContent().stream().map(BillsEntity::getLocation)
					.filter(s-> !StringUtils.isEmpty(s)).distinct().collect(Collectors.toList());
	}
	@GET
	@Path("shopHints")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> shopHints(@QueryParam("keyword") String keyword,@QueryParam("size") int size){	
		PageRequest request = new PageRequest(0, size, new Sort(Sort.Direction.DESC, "billDate"));
		Page<BillsEntity> pages= billsRepoImpl.findAll(request);
		return pages.getContent().stream().map(BillsEntity::getShopName).
				filter(s-> !StringUtils.isEmpty(s)).distinct().collect(Collectors.toList());
	}
	
}
