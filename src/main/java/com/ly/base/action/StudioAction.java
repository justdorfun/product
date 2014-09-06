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

import com.ly.base.vo.Studio;
import com.ly.base.service.StudioService;


@IocBean
@At("/studio")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class StudioAction {

	private static final Log log = Logs.getLog(StudioAction.class);
	
	@Inject
	private StudioService studioService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/studio_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Studio studio,
                      HttpServletRequest request){
        Cnd c = new ParseObj(studio).getCnd();
        List<Studio> list_m = studioService.query(c, p);
        p.setRecordCount(studioService.count(c));

        request.setAttribute("list_obj", list_m);
        request.setAttribute("page", p);
        request.setAttribute("studio", studio);
    }

    @At
    @Ok("beetl:/WEB-INF/base/studio.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("studio", null);
        }else{
            request.setAttribute("studio", studioService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Studio studio){
        Object rtnObject;
        if (studio.getId() == null || studio.getId() == 0) {
            rtnObject = studioService.dao().insert(studio);
        }else{
            rtnObject = studioService.dao().updateIgnoreNull(studio);
        }
        return Dwz.rtnMap((rtnObject == null) ? false : true, "studio", "closeCurrent");
    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  studioService.delete(id);
        return Dwz.rtnMap((num > 0) ? true : false , "studio", "");
    }

}
