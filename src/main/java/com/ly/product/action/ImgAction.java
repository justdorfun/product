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


import com.ly.product.vo.Img;
import com.ly.product.service.ImgService;


@IocBean
@At("/img")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class ImgAction {

	private static final Log log = Logs.getLog(ImgAction.class);
	
	@Inject
	private ImgService imgService;

    @At("/")
    @Ok("beetl:/WEB-INF/product/img_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Img img,
                      HttpServletRequest request){

        Cnd c = new ParseObj(img).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(imgService.listCount(c));
            request.setAttribute("list_obj", imgService.queryCache(c,p));
        }else{
            p.setRecordCount(imgService.count(c));
            request.setAttribute("list_obj", imgService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("img", img);
    }

    @At
    @Ok("beetl:/WEB-INF/product/img.html")
    public void edit(@Param("action")int action,
                     @Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("img", null);
        }else{

            Img img = imgService.fetch(id);
            if (action == 3)
            {
                //img.setName(null);
            }
            request.setAttribute("img", img);
        }
        request.setAttribute("action", action);
    }

    @At
    @Ok("json")
    public Map<String,String> save(@Param("action")int action,
                                @Param("..")Img img){
        Object rtnObject;
        if (img.getId() == null || img.getId() == 0) {
            rtnObject = imgService.dao().insert(img);
        }else{
            if (action == 3) {
                img.setId(null);
                rtnObject = imgService.dao().insert(img);
            }else{
                rtnObject = imgService.dao().updateIgnoreNull(img);
            }
        }
        CacheManager.getInstance().getCache(ImgService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_img", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  imgService.delete(id);
        CacheManager.getInstance().getCache(ImgService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_img",false);
    }

}
