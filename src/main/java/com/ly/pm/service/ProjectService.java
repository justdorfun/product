package  com.ly.pm.service;

import com.ly.pm.vo.Project;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;

import java.util.List;


@IocBean(fields = { "dao" })
public class ProjectService extends IdEntityService<Project> {
}


