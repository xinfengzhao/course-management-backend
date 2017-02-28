package com.cloume.hsep.courses.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.cloume.hsep.beanutils2.IConverter;
import com.cloume.hsep.beanutils2.Updater;
import com.cloume.hsep.courses.resource.Types;
import com.cloume.hsep.rest.RestResponse;
import com.cloume.hsep.service.base.AbstractServiceBase;

@Service
public class TypesService extends AbstractServiceBase implements ITypesService {

	@Override
	public RestResponse<Types> create(Map<String, Object> newValus) {
		//构造新的课程类别记录
		if(newValus.get("organization") == "" || newValus.get("organization") == null){
			return RestResponse.bad(-11002, "properties organization can not be empty!", null);
		}
		
		Types type = new Updater<Types>(new Types()).update(newValus, new IConverter() {
	           @Override public Entry<String, Object> convert(String key, Object value) {
	               return pair(key, value);
	           }
	       });

		getMongoTemplate().save(type);
		
		return RestResponse.good(type);
	}

	@Override
	public List<Types> getDetails(Query query) {
		List<Types> type = getMongoTemplate().find(query, Types.class);
		return type;
	}

	@Override
	public RestResponse<Types> delete(String id) {
		getMongoTemplate().remove(Query.query(Criteria.where("id").is(id)), Types.class);
		return RestResponse.good(null);
	}

}
