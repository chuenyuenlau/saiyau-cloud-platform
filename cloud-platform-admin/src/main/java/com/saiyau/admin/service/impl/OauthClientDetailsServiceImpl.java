package com.saiyau.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saiyau.admin.mapper.OauthClientDetailsMapper;
import com.saiyau.admin.pojo.entity.OauthClientDetails;
import com.saiyau.admin.service.OauthClientDetailsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * oauth2 客户端信息表 服务实现类
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-21
 */
@Service
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements OauthClientDetailsService {

}
