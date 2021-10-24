package com.benyq.guochatapi.base.interceptor;

import com.benyq.guochatapi.base.error.ErrorCode;
import com.benyq.guochatapi.base.error.MarketException;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JWTInterceptor implements HandlerInterceptor {

    @Resource
    private JwtConfig jwtConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("request url " + request.getRequestURI());
        // Token 验证
        String token = request.getHeader(jwtConfig.getHeader());
        Claims claims = jwtConfig.getTokenClaim(token);
        if (claims == null || jwtConfig.isTokenExpired(claims.getExpiration())) {
            throw new MarketException(ErrorCode.ERROR_TOKEN.getErrorMsg(), ErrorCode.ERROR_TOKEN.getErrorCode());
        }

        //设置 uid, 后面controller中可能会用到，这样请求时不用传uid了
        request.setAttribute("id", claims.getSubject() != null ? claims.getSubject() : "");
        return true;
    }

}
