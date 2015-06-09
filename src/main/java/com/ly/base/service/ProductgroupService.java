package  com.ly.base.service;

import com.ly.base.vo.Productgroup;
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
public class ProductgroupService extends IdEntityService<Productgroup> {

	public static String CACHE_NAME = "productgroup";
    public static String CACHE_COUNT_KEY = "productgroup_count";

    public List<Productgroup> queryCache(Condition c,Page p)
    {
        List<Productgroup> list_productgroup = null;
        String cacheKey = "productgroup_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_productgroup = this.query(c, p);
            cache.put(new Element(cacheKey, list_productgroup));
        }else{
            list_productgroup = (List<Productgroup>)cache.get(cacheKey).getObjectValue();
        }
        return list_productgroup;
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


