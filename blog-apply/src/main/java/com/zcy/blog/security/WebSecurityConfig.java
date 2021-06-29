package com.zcy.blog.security;

import com.zcy.blog.entity.User;
import com.zcy.blog.service.UserService;
import com.zcy.blog.utils.Result;
import com.zcy.blog.utils.ResultArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    //redis 操作工具类
    private RedisTemplate redisTemplate;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/article/**").permitAll()
                .antMatchers("/category/**").permitAll()
                .antMatchers("/test/**").permitAll()
                .antMatchers("/tag/**").permitAll()
                .antMatchers("/test/test").authenticated()
//                .antMatchers("/hello").permitAll()//所有请求允许
//                .antMatchers("/test/admin").hasRole("管理员")//要有管理员权限
                .anyRequest().authenticated()//只要登录就能访问
                .and().cors().and().csrf().disable()//关闭csrf
                .formLogin().loginProcessingUrl("/login")//登录请求路径
                .and().logout().logoutSuccessHandler(new LogoutSuccessHandler(){
                    @Override
                    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        SecurityHandlerUtil.responseHandler(httpServletResponse,new Result(ResultArgs.LOGOUT_SUCCESS_CODE,ResultArgs.LOGOUT_SUCCESS_MSG));
                    }
                }).logoutUrl("/logout")//注销请求路径
                .and().exceptionHandling().accessDeniedHandler(new AccessDeniedHandler(){
                    @Override
                    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                        SecurityHandlerUtil.responseHandler(httpServletResponse,new Result(ResultArgs.AUTHORIZE_FAILURE_CODE,ResultArgs.AUTHORIZE_FAILURE_MSG));
                    }
                })//发生异常处理器
                .authenticationEntryPoint(new AuthenticationEntryPoint(){
                    @Override
                    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        SecurityHandlerUtil.responseHandler(httpServletResponse,new Result(ResultArgs.USER_NOT_LOGIN_FAILURE_CODE,ResultArgs.USER_NOT_LOGIN_FAILURE_MSG));
                    }
                });//权限不足处理器


        //替换filter：使支持post等登录
        http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(new Md5PasswordEncoder());
    }

    CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
      CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
      filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler(){//登陆成功
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                User principal = (User)authentication.getPrincipal();
                SecurityHandlerUtil.responseHandler(httpServletResponse,new Result(ResultArgs.USER_LOGIN_SUCCESS_CODE, ResultArgs.USER_LOGIN_SUCCESS_MSG)
                        .addKV("token",principal.getId()));
            }
        });
      filter.setAuthenticationFailureHandler(new AuthenticationFailureHandler(){
          @Override
          public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
              SecurityHandlerUtil.responseHandler(httpServletResponse,new Result(ResultArgs.USER_NOT_EXIST_FAILURE_CODE, ResultArgs.USER_NOT_EXIST_FAILURE_MSG));
          }
      });
      filter.setAuthenticationManager(authenticationManagerBean());	
      return filter;
    }


    /**
     * 配置哪些请求不拦截
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/**","/swagger-ui.html/**","/img/**");

    }


}

