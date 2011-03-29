package javax.cache;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MockCache implements Cache {
    Map<Object, Object> storage;
    public MockCache(Map<Object, Object> configuration) {
        // Configuration is ignored
        // Storage created when the first data insertion is attempted
    }

    @Override public void addListener(CacheListener arg0) { throw new RuntimeException("Not implemented in mock class -- needs local override."); }
    @Override public void evict() { throw new RuntimeException("Not implemented in mock class -- needs local override."); }
    @SuppressWarnings("unchecked") @Override public Map getAll(Collection arg0) { throw new RuntimeException("Not implemented in mock class -- needs local override."); }
    @Override public CacheEntry getCacheEntry(Object arg0) { throw new RuntimeException("Not implemented in mock class -- needs local override."); }
    @Override public CacheStatistics getCacheStatistics() { throw new RuntimeException("Not implemented in mock class -- needs local override."); }
    @Override public void load(Object arg0) { throw new RuntimeException("Not implemented in mock class -- needs local override."); }
    @SuppressWarnings("unchecked") @Override public void loadAll(Collection arg0) { throw new RuntimeException("Not implemented in mock class -- needs local override."); }
    @Override public Object peek(Object arg0) { throw new RuntimeException("Not implemented in mock class -- needs local override."); }
    @Override public void removeListener(CacheListener arg0) { throw new RuntimeException("Not implemented in mock class -- needs local override."); }

    @Override public void clear() {
        if (storage != null ) {
            storage.clear();
        }
    }
    @Override public boolean containsKey(Object key) {
        if (storage != null ) {
            return storage.containsKey(key);
        }
        return false;
    }
    @Override public boolean containsValue(Object value) {
        if (storage != null ) {
            return storage.containsValue(value);
        }
        return false;
    }
    @SuppressWarnings("unchecked") @Override public Set entrySet() {
        if (storage != null ) {
            return storage.entrySet();
        }
        return null;
    }
    @Override public Object get(Object key) {
        if (storage != null ) {
            return storage.get(key);
        }
        return null;
    }
    @Override public boolean isEmpty() {
        if (storage != null ) {
            return storage.isEmpty();
        }
        return false;
    }
    @SuppressWarnings("unchecked") @Override public Set keySet() {
        if (storage != null ) {
            return storage.keySet();
        }
        return null;
    }
    @Override public Object put(Object key, Object value) {
        if (storage == null ) {
            storage = new HashMap<Object, Object>();
        }
        return storage.put(key, value);
    }
    @SuppressWarnings("unchecked") @Override public void putAll(Map m) {
        if (storage == null ) {
            storage = new HashMap<Object, Object>();
        }
        storage.putAll(m);
    }
    @Override public Object remove(Object key) {
        if (storage != null ) {
            return storage.remove(key);
        }
        return false;
    }
    @Override public int size() {
        if (storage != null ) {
            return storage.size();
        }
        return 0;
    }
    @SuppressWarnings("unchecked") @Override public Collection values() {
        if (storage != null ) {
            return storage.values();
        }
        return null;
    }
}
