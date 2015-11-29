package com.ly.product.action;

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


import com.ly.product.vo.Publish;
import com.ly.product.service.PublishService;


@IocBean
@At("/publish")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class PublishAction {

	private static final Log log = Logs.getLog(PublishAction.class);
	
	@Inject
	private PublishService publishService;

    @At("/")
    @Ok("beetl:/WEB-INF/product/publish_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Publish publish,
                      @Param("productid")long productid,
                      HttpServletRequest request){

        Cnd c = new ParseObj(publish).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(publishService.listCount(c));
            request.setAttribute("list_obj", publishService.queryCache(c,p));
        }else{
            p.setRecordCount(publishService.count(c));
            request.setAttribute("list_obj", publishService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("publish", publish);
        request.setAttribute("productid",productid);
    }

    @At
    @Ok("beetl:/WEB-INF/product/publish.html")
    public void edit(@Param("id")Long id,
                     @Param("productid")long productid,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("publish", null);
        }else{
            request.setAttribute("publish", publishService.fetch(id));
        }
        request.setAttribute("productid",productid);
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Publish publish){
        Object rtnObject;
        if (publish.getId() == null || publish.getId() == 0) {
            rtnObject = publishService.dao().insert(publish);
        }else{
            rtnObject = publishService.dao().updateIgnoreNull(publish);
        }
        CacheManager.getInstance().getCache(PublishService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_publish", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  publishService.delete(id);
        CacheManager.getInstance().getCache(PublishService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_publish",false);
    }

}
