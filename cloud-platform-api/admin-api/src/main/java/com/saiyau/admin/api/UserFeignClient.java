package com.saiyau.admin.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author liuzhongyuan
 * @ClassName UserFeignClient.java
 * @Description TODO
 * @createTime 2021/10/19
 */
@FeignClient(value = "cloud-platform-admin", path = "/api/v1", contextId = "users")
public interface UserFeignClient {
}
