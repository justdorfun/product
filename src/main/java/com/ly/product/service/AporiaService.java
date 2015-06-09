package  com.ly.product.service;

import com.ly.product.vo.Aporia;
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
public class AporiaService extends IdEntityService<Aporia> {

	public static String CACHE_NAME = "aporia";
    public static String CACHE_COUNT_KEY = "aporia_count";

    public List<Aporia> queryCache(Condition c,Page p)
    {
        List<Aporia> list_aporia = null;
        String cacheKey = "aporia_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_aporia = this.query(c, p);
            cache.put(new Element(cacheKey, list_aporia));
        }else{
            list_aporia = (List<Aporia>)cache.get(cacheKey).getObjectValue();
        }
        return list_aporia;
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


