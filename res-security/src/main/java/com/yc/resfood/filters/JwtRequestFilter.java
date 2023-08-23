package com.yc.resfood.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.resfood.config.JwtTokenUtil;
import com.yc.resfood.service.JwtUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

/**
 * JwtRequestFilter扩展了Spring WebFilterOncePerRequestFilter类
 * 对于任何传入的请求，都会执行Filter类，它检查是否具有有效的JWT令牌
 * 如果它具有有效的JWT令牌，则上下文中设置身份验证，以指定当前用户已通过身份验证
 */
@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService; // 验证用户身份的业务类
    @Autowired
    private JwtTokenUtil jwtTokenUtil; // token工具类（生成token，验证token）

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // 因为客户端会将token以 Authorization:Bearer token 的形式在header和、中传输
        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        // JWT Token以"Bearer token"值的形式提供. 移除Bearer，得到token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7); //只取token
            try {
                // 取出用户名
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        } else {
            log.warn(requestTokenHeader +"=================AAAAAAAAAAAAAAAAAAAAAA");
            logger.warn("JWT Token does not begin with Bearer String");
        }

        // 判断用户名
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 根据用户名获取信息（权限）
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
            // jwtToken是用户传过来的  userDetails是根据用户名在数据库中查出来的
            // 验证token中的userDetails是否有效
            //重新用 HS256（userDetails，secret） 计算得到一个 token 与 jwtToken比较 看是否相等
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                //构建器模式
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 在上下文中设置身份验证后，我们指定当前用户已通过身份验证，所以它通过了Spring安全配置成功
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
