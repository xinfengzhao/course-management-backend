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

import com.cloume.hsep.courses.resource.Grades;
import com.cloume.hsep.courses.service.IGradesService;
import com.cloume.hsep.rest.RestResponse;

@RestController
@RequestMapping("/grades")
public class GradesController {
	@Autowired private IGradesService gradesService;
	
	//创建年级
	@RequestMapping(method = RequestMethod.PUT)
	public RestResponse<Grades> put(@RequestBody Map<String, Object> body){
		RestResponse<Grades> grade = gradesService.create(body);

		return grade;
	}
	
	//获取年级
	@RequestMapping(method = RequestMethod.GET)
	public RestResponse<List<Grades>> get(	
		@RequestParam(value="organization", defaultValue = "") String organization,
		@RequestParam(value="stage", defaultValue = "") String stage,
		@RequestParam(value="name", defaultValue = "") String name){
		 
		Query query = new Query();

		//根据指定条件筛选对应条目
		if(!organization.isEmpty()){
			query.addCriteria(Criteria.where("organization").is(organization));
		}
		if(!stage.isEmpty()){
			query.addCriteria(Criteria.where("stage").is(stage));
		}
		if(!name.isEmpty()){
			query.addCriteria(Criteria.where("name").is(name));
		}
		List<Grades> grade = gradesService.getDetails(query);
		
		return RestResponse.good(grade);
	}
	
	//删除
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	   public RestResponse<Grades> delete(@PathVariable("id") String id){
	     
		RestResponse<Grades> grade = gradesService.delete(id);
		
		return grade;
	   }
}
