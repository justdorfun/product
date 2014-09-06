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

import com.ly.base.vo.Coode;
import com.ly.base.service.CoodeService;


@IocBean
@At("/coode")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class CoodeAction {

	private static final Log log = Logs.getLog(CoodeAction.class);
	
	@Inject
	private CoodeService coodeService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/coode_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Coode coode,
                      HttpServletRequest request){
        Cnd c = new ParseObj(coode).getCnd();
        List<Coode> list_m = coodeService.query(c, p);
        p.setRecordCount(coodeService.count(c));

        request.setAttribute("list_obj", list_m);
        request.setAttribute("page", p);
        request.setAttribute("coode", coode);
    }

    @At
    @Ok("beetl:/WEB-INF/base/coode.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("coode", null);
        }else{
            request.setAttribute("coode", coodeService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Coode coode){
        Object rtnObject;
        if (coode.getId() == null || coode.getId() == 0) {
            rtnObject = coodeService.dao().insert(coode);
        }else{
            rtnObject = coodeService.dao().updateIgnoreNull(coode);
        }
        return Dwz.rtnMap((rtnObject == null) ? false : true, "coode", "closeCurrent");
    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  coodeService.delete(id);
        return Dwz.rtnMap((num > 0) ? true : false , "coode", "");
    }

}
