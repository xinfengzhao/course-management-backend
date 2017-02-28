package com.cloume.hsep.courses.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.cloume.hsep.beanutils2.IConverter;
import com.cloume.hsep.beanutils2.Updater;
import com.cloume.hsep.courses.resource.Grades;
import com.cloume.hsep.rest.RestResponse;
import com.cloume.hsep.service.base.AbstractServiceBase;

@Service
public class GradesService extends AbstractServiceBase implements IGradesService {

	@Override
	public RestResponse<Grades> create(Map<String, Object> newValus) {
		//构造新的课程记录
		if(newValus.get("organization") == "" || newValus.get("organization") == null){
			return RestResponse.bad(-11002, "properties organization can not be empty!", null);
		}
		Grades grade = new Updater<Grades>(new Grades()).update(newValus, new IConverter() {
	           @Override public Entry<String, Object> convert(String key, Object value) {
	               return pair(key, value);
	           }
	       });

		getMongoTemplate().save(grade);
		
		return RestResponse.good(grade);
	}

	@Override
	public RestResponse<Grades> delete(String id) {
		getMongoTemplate().remove(Query.query(Criteria.where("id").is(id)), Grades.class);
		return RestResponse.good(null);
	}

	@Override
	public List<Grades> getDetails(Query query) {
		List<Grades> grade = getMongoTemplate().find(query, Grades.class);
		return grade;
	}

}
