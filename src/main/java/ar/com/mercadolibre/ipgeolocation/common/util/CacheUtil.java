package ar.com.mercadolibre.ipgeolocation.common.util;

import ar.com.mercadolibre.ipgeolocation.domain.redis.SpringCacheProvider;
import java.util.Optional;

public abstract class CacheUtil {
    public final static int TWO_HOURS = 7200;
    public final static int FIVE_MINUTES = 300;

    private static SpringCacheProvider cacheProvider;

    public static void setCacheProvider(SpringCacheProvider cacheProvider) {
        CacheUtil.cacheProvider = cacheProvider;
    }

    public static SpringCacheProvider getCacheProvider() {
        return CacheUtil.cacheProvider;
    }

    public static Optional<String> getKey(String key) {
        return CacheUtil.cacheProvider.get(key);
    }

    public static Boolean deleteKey(String key) {
        return CacheUtil.cacheProvider.delete(key);
    }

}
