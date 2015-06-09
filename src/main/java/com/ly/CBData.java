package com.ly;

import com.ly.comm.AppContext;
import com.ly.comm.Page;
import org.nutz.dao.Cnd;

import java.util.List;

public class CBData {

    private static CBData uniqueInstance = null;

//    private List<Os> osList;



    private CBData() {
    }

    public static CBData getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new CBData();
        }
        return uniqueInstance;
    }

    public void init()
    {
        Page p = new Page();

//        setOsList(AppContext.ioc.get(OsService.class).queryCache(null, p));


//        setProjectList(AppContext.ioc.get(ProjectService.class).queryCache(Cnd.NEW().desc("id"),p));


    }

}
