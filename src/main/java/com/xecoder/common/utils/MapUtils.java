/*
 * Copyright 2014 Jakub Jirutka <jakub@jirutka.cz>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xecoder.common.utils;

import java.util.Map;

public class MapUtils extends org.apache.commons.collections.MapUtils {

    /**
     * source中与target不重复部分的的值加入到target
     *
     * @param target The target map where to put new entries.
     * @param source The source map from which read the entries.
     */
    public static <K, V> void putAllIfAbsent(Map<K, V> target, Map<K, V> source) {

        for (Map.Entry<K, V> entry : source.entrySet()) {
            if (!target.containsKey(entry.getKey())) {
                target.put(entry.getKey(), entry.getValue());
            }
        }
    }
}
