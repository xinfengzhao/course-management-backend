package com.cloume.hsep.courses.resource;

public class Courses {
	
	private String id;
	
	/**
	 * 课程所属机构
	 */
	private String organization;
	
	/**
	 * 课程的名称 如 语文
	 */
	private String name;
	
	/**
	 * 课程的年级段 如 初中 高中
	 */
	private String stage;
	
	/**
	 * 课程的具体年级 如 一年级
	 */
	private String grade;
	
	/**
	 * 课程的类型 如 课内 课外
	 */
	private String type;
	
	/**
	 * 课程的单价 精确到分
	 */
	private long price;
	
	/**
	 * 课程的最短可预约时长  单位 小时
	 */
	private long hours;
	
	/**
	 * 课程的创建时间
	 */
	private long createdTime;
	
	public Courses(){
		this.createdTime = System.currentTimeMillis();
	}
	
	public String getId(){
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long getCreatedTime(){
		return createdTime;
	}
	
	public void setCreatedTime(long createdTime) {
		this.createdTime = createdTime;
	}
	
	public String getStage(){
		return stage;
	}
	
	public void setStage(String stage) {
		this.stage = stage;
	}
	
	public String getGrade(){
		return grade;
	}
	
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public String getType(){
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	public long getPrice(){
		return price;
	}
	
	public void setPrice(long price) {
		this.price = price;
	}
	public long getHours(){
		return hours;
	}
	
	public void setHours(long hours) {
		this.hours = hours;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
	

}
