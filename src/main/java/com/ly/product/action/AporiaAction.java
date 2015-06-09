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


import com.ly.product.vo.Aporia;
import com.ly.product.service.AporiaService;


@IocBean
@At("/aporia")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class AporiaAction {

	private static final Log log = Logs.getLog(AporiaAction.class);
	
	@Inject
	private AporiaService aporiaService;

    @At("/")
    @Ok("beetl:/WEB-INF/product/aporia_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Aporia aporia,
                      HttpServletRequest request){

        Cnd c = new ParseObj(aporia).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(aporiaService.listCount(c));
            request.setAttribute("list_obj", aporiaService.queryCache(c,p));
        }else{
            p.setRecordCount(aporiaService.count(c));
            request.setAttribute("list_obj", aporiaService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("aporia", aporia);
    }

    @At
    @Ok("beetl:/WEB-INF/product/aporia.html")
    public void edit(@Param("action")int action,
                     @Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("aporia", null);
        }else{

            Aporia aporia = aporiaService.fetch(id);
            if (action == 3)
            {
                //aporia.setName(null);
            }
            request.setAttribute("aporia", aporia);
        }
        request.setAttribute("action", action);
    }

    @At
    @Ok("json")
    public Map<String,String> save(@Param("action")int action,
                                @Param("..")Aporia aporia){
        Object rtnObject;
        if (aporia.getId() == null || aporia.getId() == 0) {
            rtnObject = aporiaService.dao().insert(aporia);
        }else{
            if (action == 3) {
                aporia.setId(null);
                rtnObject = aporiaService.dao().insert(aporia);
            }else{
                rtnObject = aporiaService.dao().updateIgnoreNull(aporia);
            }
        }
        CacheManager.getInstance().getCache(AporiaService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_aporia", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  aporiaService.delete(id);
        CacheManager.getInstance().getCache(AporiaService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_aporia",false);
    }

}
