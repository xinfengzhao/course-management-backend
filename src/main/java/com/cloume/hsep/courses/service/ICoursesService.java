package com.cloume.hsep.courses.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Query;

import com.cloume.hsep.courses.resource.Courses;
import com.cloume.hsep.rest.PagingRestResponse;
import com.cloume.hsep.rest.RestResponse;

public interface ICoursesService {
	
	/**
	 * 创建课程
	 * @param id
	 */
	RestResponse<Courses> create(Map<String, Object> newValus);
	
	/**
	 * 修改指定的课程
	 * @param id
	 */
	RestResponse<Courses> modify(String id,  Map<String, Object> newValus);
	
	/**
	 * 获取指定的课程
	 * @param id
	 */
	RestResponse<Courses> getDetails(String id);
	PagingRestResponse<Courses> getDetails(Query query);
	
	/**
	 * 删除指定的课程
	 * @param id
	 */
	RestResponse<Courses> delete(String id);
	
	boolean verify(Map<String, Object> newValus, Collection<String> keys);
	
}
