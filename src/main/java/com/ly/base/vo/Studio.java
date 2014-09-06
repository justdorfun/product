package com.ly.base.vo;

import java.util.Date;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.View;

@Table("studio")
public class Studio{

	@Id
	@Column
	private Long id;

	@Column
	private String name;

	@Column
	private String url;

	@Column
	private String iapname;

	@Column
	private String iappwd;

	@Column
	private String depents;

	@Column
	private String memo;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIapname() {
		return iapname;
	}

	public void setIapname(String iapname) {
		this.iapname = iapname;
	}

	public String getIappwd() {
		return iappwd;
	}

	public void setIappwd(String iappwd) {
		this.iappwd = iappwd;
	}

	public String getDepents() {
		return depents;
	}

	public void setDepents(String depents) {
		this.depents = depents;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
