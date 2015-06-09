package  com.ly.base.service;

import com.ly.base.vo.Aporialevel;
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
public class AporialevelService extends IdEntityService<Aporialevel> {

	public static String CACHE_NAME = "aporialevel";
    public static String CACHE_COUNT_KEY = "aporialevel_count";

    public List<Aporialevel> queryCache(Condition c,Page p)
    {
        List<Aporialevel> list_aporialevel = null;
        String cacheKey = "aporialevel_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_aporialevel = this.query(c, p);
            cache.put(new Element(cacheKey, list_aporialevel));
        }else{
            list_aporialevel = (List<Aporialevel>)cache.get(cacheKey).getObjectValue();
        }
        return list_aporialevel;
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


