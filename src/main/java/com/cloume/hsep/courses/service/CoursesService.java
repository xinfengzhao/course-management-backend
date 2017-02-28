package com.cloume.hsep.courses.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cloume.hsep.beanutils2.IConverter;
import com.cloume.hsep.beanutils2.Updater;
import com.cloume.hsep.courses.resource.Courses;
import com.cloume.hsep.rest.ErrorCodes;
import com.cloume.hsep.rest.PagingRestResponse;
import com.cloume.hsep.rest.RestResponse;
import com.cloume.hsep.service.base.AbstractServiceBase;

@Service
public class CoursesService extends AbstractServiceBase implements ICoursesService {
	
	@Override
	//创建课程
	public RestResponse<Courses> create(Map<String, Object> newValus) {
		
		final String[] fields = {"stage", "type", "name"};
		if(newValus.get("stage") == "" || newValus.get("type") == "" || newValus.get("name") == ""){
			return RestResponse.bad(-11002, String.format("properties %s can not be empty!", StringUtils.arrayToCommaDelimitedString(fields)), null);
		}
		
	    //如果stage,grade,name 都一样说明该课程已存在 不可以重复创建
        Query query = new Query();
        query.addCriteria(Criteria.where("stage").is(newValus.get("stage")));
        query.addCriteria(Criteria.where("grade").is(newValus.get("grade")));
        query.addCriteria(Criteria.where("name").is(newValus.get("name")));
        query.addCriteria(Criteria.where("organization").is(newValus.get("organization")));
       
        Courses existResult = getMongoTemplate().findOne(query, Courses.class);
        if(existResult != null){
        	return RestResponse.bad(-11003, "course already exist!", existResult);
        }
		//构造新的课程记录
		Courses course = new Updater<Courses>(new Courses()).update(newValus, new IConverter() {
	           @Override public Entry<String, Object> convert(String key, Object value) {
	               return pair(key, value);
	           }
	       });

		getMongoTemplate().save(course);
		
		return RestResponse.good(course);
	}
	
	

	//修改指定课程
	public RestResponse<Courses> modify(String id, Map<String, Object> newValus) {
		Courses course = getMongoTemplate().findOne(Query.query(Criteria.where("id").is(id)), Courses.class);
		if(course == null){
			return RestResponse.bad(ErrorCodes.EC_NOT_EXISTS, "course not exists!");
		}
		
		course = new Updater<Courses>(course).update(newValus);
		getMongoTemplate().save(course);
		
		return RestResponse.result(0, "course already changed!", course);
	}
	
	//获取指定课程
	public RestResponse<Courses> getDetails(String id) {
		Courses course = getMongoTemplate().findOne(Query.query(Criteria.where("id").is(id)), Courses.class);
		if(course == null){
			return RestResponse.bad(ErrorCodes.EC_NOT_EXISTS, "course not exists!");
		}

		return RestResponse.good(course);
	}
	
	//根据筛选条件获取课程
	public PagingRestResponse<Courses> getDetails(Query query) {
		List<Courses> course = getMongoTemplate().find(query, Courses.class);
		long count =  getMongoTemplate().count(query, Courses.class);
 		if(course.isEmpty()){
			return null;
		}
		
		return PagingRestResponse.good(course, count);
	}
	
	@Override
	//删除指定课程
	public RestResponse<Courses> delete(String id) {
		Courses course = getMongoTemplate().findOne(Query.query(Criteria.where("id").is(id)), Courses.class);

		if(course == null){
			return RestResponse.bad(ErrorCodes.EC_NOT_EXISTS, "course not exists");
        }
		
		getMongoTemplate().remove(Query.query(Criteria.where("id").is(id)), Courses.class);
		return RestResponse.result(0, "course delete successful!", null);
	}
	
	@Override
	public boolean verify(Map<String, Object> newValus, Collection<String> keys){
		return newValus.keySet().containsAll(keys);
	}



	
}
