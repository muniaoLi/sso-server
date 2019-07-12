package com.muniao.ssoserver.service;

public interface RedisService
{
    boolean hasKey(String key);

    String checkUsernameAndPassword(String username, String password);
}
