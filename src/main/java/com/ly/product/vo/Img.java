package com.ly.product.vo;

import java.util.Date;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.View;
import org.nutz.dao.entity.annotation.Readonly;


@Table("img")
@View("img_view")
public class Img{

	@Id
	@Column
	private Long id;

	@Column
	@Readonly
	private String productname;
		

	@Column
	private Long productid;


	@Column
	private String name;


	@Column
	private String imgurl;


	@Column
	private Date adddate;


	@Column
	private String des;


	@Column
	private String memo;





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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public Date getAdddate() {
		return adddate;
	}

	public void setAdddate(Date adddate) {
		this.adddate = adddate;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
