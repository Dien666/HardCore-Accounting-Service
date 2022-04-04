package com.hardcore.accounting.config;

import lombok.val;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {
    @Bean
    public DefaultWebSecurityManager securityManager(Realm realm) {
        return new DefaultWebSecurityManager(realm);

    }

    /**
     * Shiro Filter
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultSecurityManager defaultSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultSecurityManager);

        LinkedHashMap<String, String> shiroFilterDefinitionMap = new LinkedHashMap<String, String>();
        //@Todo: consider different HTTP method may need different filter
        shiroFilterDefinitionMap.put("/v1.0/session", "anon");
        // shiroFilterDefinitionMap.put("/v1.0/users/**", "anon");

        shiroFilterDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroFilterDefinitionMap);

        return shiroFilterFactoryBean;
    }

    @Bean
    public HashedCredentialsMatcher matcher(){
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("SHA-256");
        matcher.setHashIterations(1000);
        matcher.setHashSalted(true);
        matcher.setStoredCredentialsHexEncoded(false);

        return matcher;
    }
}
