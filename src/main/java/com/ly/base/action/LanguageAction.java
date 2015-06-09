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


import com.ly.base.vo.Language;
import com.ly.base.service.LanguageService;


@IocBean
@At("/language")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class LanguageAction {

	private static final Log log = Logs.getLog(LanguageAction.class);
	
	@Inject
	private LanguageService languageService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/language_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Language language,
                      HttpServletRequest request){

        Cnd c = new ParseObj(language).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(languageService.listCount(c));
            request.setAttribute("list_obj", languageService.queryCache(c,p));
        }else{
            p.setRecordCount(languageService.count(c));
            request.setAttribute("list_obj", languageService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("language", language);
    }

    @At
    @Ok("beetl:/WEB-INF/base/language.html")
    public void edit(@Param("action")int action,
                     @Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("language", null);
        }else{

            Language language = languageService.fetch(id);
            if (action == 3)
            {
                //language.setName(null);
            }
            request.setAttribute("language", language);
        }
        request.setAttribute("action", action);
    }

    @At
    @Ok("json")
    public Map<String,String> save(@Param("action")int action,
                                @Param("..")Language language){
        Object rtnObject;
        if (language.getId() == null || language.getId() == 0) {
            rtnObject = languageService.dao().insert(language);
        }else{
            if (action == 3) {
                language.setId(null);
                rtnObject = languageService.dao().insert(language);
            }else{
                rtnObject = languageService.dao().updateIgnoreNull(language);
            }
        }
        CacheManager.getInstance().getCache(LanguageService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_language", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  languageService.delete(id);
        CacheManager.getInstance().getCache(LanguageService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_language",false);
    }

}
