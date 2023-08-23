package com.yc.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.ResFood;
import com.yc.dao.ResFoodBizMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//Propagation.SUPPORTS 表示支持事务环境 即事务可有可无  readOnly = true只读事务
@Transactional( propagation = Propagation.SUPPORTS,isolation = Isolation.DEFAULT,
                timeout = 2000,readOnly = true,rollbackFor = RuntimeException.class)//事务
@Slf4j
public class ResFoodBizImpl implements ResFoodBiz{

    //注入dao包 继承mybatis-plus
    @Autowired
    private ResFoodBizMapper mapper;

    @Override
    public List<ResFood> FindAll() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderByDesc("fid"); //根据fid列的值降序排列
        return this.mapper.selectList(wrapper);
    }

    @Override
    public ResFood FindById(Integer id) {
        return this.mapper.selectById(id);
    }

    @Override
    public List<ResFood> FindByName(String name) {
        ResFood resFood = new ResFood();
        resFood.setFname(name);
        QueryWrapper<ResFood> wrapper = new QueryWrapper<>();
        wrapper.like("fname",name);
        List<ResFood> lists = mapper.selectList(wrapper);

        return lists;
    }

    /**
     * 分页
     * @param pageno
     * @param pagesize
     * @param sortby
     * @param sort
     * @return
     */
    @Override
    public Page<ResFood> FindByPage(int pageno,int pagesize,String sortby,String sort) {
        QueryWrapper wrapper = new QueryWrapper();
        if("asc".equalsIgnoreCase(sort)){
            wrapper.orderByAsc(sortby);
        }else {
            wrapper.orderByDesc(sortby);
        }
        //此处的Page是mybatis-plus提供的分页组件 拼接sql的分页语句
        //因为不同的数据库使用的语句不一样
        Page<ResFood> page = new Page<>(pageno,pagesize);
        //执行分页查询 可以采用mp提供的selectPage（）来完成分页查找 通过pageno,pagesize就知道如何分页
        Page<ResFood> resFoodPage = this.mapper.selectPage(page,wrapper);
        log.info("总记录数=" + resFoodPage.getTotal());
        log.info("总页数==" + resFoodPage.getPages());
        log.info("当前页码==" + resFoodPage.getCurrent());
        return resFoodPage;
    }
}
