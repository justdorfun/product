package com.ly.pm.action;

import com.ly.comm.Dwz;
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

import com.ly.pm.vo.Img;
import com.ly.pm.service.ImgService;


@IocBean
@At("/img")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class ImgAction {

	private static final Log log = Logs.getLog(ImgAction.class);
	
	@Inject
	private ImgService imgService;

    @At("/")
    @Ok("beetl:/WEB-INF/pm/img_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Img img,
                      HttpServletRequest request){
        Cnd c = new ParseObj(img).getCnd();
        List<Img> list_m = imgService.query(c, p);
        p.setRecordCount(imgService.count(c));

        request.setAttribute("list_obj", list_m);
        request.setAttribute("page", p);
        request.setAttribute("img", img);
    }

    @At
    @Ok("beetl:/WEB-INF/pm/img.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("img", null);
        }else{
            request.setAttribute("img", imgService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Img img){
        Object rtnObject;
        if (img.getId() == null || img.getId() == 0) {
            rtnObject = imgService.dao().insert(img);
        }else{
            rtnObject = imgService.dao().updateIgnoreNull(img);
        }
        return Dwz.rtnMap((rtnObject == null) ? false : true, "img", "closeCurrent");
    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  imgService.delete(id);
        return Dwz.rtnMap((num > 0) ? true : false , "img", "");
    }

}
