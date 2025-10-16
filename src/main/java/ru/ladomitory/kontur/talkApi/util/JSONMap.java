package ru.ladomitory.kontur.talkApi.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class JSONMap extends HashMap<String, String> {
    public JSONMap() {
        super();
    }

    public String toJSONString() {
        String result = "{";
        Iterator<String> iterator = this.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            result += key + ": " + this.get(key);
            if (iterator.hasNext()) {
                result += ", ";
            } else {
                result += "}";
            }
        }
        return result;
    }
}
