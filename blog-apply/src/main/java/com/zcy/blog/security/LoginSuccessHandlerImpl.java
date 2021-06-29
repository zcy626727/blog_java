package com.zcy.blog.security;

import com.zcy.blog.utils.Result;
import com.zcy.blog.utils.ResultArgs;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆成功
 */
public class LoginSuccessHandlerImpl implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        SecurityHandlerUtil.responseHandler(httpServletResponse,new Result(ResultArgs.USER_LOGIN_SUCCESS_CODE, ResultArgs.USER_LOGIN_SUCCESS_MSG)
            .addKV("userInfo",authentication.getPrincipal()));
    }
}
