package com.twsz.service.redis;

public interface RedisService {
    public boolean set(String key, String value);
    public boolean set(String key, String value, int expire);
    public boolean hessianSet(String key, Object value);
    public boolean hessianSet(String key, Object value, int expire);
    public boolean hSet(String key, String filed, String value);
    public boolean hSet(String key, String filed, Object value);
    public String get(String key);
    public <T> T hessianGet(String key);
    public String hGet(String key, String filed);
    public <T> T hessianHGet(String key, String filed);
}
