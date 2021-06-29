package com.zcy.blog.mapper;

import com.zcy.blog.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zcy
 * @since 2021-05-08
 */
public interface UserMapper extends BaseMapper<User> {

    User selectByUserName(String username);
}
