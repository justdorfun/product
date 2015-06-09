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


import com.ly.base.vo.Target;
import com.ly.base.service.TargetService;


@IocBean
@At("/target")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class TargetAction {

	private static final Log log = Logs.getLog(TargetAction.class);
	
	@Inject
	private TargetService targetService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/target_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Target target,
                      HttpServletRequest request){

        Cnd c = new ParseObj(target).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(targetService.listCount(c));
            request.setAttribute("list_obj", targetService.queryCache(c,p));
        }else{
            p.setRecordCount(targetService.count(c));
            request.setAttribute("list_obj", targetService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("target", target);
    }

    @At
    @Ok("beetl:/WEB-INF/base/target.html")
    public void edit(@Param("action")int action,
                     @Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("target", null);
        }else{

            Target target = targetService.fetch(id);
            if (action == 3)
            {
                //target.setName(null);
            }
            request.setAttribute("target", target);
        }
        request.setAttribute("action", action);
    }

    @At
    @Ok("json")
    public Map<String,String> save(@Param("action")int action,
                                @Param("..")Target target){
        Object rtnObject;
        if (target.getId() == null || target.getId() == 0) {
            rtnObject = targetService.dao().insert(target);
        }else{
            if (action == 3) {
                target.setId(null);
                rtnObject = targetService.dao().insert(target);
            }else{
                rtnObject = targetService.dao().updateIgnoreNull(target);
            }
        }
        CacheManager.getInstance().getCache(TargetService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_target", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  targetService.delete(id);
        CacheManager.getInstance().getCache(TargetService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_target",false);
    }

}
