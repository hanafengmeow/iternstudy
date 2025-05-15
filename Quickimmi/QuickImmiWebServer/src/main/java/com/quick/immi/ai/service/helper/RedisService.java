/* (C) 2024 */
package com.quick.immi.ai.service.helper;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
public class RedisService {

  @Autowired private RedisTemplate redisTemplate;

  private static int LOCK_EXPIRE = 3000;

  public Set<String> keys(String pattern) {
    return redisTemplate.keys(pattern);
  }

  public void flushAll() {
    redisTemplate.getConnectionFactory().getConnection().flushAll();
  }

  public boolean expire(String key, long seconds) {
    if (seconds > 0) {
      redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }
    return true;
  }

  public long getExpire(String key) {
    return redisTemplate.getExpire(key, TimeUnit.SECONDS);
  }

  public boolean hasKey(String key) {
    return redisTemplate.hasKey(key);
  }

  public void del(String... key) {
    if (key != null && key.length > 0) {
      if (key.length == 1) {
        redisTemplate.delete(key[0]);
      } else {
        redisTemplate.delete(CollectionUtils.arrayToList(key));
      }
    }
  }

  public <V> V get(String key) {
    return key == null ? null : (V) redisTemplate.opsForValue().get(key);
  }

  public <V> boolean set(String key, V value) {
    redisTemplate.opsForValue().set(key, value);
    return true;
  }

  public Boolean setNX(String key, String value, long timeout, TimeUnit unit) {
    Boolean isExit =
        this.redisTemplate
            .getConnectionFactory()
            .getConnection()
            .setNX(key.getBytes(), value.getBytes());
    if (isExit) {
      redisTemplate.expire(key, timeout, unit);
    }
    return isExit;
  }

  public <V> boolean set(String key, V value, long seconds) {
    if (seconds > 0) {
      redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
    } else {
      set(key, value);
    }
    return true;
  }

  public boolean lock(String key) {
    String lock = key;
    try {
      return (Boolean)
          redisTemplate.execute(
              (RedisCallback)
                  connection -> {
                    long expireAt = System.currentTimeMillis() + LOCK_EXPIRE;
                    Boolean acquire =
                        connection.setNX(lock.getBytes(), String.valueOf(expireAt).getBytes());
                    if (acquire) {
                      return true;
                    } else {
                      // 判断该key上的值是否过期了
                      byte[] value = connection.get(lock.getBytes());
                      if (Objects.nonNull(value) && value.length > 0) {
                        long expireTime = Long.parseLong(new String(value));
                        if (expireTime < System.currentTimeMillis()) {
                          // 如果锁已经过期
                          byte[] oldValue =
                              connection.getSet(
                                  lock.getBytes(),
                                  String.valueOf(System.currentTimeMillis() + LOCK_EXPIRE)
                                      .getBytes());
                          // 防止死锁
                          return Long.parseLong(new String(oldValue)) < System.currentTimeMillis();
                        }
                      }
                    }
                    return false;
                  });
    } finally {
      RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
    }
  }
}
