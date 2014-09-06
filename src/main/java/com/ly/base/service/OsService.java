package  com.ly.base.service;

import com.ly.base.vo.Os;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;

import java.util.List;


@IocBean(fields = { "dao" })
public class OsService extends IdEntityService<Os> {
}


