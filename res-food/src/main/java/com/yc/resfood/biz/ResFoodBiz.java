package com.yc.resfood.biz;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.ResFood;

import java.util.List;

/**
 * 商品业务类
 */
public interface ResFoodBiz{
    /**
     * 查看所有商品
     * @return
     */
    public List<ResFood> FindAll();

    /**
     * 通过id号查找商品
     * @return
     */
    public ResFood FindById(Integer id);

    /**
     * 通过商品名查找商品
     * @return
     */
    public List<ResFood> FindByName(String name);

    /**
     * 分页查找
     * @return
     */
    public Page<ResFood> FindByPage(int pageno, int pagesize, String sortby, String sort);
}
