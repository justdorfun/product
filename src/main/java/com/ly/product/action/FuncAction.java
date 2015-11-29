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


import com.ly.product.vo.Func;
import com.ly.product.service.FuncService;


@IocBean
@At("/func")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class FuncAction {

	private static final Log log = Logs.getLog(FuncAction.class);
	
	@Inject
	private FuncService funcService;

    @At("/")
    @Ok("beetl:/WEB-INF/product/func_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Func func,
                      @Param("productid")long productid,
                      HttpServletRequest request){

        Cnd c = new ParseObj(func).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(funcService.listCount(c));
            request.setAttribute("list_obj", funcService.queryCache(c,p));
        }else{
            p.setRecordCount(funcService.count(c));
            request.setAttribute("list_obj", funcService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("func", func);
        request.setAttribute("productid",productid);
    }

    @At
    @Ok("beetl:/WEB-INF/product/func.html")
    public void edit(@Param("id")Long id,
                     @Param("productid")long productid,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("func", null);
        }else{
            request.setAttribute("func", funcService.fetch(id));
        }
        request.setAttribute("productid",productid);
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Func func){
        Object rtnObject;
        if (func.getId() == null || func.getId() == 0) {
            rtnObject = funcService.dao().insert(func);
        }else{
            rtnObject = funcService.dao().updateIgnoreNull(func);
        }
        CacheManager.getInstance().getCache(FuncService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_func", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  funcService.delete(id);
        CacheManager.getInstance().getCache(FuncService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_func",false);
    }

}
