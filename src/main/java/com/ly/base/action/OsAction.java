package com.ly.base.action;

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
        List<Os> list_m = osService.query(c, p);
        p.setRecordCount(osService.count(c));

        request.setAttribute("list_obj", list_m);
        request.setAttribute("page", p);
        request.setAttribute("os", os);
    }

    @At
    @Ok("beetl:/WEB-INF/base/os.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("os", null);
        }else{
            request.setAttribute("os", osService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Os os){
        Object rtnObject;
        if (os.getId() == null || os.getId() == 0) {
            rtnObject = osService.dao().insert(os);
        }else{
            rtnObject = osService.dao().updateIgnoreNull(os);
        }
        return Dwz.rtnMap((rtnObject == null) ? false : true, "os", "closeCurrent");
    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  osService.delete(id);
        return Dwz.rtnMap((num > 0) ? true : false , "os", "");
    }

}
