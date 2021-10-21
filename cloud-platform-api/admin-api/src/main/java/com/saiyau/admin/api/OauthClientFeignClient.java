package com.saiyau.admin.api;

import com.saiyau.admin.pojo.entity.OauthClientDetails;
import com.saiyau.common.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author liuzhongyuan
 * @ClassName OauthClientFeignClient.java
 * @Description TODO
 * @createTime 2021/10/21
 */
@FeignClient(value = "cloud-platform-admin", path = "/api/v1", contextId = "oauth-client")
public interface OauthClientFeignClient {
    @RequestMapping("/oauth-client/{clientId}")
    R<OauthClientDetails> getOauthClientById(@PathVariable String clientId);
}
