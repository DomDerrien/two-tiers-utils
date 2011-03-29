package javax.cache;

import java.util.Map;

public class MockCacheFactory implements CacheFactory {
    @Override
    @SuppressWarnings("unchecked")
    public Cache createCache(Map configuration) throws CacheException {
        return new MockCache(configuration);
    }
}
