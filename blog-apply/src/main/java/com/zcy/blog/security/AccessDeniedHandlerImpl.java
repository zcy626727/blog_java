package com.zcy.blog.security;


import com.zcy.blog.utils.Result;
import com.zcy.blog.utils.ResultArgs;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 没有权限
 */
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        SecurityHandlerUtil.responseHandler(httpServletResponse,new Result(ResultArgs.AUTHORIZE_FAILURE_CODE,ResultArgs.AUTHORIZE_FAILURE_MSG));
    }
}

