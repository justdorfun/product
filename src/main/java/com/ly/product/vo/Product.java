package com.ly.product.vo;

import java.util.Date;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.View;
import org.nutz.dao.entity.annotation.Readonly;


@Table("product")
@View("product_view")
public class Product{

	@Id
	@Column
	private Long id;


	@Column
	private String name;

	@Column
	@Readonly
	private String osname;
		

	@Column
	private Long osid;

	@Column
	@Readonly
	private String studioname;
		

	@Column
	private Long studioid;

	@Column
	@Readonly
	private String productgroupname;
		

	@Column
	private Long productgroupid;


	@Column
	private String url;


	@Column
	private Date startdate;


	@Column
	private Date enddate;


	@Column
	private String info;


	@Column
	private String username;


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
	public String getStudioname() {
		return studioname;
	}

	public void setStudioname(String studioname) {
		this.studioname = studioname;
	}


	public Long getStudioid() {
		return studioid;
	}

	public void setStudioid(Long studioid) {
		this.studioid = studioid;
	}
	public String getProductgroupname() {
		return productgroupname;
	}

	public void setProductgroupname(String productgroupname) {
		this.productgroupname = productgroupname;
	}


	public Long getProductgroupid() {
		return productgroupid;
	}

	public void setProductgroupid(Long productgroupid) {
		this.productgroupid = productgroupid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
