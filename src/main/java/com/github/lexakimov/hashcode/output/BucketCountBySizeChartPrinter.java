package com.github.lexakimov.hashcode.output;

import com.github.lexakimov.hashcode.analysis.HashMapMetaInfo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class BucketCountBySizeChartPrinter implements ResultPrinter {

    private int columns = 200;

    @Override
    public void print(HashMapMetaInfo metaInfo) {
        System.out.println(GREEN + "BUCKET COUNT BY BUCKET SIZE" + RESET);
        metaInfo.getBucketCountBySize()
            .forEach((k, v) -> {
                System.out.printf("%3s : %4s ", k, v);
                var barLength = ((double) v) / metaInfo.getMaxBucketCount() * columns;
                System.out.print(k == 0 ? GRAY : GREEN);
                for (int i = 0; i < barLength; i++) {
                    System.out.print("█");
                }
                System.out.print("\n" + RESET);
            });
    }

}
