package com.xecoder.common.json;
import java.util.HashMap;

public class JsonMap<K, V> extends HashMap<K, V> {

    public V put(K key, V value) {
        if (key == null || value == null || value.equals("") || key.equals(""))
            return null;
        return super.put(key, value);
    }
}
