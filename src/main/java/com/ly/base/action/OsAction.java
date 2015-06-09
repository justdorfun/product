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


import com.ly.base.vo.Os;
import com.ly.base.service.OsService;


@IocBean
@At("/os")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class OsAction {

	private static final Log log = Logs.getLog(OsAction.class);
	
	@Inject
	private OsService osService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/os_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Os os,
                      HttpServletRequest request){

        Cnd c = new ParseObj(os).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(osService.listCount(c));
            request.setAttribute("list_obj", osService.queryCache(c,p));
        }else{
            p.setRecordCount(osService.count(c));
            request.setAttribute("list_obj", osService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("os", os);
    }

    @At
    @Ok("beetl:/WEB-INF/base/os.html")
    public void edit(@Param("action")int action,
                     @Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("os", null);
        }else{

            Os os = osService.fetch(id);
            if (action == 3)
            {
                //os.setName(null);
            }
            request.setAttribute("os", os);
        }
        request.setAttribute("action", action);
    }

    @At
    @Ok("json")
    public Map<String,String> save(@Param("action")int action,
                                @Param("..")Os os){
        Object rtnObject;
        if (os.getId() == null || os.getId() == 0) {
            rtnObject = osService.dao().insert(os);
        }else{
            if (action == 3) {
                os.setId(null);
                rtnObject = osService.dao().insert(os);
            }else{
                rtnObject = osService.dao().updateIgnoreNull(os);
            }
        }
        CacheManager.getInstance().getCache(OsService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_os", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  osService.delete(id);
        CacheManager.getInstance().getCache(OsService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_os",false);
    }

}
