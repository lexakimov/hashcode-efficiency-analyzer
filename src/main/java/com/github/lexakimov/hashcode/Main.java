package com.github.lexakimov.hashcode;

import com.github.lexakimov.hashcode.analysis.Analyser;
import com.github.lexakimov.hashcode.output.BucketCountBySizeChartPrinter;
import com.github.lexakimov.hashcode.output.CompositePrinter;
import com.github.lexakimov.hashcode.output.HashMapInternalStructurePrinter;
import com.github.lexakimov.hashcode.output.SummaryPrinter;
import com.github.lexakimov.hashcode.util.RandomUtil;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        // 0.75f
        // 16
        var targetHashMap = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            targetHashMap.put(RandomUtil.generateRandomNumber(), null);
            targetHashMap.put(RandomUtil.generateRandomString(), null);
        }

        // ---------------------------------------------------------------------------------------

        var metaInfo = Analyser.analyze(targetHashMap);
        new CompositePrinter(
            true,
            new HashMapInternalStructurePrinter(),
            new SummaryPrinter(),
            new BucketCountBySizeChartPrinter()
        ).print(metaInfo);
    }

}