package com.yc.resfood.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.ResUser;
import com.yc.resfood.dao.ResuserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JwtTokenUtil负责执行JWT操作（创建和验证）
 */
@Component
public class JwtTokenUtil implements Serializable {
    // tocken的有效期
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    // 注入密钥
    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private ResuserDao resuserDao;

    public static void main(String[] args) {
//        UserDetails ud = new UserDetails() {
//            @Override
//            public Collection<? extends GrantedAuthority> getAuthorities() {
//                return null;
//            }
//            @Override
//            public String getPassword() {
//                return "$2a$10$/KhkiCoRR8z9Fh0By7rexeHZt.x41Gz446hWNIpXs0CpkF8cP/CRu";
//            }
//            @Override
//            public String getUsername() {
//                return "a";
//            }
//            @Override
//            public boolean isAccountNonExpired() {
//                return false;
//            }
//            @Override
//            public boolean isAccountNonLocked() {
//                return false;
//            }
//            @Override
//            public boolean isCredentialsNonExpired() {
//                return false;
//            }
//            @Override
//            public boolean isEnabled() {
//                return true;
//            }
//        }; // spring security定义类
//        JwtTokenUtil jtu = new JwtTokenUtil();
//        String token = jtu.generateToken(ud);
//        System.out.println(token);

//        String username = jtu.getUsernameFromToken(token);
//        System.out.println(username);

//        Claims c = jtu.getAllClaimsFromToken(token);
//        System.out.println(c.get("sub") + "\t" + c.get("exp") + "\t" + c.get("iat") + "\t" + c.get("pwd") + "\t" + c.get("userid") + "\t" + c.get("username"));

        System.out.println(new BCryptPasswordEncoder().encode("a"));
    }

    //为用户生成token
    /**
     * @param userDetails：由spring security 提供的用户类：包装登录用户信息
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        //存载荷
        Map<String, Object> claims = new HashMap<>();

        // 自己增加的载荷
        claims.put("username", userDetails.getUsername());
        claims.put("pwd", userDetails.getPassword());
        QueryWrapper<ResUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", userDetails.getUsername());
        wrapper.eq("pwd", userDetails.getPassword());
        ResUser user = this.resuserDao.selectOne(wrapper);
        claims.put("userid", user.getUserid());

        return doGenerateToken(claims, userDetails.getUsername());
    }

    //创建token对象
    //1. D定义令牌的声明，如Issuer, Expiration, Subject, and the ID
    //2. 使用HS512算法和密钥对JWT进行签名
    //3. 根据JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   将JWT压缩为URL安全字符串
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        // jjwt依赖中提供了工具类：构造器模式
        // claims就是一个map
        return Jwts.builder()
                // 有效载荷
                .setClaims(claims)
                .setSubject(subject)//主题
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)) //过期时间
                // 签名运算
                .signWith(SignatureAlgorithm.HS512, secret)
                // 压缩
                .compact();
    }

    //获取jwt token中的用户名
    public String getUsernameFromToken(String token) {
        //调用Claims里面的getSubject方法
        return getClaimFromToken(token, Claims::getSubject);
    }

    //从jwt token中获取过期日期
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // 从tocker中获取claim
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims); // Claims::getSubject
    }
    //利用secret密钥从token中获取信息
    public Claims getAllClaimsFromToken(String token) {
        // JWT依赖工具类
        return Jwts
                .parser() // 构造解析器
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    //检测token是否过期
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //检测token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        // 用户相同，且token不过期
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
