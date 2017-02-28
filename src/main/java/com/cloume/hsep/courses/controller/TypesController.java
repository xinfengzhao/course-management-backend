package com.cloume.hsep.courses.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloume.hsep.courses.resource.Types;
import com.cloume.hsep.courses.service.ITypesService;
import com.cloume.hsep.rest.RestResponse;

@RestController
@RequestMapping("/types")
public class TypesController {
	
	@Autowired private ITypesService typesService;
	
	//创建年级
	@RequestMapping(method = RequestMethod.PUT)
	public RestResponse<Types> put(@RequestBody Map<String, Object> body){
		RestResponse<Types> type = typesService.create(body);
		
		return type;
	}
	
	//获取课程类别
	@RequestMapping(method = RequestMethod.GET)
	public RestResponse<List<Types>> get(	
		@RequestParam(value="organization", defaultValue = "") String organization){
		 
		Query query = new Query();

		//根据指定条件筛选对应条目
		if(!organization.isEmpty()){
			query.addCriteria(Criteria.where("organization").is(organization));
		}
		
		List<Types> type = typesService.getDetails(query);
		
		return RestResponse.good(type);
	}
		
	//删除
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public RestResponse<Types> delete(@PathVariable("id") String id){
		RestResponse<Types> type = typesService.delete(id);
		
		return type;
	 }
}
