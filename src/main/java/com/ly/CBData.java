package com.ly;

import com.ly.base.service.*;
import com.ly.base.vo.*;
import com.ly.comm.AppContext;
import com.ly.comm.Page;
import com.ly.product.service.ProductService;
import com.ly.product.service.TechnicalpointService;
import com.ly.product.vo.Product;
import com.ly.product.vo.Technicalpoint;
import net.sf.ehcache.CacheManager;

import java.util.List;

public class CBData {

    private static CBData uniqueInstance = null;

    private List<Os> osList;

    private List<Studio> studioList;

    private List<Language> languageList;

    private List<Productlevel> productlevelList;

    private List<Aporialevel> aporialevelList;

    private List<Technicalpoint> technicalpointList;

    private List<Productgroup> productgroupList;

    private List<Product> productList;


    private CBData() {
    }

    public static CBData getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new CBData();
        }
        return uniqueInstance;
    }

    public void init() {
        Page p = new Page();

        initOsList();

        setStudioList(AppContext.ioc.get(StudioService.class).queryCache(null, p));

        setLanguageList(AppContext.ioc.get(LanguageService.class).queryCache(null, p));

        setProductlevelList(AppContext.ioc.get(ProductlevelService.class).queryCache(null, p));

        setAporialevelList(AppContext.ioc.get(AporialevelService.class).queryCache(null, p));

        setTechnicalpointList(AppContext.ioc.get(TechnicalpointService.class).queryCache(null, p));

        setProductgroupList(AppContext.ioc.get(ProductgroupService.class).queryCache(null, p));

        setProductList(AppContext.ioc.get(ProductService.class).queryCache(null, p));


    }

    public void initOsList()
    {
        CacheManager.getInstance().getCache(OsService.CACHE_NAME).removeAll();
        setOsList(AppContext.ioc.get(OsService.class).queryCache(null, new Page()));
    }

    public List<Os> getOsList() {
        return osList;
    }

    public void setOsList(List<Os> osList) {
        this.osList = osList;
    }

    public List<Studio> getStudioList() {
        return studioList;
    }

    public void setStudioList(List<Studio> studioList) {
        this.studioList = studioList;
    }

    public List<Language> getLanguageList() {
        return languageList;
    }

    public void setLanguageList(List<Language> languageList) {
        this.languageList = languageList;
    }

    public List<Productlevel> getProductlevelList() {
        return productlevelList;
    }

    public void setProductlevelList(List<Productlevel> productlevelList) {
        this.productlevelList = productlevelList;
    }

    public List<Aporialevel> getAporialevelList() {
        return aporialevelList;
    }

    public void setAporialevelList(List<Aporialevel> aporialevelList) {
        this.aporialevelList = aporialevelList;
    }

    public List<Technicalpoint> getTechnicalpointList() {
        return technicalpointList;
    }

    public void setTechnicalpointList(List<Technicalpoint> technicalpointList) {
        this.technicalpointList = technicalpointList;
    }

    public List<Productgroup> getProductgroupList() {
        return productgroupList;
    }

    public void setProductgroupList(List<Productgroup> productgroupList) {
        this.productgroupList = productgroupList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
