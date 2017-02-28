package com.cloume.hsep.courses.controller;

import java.util.Arrays;
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
import com.cloume.hsep.courses.resource.Courses;
import com.cloume.hsep.courses.service.ICoursesService;
import com.cloume.hsep.rest.PagingRestResponse;
import com.cloume.hsep.rest.RestResponse;


@RestController
@RequestMapping("/courses")
public class CoursesController {
	@Autowired private ICoursesService coursesService;
	
	/**
	 * 创建课程
	 * @param body, 必须包含:
	 *   stage: "指示是哪个年级段"
  		 grade: "指示是哪个年级 和 stage对应"
  		 type: "课程类型 是‘课内’，‘课外’等等"
  		 name: "课程名称"
  		 price: "课程的课时单价"
  		 hours: "课程要求的最短时长"
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public RestResponse<Courses> put(@RequestBody Map<String, Object> body){
		//创建课程
		RestResponse<Courses> course = coursesService.create(body);

		return course;
	}

	/**
	 * 修改某条指定课程的详情
	 * @param id 课程的id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public RestResponse<Courses> post(@PathVariable("id") String id,
			@RequestBody Map<String, Object> body){	
		RestResponse<Courses> course = coursesService.modify(id, body);
		
		return course;
	}
	
	/**
	 * 获取某条指定课程的详情
	 * @param id 课程的id
	 * @return
	 */
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public RestResponse<Courses> get(@PathVariable("id") String id){		
		RestResponse<Courses> course = coursesService.getDetails(id);
		
		return course;
	}
	
	/**
	 * 根据参数 获取指定类型的课程记录
	 * @param stage 指定年级段
	 * @param grade 指定具体年级的所有科目
	 * @param type 指定类型的课程
	 * @param price 指定单价的课程
	 * @param hours 制定时长的课程
	 * @param name 各个年级的某一门课程
	 * @return
	 */
	
	@RequestMapping(method = RequestMethod.GET)
	public PagingRestResponse<Courses> get(
		@RequestParam(value="stage", defaultValue = "") String stage, 
		@RequestParam(value="grade", defaultValue = "") String grade, 
		@RequestParam(value="type", defaultValue = "") String[] type,
		@RequestParam(value="organization", defaultValue = "COMMON") String organization,
		@RequestParam(value = "limit", defaultValue = "") int[] limitation,
		@RequestParam(value="price",defaultValue = "0") long price, 
		@RequestParam(value="hours", defaultValue = "0") long hours,
		@RequestParam(value="name", defaultValue = "") String name){
		 
		Query query = new Query();

		//根据指定条件筛选对应条目
		if(!stage.isEmpty()){
			query.addCriteria(Criteria.where("stage").is(stage));
		}
		if(!grade.isEmpty()){
			query.addCriteria(Criteria.where("grade").is(grade));
		}
		if(price != 0){
			query.addCriteria(Criteria.where("price").is(price));
		}
		if(hours != 0){
			query.addCriteria(Criteria.where("hours").is(hours));
		}
		if(!name.isEmpty()){
			query.addCriteria(Criteria.where("name").is(name));
		}
		
		if(type.length > 0){
            query.addCriteria(Criteria.where("type").in(Arrays.asList(type)));
        }
		if(limitation.length > 0){
			query.skip(limitation[0] * limitation[1]).limit(limitation[1]);
		}
		query.addCriteria(Criteria.where("organization").is(organization));
		PagingRestResponse<Courses> course = coursesService.getDetails(query);
		
		return course;
	}
	/**
	 * 删除指定条目的课程
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	   public RestResponse<Courses> delete(@PathVariable("id") String id){
	     
		RestResponse<Courses> course = coursesService.delete(id);
		
		return course;
	   }
	
}
