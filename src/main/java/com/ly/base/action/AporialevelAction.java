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


import com.ly.base.vo.Aporialevel;
import com.ly.base.service.AporialevelService;


@IocBean
@At("/aporialevel")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class AporialevelAction {

	private static final Log log = Logs.getLog(AporialevelAction.class);
	
	@Inject
	private AporialevelService aporialevelService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/aporialevel_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Aporialevel aporialevel,
                      HttpServletRequest request){

        Cnd c = new ParseObj(aporialevel).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(aporialevelService.listCount(c));
            request.setAttribute("list_obj", aporialevelService.queryCache(c,p));
        }else{
            p.setRecordCount(aporialevelService.count(c));
            request.setAttribute("list_obj", aporialevelService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("aporialevel", aporialevel);
    }

    @At
    @Ok("beetl:/WEB-INF/base/aporialevel.html")
    public void edit(@Param("action")int action,
                     @Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("aporialevel", null);
        }else{

            Aporialevel aporialevel = aporialevelService.fetch(id);
            if (action == 3)
            {
                //aporialevel.setName(null);
            }
            request.setAttribute("aporialevel", aporialevel);
        }
        request.setAttribute("action", action);
    }

    @At
    @Ok("json")
    public Map<String,String> save(@Param("action")int action,
                                @Param("..")Aporialevel aporialevel){
        Object rtnObject;
        if (aporialevel.getId() == null || aporialevel.getId() == 0) {
            rtnObject = aporialevelService.dao().insert(aporialevel);
        }else{
            if (action == 3) {
                aporialevel.setId(null);
                rtnObject = aporialevelService.dao().insert(aporialevel);
            }else{
                rtnObject = aporialevelService.dao().updateIgnoreNull(aporialevel);
            }
        }
        CacheManager.getInstance().getCache(AporialevelService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_aporialevel", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  aporialevelService.delete(id);
        CacheManager.getInstance().getCache(AporialevelService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_aporialevel",false);
    }

}
