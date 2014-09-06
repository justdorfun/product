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

import com.ly.pm.vo.Project;
import com.ly.pm.service.ProjectService;


@IocBean
@At("/project")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class ProjectAction {

	private static final Log log = Logs.getLog(ProjectAction.class);
	
	@Inject
	private ProjectService projectService;

    @At("/")
    @Ok("beetl:/WEB-INF/pm/project_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Project project,
                      HttpServletRequest request){
        Cnd c = new ParseObj(project).getCnd();
        List<Project> list_m = projectService.query(c, p);
        p.setRecordCount(projectService.count(c));

        request.setAttribute("list_obj", list_m);
        request.setAttribute("page", p);
        request.setAttribute("project", project);
    }

    @At
    @Ok("beetl:/WEB-INF/pm/project.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("project", null);
        }else{
            request.setAttribute("project", projectService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Project project){
        Object rtnObject;
        if (project.getId() == null || project.getId() == 0) {
            rtnObject = projectService.dao().insert(project);
        }else{
            rtnObject = projectService.dao().updateIgnoreNull(project);
        }
        return Dwz.rtnMap((rtnObject == null) ? false : true, "project", "closeCurrent");
    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  projectService.delete(id);
        return Dwz.rtnMap((num > 0) ? true : false , "project", "");
    }

}
