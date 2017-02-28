package com.cloume.hsep.courses.resource;

public class Grades {
	
	private String id;
	
	/**
	 * 具体年级
	 */
	private String name;
	
	/**
	 * 年级所属年级段
	 */
	private String stage;
	
	/**
	 * 年级所属机构
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

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
		
	
}
