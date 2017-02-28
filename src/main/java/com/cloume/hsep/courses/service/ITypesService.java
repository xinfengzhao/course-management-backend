package com.cloume.hsep.courses.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Query;
import com.cloume.hsep.courses.resource.Types;
import com.cloume.hsep.rest.RestResponse;

public interface ITypesService {
	/**
	 * 创建课程类别记录
	 * @param id
	 */
	RestResponse<Types> create(Map<String, Object> newValus);
	
	/**
	 * 获取课程类别记录
	 * @param id
	 */
	List<Types> getDetails(Query query);
	
	/**
	 * 删除课程类别记录
	 * @param id
	 */
	RestResponse<Types> delete(String id);
}
