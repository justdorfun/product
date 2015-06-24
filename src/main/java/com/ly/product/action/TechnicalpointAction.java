package com.ly.product.action;

import com.ly.CBData;
import com.ly.base.service.OsService;
import com.ly.comm.Bjui;
import com.ly.comm.Page;
import com.ly.comm.ParseObj;
import com.ly.product.service.ProductService;
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


import com.ly.product.vo.Technicalpoint;
import com.ly.product.service.TechnicalpointService;


@IocBean
@At("/technicalpoint")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class TechnicalpointAction {

	private static final Log log = Logs.getLog(TechnicalpointAction.class);
	
	@Inject
	private TechnicalpointService technicalpointService;

    @Inject
    private OsService osService;

    @Inject
    private ProductService productService;

    @At("/")
    @Ok("beetl:/WEB-INF/product/technicalpoint_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Technicalpoint technicalpoint,
                      HttpServletRequest request){

        Cnd c = new ParseObj(technicalpoint).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(technicalpointService.listCount(c));
            request.setAttribute("list_obj", technicalpointService.queryCache(c,p));
        }else{
            p.setRecordCount(technicalpointService.count(c));
            request.setAttribute("list_obj", technicalpointService.query(c,p));
        }

        request.setAttribute("osList", osService.queryCache(null,new Page()));
        request.setAttribute("productList", productService.queryCache(null,new Page()));
        request.setAttribute("page", p);
        request.setAttribute("technicalpoint", technicalpoint);
    }

    @At
    @Ok("beetl:/WEB-INF/product/technicalpoint.html")
    public void edit(@Param("action")int action,
                     @Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("technicalpoint", null);
        }else{

            Technicalpoint technicalpoint = technicalpointService.fetch(id);
            if (action == 3)
            {
                //technicalpoint.setName(null);
            }
            request.setAttribute("technicalpoint", technicalpoint);
        }
        request.setAttribute("osList", osService.queryCache(null, new Page()));
        request.setAttribute("productList", productService.queryCache(null, new Page()));
        request.setAttribute("action", action);
    }

    @At
    @Ok("json")
    public Map<String,String> save(@Param("action")int action,
                                @Param("..")Technicalpoint technicalpoint){
        Object rtnObject;
        if (technicalpoint.getId() == null || technicalpoint.getId() == 0) {
            rtnObject = technicalpointService.dao().insert(technicalpoint);
        }else{
            if (action == 3) {
                technicalpoint.setId(null);
                rtnObject = technicalpointService.dao().insert(technicalpoint);
            }else{
                rtnObject = technicalpointService.dao().updateIgnoreNull(technicalpoint);
            }
        }
        CacheManager.getInstance().getCache(TechnicalpointService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_technicalpoint", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  technicalpointService.delete(id);
        CacheManager.getInstance().getCache(TechnicalpointService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_technicalpoint",false);
    }

}
