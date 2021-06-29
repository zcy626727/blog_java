package com.zcy.blog.service;

import com.zcy.blog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zcy
 * @since 2021-05-08
 */
public interface UserService extends IService<User>, UserDetailsService {

}
