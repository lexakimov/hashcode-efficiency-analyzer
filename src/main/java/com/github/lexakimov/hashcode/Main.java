package com.github.lexakimov.hashcode;

import static com.github.lexakimov.hashcode.util.RandomUtil.generateRandomString;

import com.github.lexakimov.hashcode.analysis.Analyser;
import com.github.lexakimov.hashcode.output.BucketCountBySizeChartPrinter;
import com.github.lexakimov.hashcode.output.CompositePrinter;
import com.github.lexakimov.hashcode.output.HashMapInternalStructurePrinter;
import com.github.lexakimov.hashcode.output.SummaryPrinter;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        var targetHashMap = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            targetHashMap.put(generateRandomString(), null);
        }

        var metaInfo = Analyser.analyze(targetHashMap);
        new CompositePrinter(
            true,
            new HashMapInternalStructurePrinter(),
            new SummaryPrinter(),
            new BucketCountBySizeChartPrinter()
        ).print(metaInfo);
    }

}