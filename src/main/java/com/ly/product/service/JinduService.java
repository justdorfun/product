package  com.ly.product.service;

import com.ly.product.vo.Jindu;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class JinduService extends IdEntityService<Jindu> {

	public static String CACHE_NAME = "jindu";
    public static String CACHE_COUNT_KEY = "jindu_count";

    public List<Jindu> queryCache(Cnd c,Page p)
    {
        List<Jindu> list_jindu = null;
        String cacheKey = "jindu_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_jindu = this.query(c, p);
            cache.put(new Element(cacheKey, list_jindu));
        }else{
            list_jindu = (List<Jindu>)cache.get(cacheKey).getObjectValue();
        }
        return list_jindu;
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


