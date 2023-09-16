package com.github.lexakimov.hashcode.output;

import com.github.lexakimov.hashcode.analysis.HashMapMetaInfo;

public class HashMapInternalStructurePrinter implements ResultPrinter {

    @Override
    public void print(HashMapMetaInfo metaInfo) {
        System.out.println(GREEN + "HASH MAP INTERNAL STRUCTURE VISUALIZATION:" + RESET);
        System.out.println();
        for (int i = 0; i < metaInfo.getTotalBucketCount(); i++) {
            var keysByBucket = metaInfo.getKeysByBuckets()[i];
            System.out.printf("BUCKET %3s [%3s ] : ", i, keysByBucket.size());
            if (keysByBucket.isEmpty()) {
                System.out.printf(YELLOW + "no keys" + RESET);
            }
            var iterator = keysByBucket.iterator();
            while (iterator.hasNext()) {
                System.out.printf("'%s'", iterator.next());
                if (iterator.hasNext()) {
                    System.out.print(CYAN + " ► " + RESET);
                }
            }
            System.out.println();
        }
    }

}
