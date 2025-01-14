package io.shulie.takin.web.biz.cache;

import java.util.Set;

import javax.annotation.PostConstruct;

import io.shulie.takin.web.ext.util.WebPluginUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author shiyajian
 * create: 2020-12-17
 */
@Slf4j
public abstract class AbstractAgentConfigCache<T> implements AgentCacheSupport<T> {

    private final String cacheName;
    private final RedisTemplate redisTemplate;

    public AbstractAgentConfigCache(String cacheName, RedisTemplate redisTemplate) {
        this.cacheName = cacheName;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public T get(String namespace) {
        T result = (T)redisTemplate.opsForValue().get(getCacheKey(namespace));
        if (result == null) {
            result = queryValue(namespace);
            redisTemplate.opsForValue().set(getCacheKey(namespace), result);
        }
        return result;
    }

    @Override
    public void evict(String namespace) {
        redisTemplate.delete(getCacheKey(namespace));
    }

    /**
     * 项目重启之后，缓存清空下
     */
    @PostConstruct
    private void reset() {
        String beClearKey = this.cacheName + "*";
        if (!"*".equals(beClearKey)) {
            Set keys = redisTemplate.keys(beClearKey);
            if (CollectionUtils.isNotEmpty(keys)) {
                keys.forEach(key -> {
                    redisTemplate.delete(key);
                });
                log.info("清除key:{}对应的缓存成功", beClearKey);
            }
        }
    }

    private String getCacheKey(String namespace) {
        String key = cacheName + ":" + WebPluginUtils.getCustomerId();
        if (namespace != null) {
            key += ":";
            key += namespace;
        }
        return key;
    }

    /**
     * 查询值
     *
     * @param namespace 命名空间
     * @return 值
     */
    protected abstract T queryValue(String namespace);

}
