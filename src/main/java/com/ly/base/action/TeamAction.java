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

import com.ly.base.vo.Team;
import com.ly.base.service.TeamService;


@IocBean
@At("/team")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class TeamAction {

	private static final Log log = Logs.getLog(TeamAction.class);
	
	@Inject
	private TeamService teamService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/team_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Team team,
                      HttpServletRequest request){
        Cnd c = new ParseObj(team).getCnd();
        List<Team> list_m = teamService.query(c, p);
        p.setRecordCount(teamService.count(c));

        request.setAttribute("list_obj", list_m);
        request.setAttribute("page", p);
        request.setAttribute("team", team);
    }

    @At
    @Ok("beetl:/WEB-INF/base/team.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("team", null);
        }else{
            request.setAttribute("team", teamService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Team team){
        Object rtnObject;
        if (team.getId() == null || team.getId() == 0) {
            rtnObject = teamService.dao().insert(team);
        }else{
            rtnObject = teamService.dao().updateIgnoreNull(team);
        }
        return Dwz.rtnMap((rtnObject == null) ? false : true, "team", "closeCurrent");
    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  teamService.delete(id);
        return Dwz.rtnMap((num > 0) ? true : false , "team", "");
    }

}
