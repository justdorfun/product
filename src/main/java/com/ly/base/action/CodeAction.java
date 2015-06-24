package com.ly.base.action;

import com.ly.CBData;
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


import com.ly.base.vo.Code;
import com.ly.base.service.CodeService;


@IocBean
@At("/code")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class CodeAction {

	private static final Log log = Logs.getLog(CodeAction.class);
	
	@Inject
	private CodeService codeService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/code_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Code code,
                      HttpServletRequest request){

        Cnd c = new ParseObj(code).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(codeService.listCount(c));
            request.setAttribute("list_obj", codeService.queryCache(c,p));
        }else{
            p.setRecordCount(codeService.count(c));
            request.setAttribute("list_obj", codeService.query(c,p));
        }

        request.setAttribute("osList", CBData.getInstance().getOsList());

        request.setAttribute("page", p);
        request.setAttribute("code", code);
    }

    @At
    @Ok("beetl:/WEB-INF/base/code.html")
    public void edit(@Param("action")int action,
                     @Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("code", null);
        }else{

            Code code = codeService.fetch(id);
            if (action == 3)
            {
                //code.setName(null);
            }
            request.setAttribute("code", code);
        }
        request.setAttribute("osList", CBData.getInstance().getOsList());
        request.setAttribute("action", action);
    }

    @At
    @Ok("json")
    public Map<String,String> save(@Param("action")int action,
                                @Param("..")Code code){
        Object rtnObject;
        if (code.getId() == null || code.getId() == 0) {
            rtnObject = codeService.dao().insert(code);
        }else{
            if (action == 3) {
                code.setId(null);
                rtnObject = codeService.dao().insert(code);
            }else{
                rtnObject = codeService.dao().updateIgnoreNull(code);
            }
        }
        CacheManager.getInstance().getCache(CodeService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_code", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  codeService.delete(id);
        CacheManager.getInstance().getCache(CodeService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_code",false);
    }

}
