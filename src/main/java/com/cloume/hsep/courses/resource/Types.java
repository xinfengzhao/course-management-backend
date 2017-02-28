package com.cloume.hsep.courses.resource;

public class Types {
	private String id;
	
	/**
	 * 类型名
	 */
	private String name;
	
	/**
	 * 类型所属机构
	 */
	private String organization;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
}
