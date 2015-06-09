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


import com.ly.base.vo.Productlevel;
import com.ly.base.service.ProductlevelService;


@IocBean
@At("/productlevel")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class ProductlevelAction {

	private static final Log log = Logs.getLog(ProductlevelAction.class);
	
	@Inject
	private ProductlevelService productlevelService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/productlevel_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Productlevel productlevel,
                      HttpServletRequest request){

        Cnd c = new ParseObj(productlevel).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(productlevelService.listCount(c));
            request.setAttribute("list_obj", productlevelService.queryCache(c,p));
        }else{
            p.setRecordCount(productlevelService.count(c));
            request.setAttribute("list_obj", productlevelService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("productlevel", productlevel);
    }

    @At
    @Ok("beetl:/WEB-INF/base/productlevel.html")
    public void edit(@Param("action")int action,
                     @Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("productlevel", null);
        }else{

            Productlevel productlevel = productlevelService.fetch(id);
            if (action == 3)
            {
                //productlevel.setName(null);
            }
            request.setAttribute("productlevel", productlevel);
        }
        request.setAttribute("action", action);
    }

    @At
    @Ok("json")
    public Map<String,String> save(@Param("action")int action,
                                @Param("..")Productlevel productlevel){
        Object rtnObject;
        if (productlevel.getId() == null || productlevel.getId() == 0) {
            rtnObject = productlevelService.dao().insert(productlevel);
        }else{
            if (action == 3) {
                productlevel.setId(null);
                rtnObject = productlevelService.dao().insert(productlevel);
            }else{
                rtnObject = productlevelService.dao().updateIgnoreNull(productlevel);
            }
        }
        CacheManager.getInstance().getCache(ProductlevelService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_productlevel", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  productlevelService.delete(id);
        CacheManager.getInstance().getCache(ProductlevelService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_productlevel",false);
    }

}
