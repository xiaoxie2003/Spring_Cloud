package com.yc.resfood.service;

import java.util.ArrayList;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.ResUser;
import com.yc.resfood.dao.ResuserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private ResuserDao resuserDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<ResUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        ResUser resuser = resuserDao.selectOne(wrapper);
        if (resuser != null) {
            // spring security的User类                                权限
            return new User(resuser.getUsername(), resuser.getPwd(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
