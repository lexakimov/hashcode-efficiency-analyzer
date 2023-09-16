package com.github.lexakimov.hashcode.output;

import com.github.lexakimov.hashcode.analysis.HashMapMetaInfo;

public class SummaryPrinter implements ResultPrinter {

    @Override
    public void print(HashMapMetaInfo metaInfo) {
        System.out.println(GREEN + "MAP PROPERTIES:" + RESET);
        System.out.printf("Size:              %5s\n", metaInfo.getSize());
        System.out.printf("LoadFactor:        %5s\n", metaInfo.getLoadFactor());
        System.out.println();

        System.out.println(GREEN + "SUMMARY:" + RESET);
        System.out.printf("Total buckets:     %5s\n", metaInfo.getTotalBucketCount());
        System.out.printf("Empty buckets:     %5s\n", metaInfo.getEmptyBucketsCount());
        System.out.printf("Non-empty buckets: %5s\n", metaInfo.getNonEmptyBucketsCount());
        System.out.printf("Largest bucket size:%4s\n", metaInfo.getLargestBucketSize());
        System.out.printf("Efficiency:        %4s%%\n", "TODO");
    }

}
