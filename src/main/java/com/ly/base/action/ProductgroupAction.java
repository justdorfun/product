package com.ly.base.action;

import com.ly.comm.Bjui;
import com.ly.comm.Page;
import com.ly.comm.ParseObj;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.CacheManager;


import com.ly.base.vo.Productgroup;
import com.ly.base.service.ProductgroupService;


@IocBean
@At("/productgroup")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class ProductgroupAction {

	private static final Log log = Logs.getLog(ProductgroupAction.class);
	
	@Inject
	private ProductgroupService productgroupService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/productgroup_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Productgroup productgroup,
                      HttpServletRequest request){

        Cnd c = new ParseObj(productgroup).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(productgroupService.listCount(c));
            request.setAttribute("list_obj", productgroupService.queryCache(c,p));
        }else{
            p.setRecordCount(productgroupService.count(c));
            request.setAttribute("list_obj", productgroupService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("productgroup", productgroup);
    }

    @At
    @Ok("beetl:/WEB-INF/base/productgroup.html")
    public void edit(@Param("action")int action,
                     @Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("productgroup", null);
        }else{

            Productgroup productgroup = productgroupService.fetch(id);
            if (action == 3)
            {
                //productgroup.setName(null);
            }
            request.setAttribute("productgroup", productgroup);
        }
        request.setAttribute("action", action);
    }

    @At
    @Ok("json")
    public Map<String,String> save(@Param("action")int action,
                                @Param("..")Productgroup productgroup){
        Object rtnObject;
        if (productgroup.getId() == null || productgroup.getId() == 0) {
            rtnObject = productgroupService.dao().insert(productgroup);
        }else{
            if (action == 3) {
                productgroup.setId(null);
                rtnObject = productgroupService.dao().insert(productgroup);
            }else{
                rtnObject = productgroupService.dao().updateIgnoreNull(productgroup);
            }
        }
        CacheManager.getInstance().getCache(ProductgroupService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_productgroup", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  productgroupService.delete(id);
        CacheManager.getInstance().getCache(ProductgroupService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_productgroup",false);
    }

}
