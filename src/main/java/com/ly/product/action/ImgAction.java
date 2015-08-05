package com.ly.product.action;

import com.ly.comm.Bjui;
import com.ly.comm.Page;
import com.ly.comm.ParseObj;
import com.ly.product.service.ProductService;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.CacheManager;


import com.ly.product.vo.Img;
import com.ly.product.service.ImgService;
import org.nutz.mvc.upload.UploadAdaptor;


@IocBean
@At("/img")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class ImgAction {

	private static final Log log = Logs.getLog(ImgAction.class);
	
	@Inject
	private ImgService imgService;

    @Inject
    private ProductService productService;


    @At("/")
    @Ok("beetl:/WEB-INF/product/img_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Img img,
                      HttpServletRequest request){

        Cnd c = new ParseObj(img).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(imgService.listCount(c));
            request.setAttribute("list_obj", imgService.queryCache(c,p));
        }else{
            p.setRecordCount(imgService.count(c));
            request.setAttribute("list_obj", imgService.query(c,p));
        }

        request.setAttribute("productList", productService.queryCache(null, new Page()));

        request.setAttribute("page", p);
        request.setAttribute("img", img);
    }

    @At
    @Ok("beetl:/WEB-INF/product/img.html")
    public void edit(@Param("action")int action,
                     @Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("img", null);
        }else{

            Img img = imgService.fetch(id);
            if (action == 3)
            {
                //img.setName(null);
            }
            request.setAttribute("img", img);
        }
        request.setAttribute("productList", productService.queryCache(null, new Page()));
        request.setAttribute("action", action);
    }

    @At
    @Ok("json")
    @AdaptBy(type = UploadAdaptor.class, args = { "ioc:uploadFile" })
    public Map<String,String> save(@Param("action")int action,
                                @Param("..")Img img,
                                   @Param("file1") File f1,
                                   HttpServletRequest request
    )throws IOException {
        Object rtnObject;

        String webPath =  request.getServletContext().getRealPath("/");
        String appPath = webPath.substring(0,webPath.length() - 1) + "upload/";

        if (f1 != null)
        {
            String oldImgName = img.getImgurl().trim();
            if (oldImgName.length() > 2)
            {
                Files.deleteFile(new File(appPath + oldImgName));
            }

            String fileName = System.currentTimeMillis()+f1.getName();
            Files.copyFile(f1, new File(appPath + fileName));
            img.setImgurl(fileName);
        }

        if (img.getId() == null || img.getId() == 0) {
            img.setAdddate(new Date());
            rtnObject = imgService.dao().insert(img);
        }else{
            img.setAdddate(new Date());
            if (action == 3) {
                img.setId(null);
                rtnObject = imgService.dao().insert(img);
            }else{
                rtnObject = imgService.dao().updateIgnoreNull(img);
            }
        }
        CacheManager.getInstance().getCache(ImgService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_img", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id,HttpServletRequest request)
    {

        Img img = imgService.fetch(id);
        if (img.getImgurl() != null && img.getImgurl().trim().length() > 2)
        {
            String webPath =  request.getServletContext().getRealPath("/");
            String appPath = webPath.substring(0,webPath.length() - 1) + "upload/";

            Files.deleteFile(new File(appPath + img.getImgurl().trim()));
        }


        int num =  imgService.delete(id);
        CacheManager.getInstance().getCache(ImgService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_img",false);
    }

}
