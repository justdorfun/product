package  com.ly.base.service;

import com.ly.base.vo.Productlevel;
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
public class ProductlevelService extends IdEntityService<Productlevel> {

	public static String CACHE_NAME = "productlevel";
    public static String CACHE_COUNT_KEY = "productlevel_count";

    public List<Productlevel> queryCache(Condition c,Page p)
    {
        List<Productlevel> list_productlevel = null;
        String cacheKey = "productlevel_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_productlevel = this.query(c, p);
            cache.put(new Element(cacheKey, list_productlevel));
        }else{
            list_productlevel = (List<Productlevel>)cache.get(cacheKey).getObjectValue();
        }
        return list_productlevel;
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


