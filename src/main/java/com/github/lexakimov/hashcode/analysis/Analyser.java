package com.github.lexakimov.hashcode.analysis;

import com.github.lexakimov.hashcode.util.ReflectionUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class Analyser {

    public static HashMapMetaInfo analyze(HashMap<Object, Object> targetHashMap) {
        var size = targetHashMap.size();
        var loadFactor = ReflectionUtil.getFieldValue(targetHashMap, "loadFactor", Float.class);
        var table = ReflectionUtil.getFieldValue(targetHashMap, "table", Object[].class);
        var keysByBuckets = new ArrayList[table.length];

        var bucketCountBySize = new TreeMap<Integer, Integer>();

        for (int i = 0; i < table.length; i++) {
            var bucketContent = new ArrayList<>();
            keysByBuckets[i] = bucketContent;

            Object node = table[i];
            if (node == null) {
                bucketCountBySize.compute(0, (k, v) -> (v == null) ? 1 : ++v);
                continue;
            }

            processHashMapNode(node, bucketContent);
            bucketCountBySize.compute(bucketContent.size(), (k, v) -> (v == null) ? 1 : ++v);
        }

        return new HashMapMetaInfo(targetHashMap, size, loadFactor, keysByBuckets, bucketCountBySize);
    }

    private static void processHashMapNode(Object node, ArrayList<Object> bucketContent) {
        if (node == null) {
            return;
        }

        var nodeClassName = node.getClass().getName();

        switch (nodeClassName) {
            case "java.util.HashMap$TreeNode":
                Object prevNode = node;
                Object firstNode = prevNode;
                while (prevNode != null) {
                    firstNode = prevNode;
                    prevNode = ReflectionUtil.getFieldValue(node, "prev", Object.class);
                }
                Object nextNode = firstNode;
                while (nextNode != null) {
                    var storedKey = ReflectionUtil.getFieldValue(nextNode, "key", Object.class);
                    bucketContent.add(storedKey);
                    nextNode = ReflectionUtil.getFieldValue(nextNode, "next", Object.class);
                }
                break;
            case "java.util.HashMap$Node":
                while (node != null) {
                    var storedKey = ReflectionUtil.getFieldValue(node, "key", Object.class);
                    bucketContent.add(storedKey);
                    node = ReflectionUtil.getFieldValue(node, "next", Object.class);
                }
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

}
