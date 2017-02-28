package com.cloume.hsep.courses.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Query;
import com.cloume.hsep.courses.resource.Grades;
import com.cloume.hsep.rest.RestResponse;

public interface IGradesService {
	
	/**
	 * 创建年级记录
	 * @param id
	 */
	RestResponse<Grades> create(Map<String, Object> newValus);
	
	/**
	 * 删除年级记录
	 * @param id
	 */
	RestResponse<Grades> delete(String id);
	
	/**
	 * 获取年级信息
	 * @param query
	 * @return
	 */
	List<Grades> getDetails(Query query);
}
