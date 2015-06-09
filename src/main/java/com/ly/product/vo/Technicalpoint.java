package com.ly.product.vo;

import java.util.Date;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.View;
import org.nutz.dao.entity.annotation.Readonly;


@Table("technicalpoint")
@View("technicalpoint_view")
public class Technicalpoint{

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

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}
}
