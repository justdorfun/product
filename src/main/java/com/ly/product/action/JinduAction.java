package com.ly.product.action;

import com.ly.base.service.OsService;
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


import com.ly.product.vo.Jindu;
import com.ly.product.service.JinduService;


@IocBean
@At("/jindu")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class JinduAction {

	private static final Log log = Logs.getLog(JinduAction.class);
	
	@Inject
	private JinduService jinduService;

    @Inject
    private OsService osService;


    @At("/")
    @Ok("beetl:/WEB-INF/product/jindu_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Jindu jindu,
                      @Param("productid")long productid,
                      HttpServletRequest request){

        Cnd c = new ParseObj(jindu).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(jinduService.listCount(c));
            request.setAttribute("list_obj", jinduService.queryCache(c,p));
        }else{
            p.setRecordCount(jinduService.count(c));
            request.setAttribute("list_obj", jinduService.query(c,p));
        }
        request.setAttribute("osList", osService.queryCache(null, new Page()));

        request.setAttribute("page", p);
        request.setAttribute("jindu", jindu);
        request.setAttribute("productid",productid);

    }

    @At
    @Ok("beetl:/WEB-INF/product/jindu.html")
    public void edit(@Param("id")Long id,
                     @Param("productid")long productid,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("jindu", null);
        }else{
            request.setAttribute("jindu", jinduService.fetch(id));
        }
        request.setAttribute("productid",productid);
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Jindu jindu){
        Object rtnObject;
        if (jindu.getId() == null || jindu.getId() == 0) {
            rtnObject = jinduService.dao().insert(jindu);
        }else{
            rtnObject = jinduService.dao().updateIgnoreNull(jindu);
        }
        CacheManager.getInstance().getCache(JinduService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_jindu", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  jinduService.delete(id);
        CacheManager.getInstance().getCache(JinduService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_jindu",false);
    }

}
