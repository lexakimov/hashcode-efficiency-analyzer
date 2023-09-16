package com.github.lexakimov.hashcode.output;

import static com.github.lexakimov.hashcode.EfficiencyAnalyzer.calculateEfficiency;

import com.github.lexakimov.hashcode.analysis.HashMapMetaInfo;

public class SummaryPrinter implements ResultPrinter {

    @Override
    public void print(HashMapMetaInfo metaInfo) {
        System.out.println(GREEN + "MAP PROPERTIES:" + RESET);
        System.out.printf("Size:              %5s\n", metaInfo.getSize());
        System.out.printf("LoadFactor:        %5s\n", metaInfo.getLoadFactor());
        System.out.println();

        var totalBucketCount = metaInfo.getTotalBucketCount();
        var emptyBucketsCount = metaInfo.getEmptyBucketsCount();
        var emptyBucketsPercentage = emptyBucketsCount * 100.0 / totalBucketCount;

        var nonEmptyBucketsCount = metaInfo.getNonEmptyBucketsCount();
        var nonEmptyBucketsPercentage = nonEmptyBucketsCount * 100.0 / totalBucketCount;

        System.out.println(GREEN + "SUMMARY:" + RESET);
        System.out.printf("Total buckets:     %5s\n", totalBucketCount);
        System.out.printf("Empty buckets:     %5s   %3.2f%%\n", emptyBucketsCount, emptyBucketsPercentage);
        System.out.printf("Non-empty buckets: %5s   %3.2f%%\n", nonEmptyBucketsCount, nonEmptyBucketsPercentage);
        System.out.printf("Largest bucket size:%4s\n", metaInfo.getLargestBucketSize());
        System.out.printf("Efficiency:        %4s%%\n", calculateEfficiency(metaInfo));
    }

}
