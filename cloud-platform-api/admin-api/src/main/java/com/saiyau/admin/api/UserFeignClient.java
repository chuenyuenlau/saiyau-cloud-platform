package com.saiyau.admin.api;

import com.saiyau.admin.pojo.entity.SysUser;
import com.saiyau.common.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author liuzhongyuan
 * @ClassName UserFeignClient.java
 * @Description TODO
 * @createTime 2021/10/19
 */
@FeignClient(value = "cloud-platform-admin", path = "/api/v1", contextId = "users")
public interface UserFeignClient {
    @RequestMapping("/users/username/{username}")
    R<SysUser> getUserByUsername(@PathVariable String username);
}
