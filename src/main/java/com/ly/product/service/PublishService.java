package  com.ly.product.service;

import com.ly.product.vo.Publish;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class PublishService extends IdEntityService<Publish> {

	public static String CACHE_NAME = "publish";
    public static String CACHE_COUNT_KEY = "publish_count";

    public List<Publish> queryCache(Cnd c,Page p)
    {
        List<Publish> list_publish = null;
        String cacheKey = "publish_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_publish = this.query(c, p);
            cache.put(new Element(cacheKey, list_publish));
        }else{
            list_publish = (List<Publish>)cache.get(cacheKey).getObjectValue();
        }
        return list_publish;
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


