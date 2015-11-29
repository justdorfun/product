package  com.ly.product.service;

import com.ly.product.vo.Func;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class FuncService extends IdEntityService<Func> {

	public static String CACHE_NAME = "func";
    public static String CACHE_COUNT_KEY = "func_count";

    public List<Func> queryCache(Cnd c,Page p)
    {
        List<Func> list_func = null;
        String cacheKey = "func_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_func = this.query(c, p);
            cache.put(new Element(cacheKey, list_func));
        }else{
            list_func = (List<Func>)cache.get(cacheKey).getObjectValue();
        }
        return list_func;
    }

    public int listCount(Cnd c)
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


