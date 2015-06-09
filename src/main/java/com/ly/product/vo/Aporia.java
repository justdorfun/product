package com.ly.product.vo;

import java.util.Date;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.View;
import org.nutz.dao.entity.annotation.Readonly;


@Table("aporia")
@View("aporia_view")
public class Aporia{

	@Id
	@Column
	private Long id;

	@Column
	@Readonly
	private String productname;
		

	@Column
	private Long productid;

	@Column
	@Readonly
	private String osname;
		

	@Column
	private Long osid;


	@Column
	private String name;

	@Column
	@Readonly
	private String aporialevelname;
		

	@Column
	private Long aporialevelid;


	@Column
	private Date startdate;


	@Column
	private Date finishdate;


	@Column
	private String username;


	@Column
	private String des;





	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}


	public Long getProductid() {
		return productid;
	}

	public void setProductid(Long productid) {
		this.productid = productid;
	}
	public String getOsname() {
		return osname;
	}

	public void setOsname(String osname) {
		this.osname = osname;
	}


	public Long getOsid() {
		return osid;
	}

	public void setOsid(Long osid) {
		this.osid = osid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getAporialevelname() {
		return aporialevelname;
	}

	public void setAporialevelname(String aporialevelname) {
		this.aporialevelname = aporialevelname;
	}


	public Long getAporialevelid() {
		return aporialevelid;
	}

	public void setAporialevelid(Long aporialevelid) {
		this.aporialevelid = aporialevelid;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getFinishdate() {
		return finishdate;
	}

	public void setFinishdate(Date finishdate) {
		this.finishdate = finishdate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}
}
