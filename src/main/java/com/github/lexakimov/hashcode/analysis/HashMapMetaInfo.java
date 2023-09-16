package com.github.lexakimov.hashcode.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HashMapMetaInfo {
    private HashMap<?, ?> targetMap;

    private int size;
    private float loadFactor;
    private ArrayList<?>[] keysByBuckets;
    private TreeMap<Integer, Integer> bucketCountBySize;

    public int getTotalBucketCount() {
        return keysByBuckets.length;
    }

    public int getEmptyBucketsCount() {
        return bucketCountBySize.getOrDefault(0, 0);
    }

    public int getNonEmptyBucketsCount() {
        return getTotalBucketCount() - getEmptyBucketsCount();
    }

    public int getLargestBucketSize() {
        return bucketCountBySize.lastKey();
    }

    public int getMaxBucketCount() {
        return bucketCountBySize.values().stream().mapToInt(Integer::intValue).max().orElse(0);
    }
}
