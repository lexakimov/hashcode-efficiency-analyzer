package com.github.lexakimov.hashcode.output;

import com.github.lexakimov.hashcode.analysis.HashMapMetaInfo;

public class HashMapInternalStructurePrinter implements ResultPrinter {

    @Override
    public void print(HashMapMetaInfo metaInfo) {
        int bucketCountDigitsCount = (int) (Math.log10(metaInfo.getTotalBucketCount()) + 1);
        int bucketSizeDigitsCount = (int) (Math.log10(metaInfo.getLargestBucketSize()) + 1);

        System.out.println(GREEN + "HASH MAP INTERNAL STRUCTURE VISUALIZATION:" + RESET);
        for (int i = 0; i < metaInfo.getTotalBucketCount(); i++) {
            var keysByBucket = metaInfo.getKeysByBuckets()[i];
            System.out.printf("BUCKET %" + bucketCountDigitsCount + "s (%" + bucketSizeDigitsCount + "s) : ",
                i, keysByBucket.size());
            if (keysByBucket.isEmpty()) {
                System.out.printf("[" + CYAN + "empty" + RESET + "]");
            }
            var iterator = keysByBucket.iterator();
            while (iterator.hasNext()) {
                System.out.printf("'%s'", iterator.next());
                if (iterator.hasNext()) {
                    System.out.print(CYAN + " > " + RESET);
                }
            }
            System.out.println();
        }
    }

}
