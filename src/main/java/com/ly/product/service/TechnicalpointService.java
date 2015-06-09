package  com.ly.product.service;

import com.ly.product.vo.Technicalpoint;
import org.nutz.dao.Condition;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class TechnicalpointService extends IdEntityService<Technicalpoint> {

	public static String CACHE_NAME = "technicalpoint";
    public static String CACHE_COUNT_KEY = "technicalpoint_count";

    public List<Technicalpoint> queryCache(Condition c,Page p)
    {
        List<Technicalpoint> list_technicalpoint = null;
        String cacheKey = "technicalpoint_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_technicalpoint = this.query(c, p);
            cache.put(new Element(cacheKey, list_technicalpoint));
        }else{
            list_technicalpoint = (List<Technicalpoint>)cache.get(cacheKey).getObjectValue();
        }
        return list_technicalpoint;
    }

    public int listCount(Condition c)
    {
        Long num = 0L;
        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(CACHE_COUNT_KEY) == null)
        {
            num = Long.valueOf(this.count(c));
            cache.put(new Element(CACHE_COUNT_KEY, num));
        }else{
            num = (Long)cache.get(CACHE_COUNT_KEY).getObjectValue();
        }
        return num.intValue();
    }



}


