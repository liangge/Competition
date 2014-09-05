package com.egov.secrecysystem.model;
// default package
// Generated Aug 18, 2014 4:45:24 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * CpUser generated by hbm2java
 */
public class CpUser implements java.io.Serializable {

	private String id;
	private CpRole cpRole;
	private String name;
	private String loginname;
	private String passwd;
	private String remark;
	private Set<CpAnswerRecord> cpAnswerRecords = new HashSet<CpAnswerRecord>(0);

	public CpUser() {
	}

	public CpUser(String id, String loginname, String passwd) {
		this.id = id;
		this.loginname = loginname;
		this.passwd = passwd;
	}

	public CpUser(String id, CpRole cpRole, String name, String loginname,
			String passwd, String remark, Set<CpAnswerRecord> cpAnswerRecords) {
		this.id = id;
		this.cpRole = cpRole;
		this.name = name;
		this.loginname = loginname;
		this.passwd = passwd;
		this.remark = remark;
		this.cpAnswerRecords = cpAnswerRecords;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CpRole getCpRole() {
		return this.cpRole;
	}

	public void setCpRole(CpRole cpRole) {
		this.cpRole = cpRole;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<CpAnswerRecord> getCpAnswerRecords() {
		return this.cpAnswerRecords;
	}

	public void setCpAnswerRecords(Set<CpAnswerRecord> cpAnswerRecords) {
		this.cpAnswerRecords = cpAnswerRecords;
	}

}