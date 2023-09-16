package com.github.lexakimov.hashcode;

import com.github.lexakimov.hashcode.analysis.HashMapMetaInfo;
import java.util.Map;

public class EfficiencyAnalyzer {

    public static int calculateEfficiency(HashMapMetaInfo result) {
        var bucketCountBySize = result.getBucketCountBySize();

        long sum = 0;

        for (Integer bucketSize : bucketCountBySize.keySet()) {
            if (bucketSize == 0) {
                continue;
            }
            sum += ((long) bucketSize) * bucketCountBySize.get(bucketSize);
        }

        var minNotZeroCount = bucketCountBySize
            .entrySet()
            .stream()
            .filter(e -> e.getKey() != 0)
            .map(Map.Entry::getValue)
            .mapToInt(Integer::intValue)
            .min()
            .orElse(0);

        return (int) (minNotZeroCount * 100 / sum);
    }

}
