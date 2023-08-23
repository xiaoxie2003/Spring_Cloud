package com.yc.resfood.config;

import com.yc.resfood.controller.JwtAuthenticationEntryPoint;
import com.yc.resfood.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 这个类扩展了WebSecurityConfigurerAdapter
 * 它允许对WebSecurityHttpSecurity进行自定义
 */
@Configuration
@EnableWebSecurity //web安全配置
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint; // 出错了的处理类
    @Autowired
    private UserDetailsService jwtUserDetailsService; // 业务类
    @Autowired
    private JwtRequestFilter jwtRequestFilter; // 过滤类

    //@Bean
    // 加密算法类
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     *验证管理器
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        // 配置AuthenticationManager，使其知道从何处（jwtUserDetailsService）加载匹配凭据的用户
        // 在匹配时使用BCryptPasswordEncoder
        // auth类组装 jwtUserDetailsService（它提供一个根据用户名加载用户信息）
        //组装加密算法 => AuthenticationManager
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 关闭csrf跨站请求伪造
        httpSecurity.csrf().disable()
                // 不验证此特定请求
                .authorizeRequests().antMatchers("/resfood/code.action","/resfood/resuser.action","/swagger-ui/**").permitAll().
                // 所有其他请求都需要经过身份验证
                        anyRequest().authenticated()
                .and().
                        // 验证出错，则回401
                        exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                // 确保我们使用无状态会话，会话不会用户存储用户的状态
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 添加一个筛选器以验证每个请求的令牌
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}