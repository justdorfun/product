package  com.ly.base.service;

import com.ly.base.vo.Coode;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;

import java.util.List;


@IocBean(fields = { "dao" })
public class CoodeService extends IdEntityService<Coode> {
}


