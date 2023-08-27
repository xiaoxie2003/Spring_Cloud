package com.yc.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.ResFood;
import com.yc.biz.ResFoodBiz;
import com.yc.web.model.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("resfood") //http:localhost:port/resfood
@Slf4j
@Api(tags = "菜品管理")  //swagger网页监控
public class ResfoodController {

    @Autowired
    private ResFoodBiz resFoodBiz;

    @GetMapping("findById/{fid}")
    @ApiOperation(value = "根据菜品编号查询操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid",value = "菜品号",required = true)
    })
    public Map<String, Object> findById(@PathVariable Integer fid){

//        try {
//            Thread.sleep(20000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        Map<String,Object> map = new HashMap<>();
        ResFood rf = null;
        try {
            rf = this.resFoodBiz.FindById(fid);
        }catch (Exception ex){
            ex.printStackTrace();
            map.put("code",0);
            map.put("msg",ex.getCause());
            return map;
        }
        map.put("code",1);
        map.put("obj",rf);
        return map;
    }

//    private Map<String,Object>handleBlock(@RequestParam int pageno, @RequestParam int pagesize,
//                                          @RequestParam String sortby, @RequestParam String sort, BlockException exception){
//        exception.printStackTrace();
//        Map<String,Object> map = new HashMap<>();
//        map.put("code",0);
//        map.put("msg",exception.getRuleLimitApp() + ":被限流，规则为：" + exception.getRule().toString());
//        return map; //sentinel 200
//    }

    @RequestMapping(value = "findByPage",method = {RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value = "分页查询操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageno",value = "页号",required = true),
            @ApiImplicitParam(name = "pagesize",value = "每页记录数",required = true),
            @ApiImplicitParam(name = "sortby",value = "排序列",required = true),
            @ApiImplicitParam(name = "sort",value = "排序方式",required = true),
    })
    //@SentinelResource(value = "hotkey-page",blockHandler = "handleBlock") //流控资源名
    public Map<String, Object> findByPage(@RequestParam int pageno,@RequestParam int pagesize,
                                          @RequestParam String sortby,@RequestParam String sort){
        Map<String,Object> map = new HashMap<>();
        Page<ResFood> page = null;
        try {
            page = this.resFoodBiz.FindByPage(pageno,pagesize,sortby,sort);
        }catch (Exception ex){
            ex.printStackTrace();
            map.put("code",0);
            map.put("msg",ex.getCause());
            return map;
        }
        map.put("code",1);
        PageBean pageBean = new PageBean();
        pageBean.setPageno(pageno);
        pageBean.setPagesize(pagesize);
        pageBean.setSort(sort);
        pageBean.setSortby(sortby);
        pageBean.setTotal(page.getTotal());
        pageBean.setDataset(page.getRecords());
        //其他的分页数据
        //计算总页数
        long totalPages = page.getTotal()%pageBean.getPagesize()==0?page.getTotal()/pageBean.getPagesize()
                :page.getTotal()/pageBean.getPagesize()+1;
        pageBean.setTotalpages((int) totalPages);
        //上一页页号的计算
        if(pageBean.getPageno() <= 1){
            pageBean.setPre(1);
        }else {
            pageBean.setPre(pageBean.getPageno() - 1);
        }
        //下一页的页号
        if(pageBean.getPageno() == totalPages){
            pageBean.setNext((int) totalPages);
        }else {
            pageBean.setNext(pageBean.getPageno() + 1);
        }
        map.put("data",pageBean);
        return map;
    }

    //set集合 存线程
    public Set<Thread> set = new HashSet<>();

//    private Map<String,Object>exceptionFallback(Throwable t){
//        t.printStackTrace();
//        Map<String,Object> map = new HashMap<>();
//        map.put("code",0);
//        map.put("msg","resource is under Exception cause as following：" + t.getCause());
//        return map; //sentinel 200
//    }

    @GetMapping("findAll")
    @ApiOperation(value = "查询所有菜品")
    //@SentinelResource(value = "findAll",fallback = "exceptionFallback")
    public Map<String, Object> findAll(){
//        int flag = 1;
//        if(flag == 1){
//            throw new RuntimeException("业务异常：查找出异常了...."); //springboot处理
//        }

//        Thread thread = Thread.currentThread(); //取出当前线程
//        set.add(thread);
//        log.info("线程数为：" + set.size() + ",当前的线程编号为：" + thread.getId());
//
//        try {
//            Thread.sleep(20000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        Map<String,Object> map = new HashMap<>();
        List<ResFood> list = null;
        try {
            list = this.resFoodBiz.FindAll();
        }catch (Exception ex){
            ex.printStackTrace();
            map.put("code",0);
            map.put("msg",ex.getCause());
            return map;
        }
        map.put("code",1);
        map.put("obj",list);
        return map;
    }
}
