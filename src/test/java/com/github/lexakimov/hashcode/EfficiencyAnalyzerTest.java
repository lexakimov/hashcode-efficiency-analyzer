package com.github.lexakimov.hashcode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.lexakimov.hashcode.analysis.Analyser;
import com.github.lexakimov.hashcode.analysis.HashMapMetaInfo;
import com.github.lexakimov.hashcode.output.BucketCountBySizeChartPrinter;
import com.github.lexakimov.hashcode.output.CompositePrinter;
import com.github.lexakimov.hashcode.output.HashMapInternalStructurePrinter;
import com.github.lexakimov.hashcode.output.SummaryPrinter;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

class EfficiencyAnalyzerTest {

    @Test
    void _100_percent() {
        var targetHashMap = new HashMap<>(4, 1f);
        for (int i = 0; i < 28; i++) {
            targetHashMap.put(i, null);
        }

        var metaInfo = analyze(targetHashMap);
        int percentage = EfficiencyAnalyzer.calculateEfficiency(metaInfo);
        assertEquals(100, percentage);
    }

    @Test
    void _25_percent() {
        var targetHashMap = new HashMap<>(4, 1f);
        targetHashMap.put(0, null);
        targetHashMap.put(1, null);
        targetHashMap.put(3, null);
        targetHashMap.put(4, null);
        targetHashMap.put(5, null);
        targetHashMap.put(9, null);
        targetHashMap.put(11, null);
        targetHashMap.put(13, null);

        var metaInfo = analyze(targetHashMap);
        int percentage = EfficiencyAnalyzer.calculateEfficiency(metaInfo);
        assertEquals(25, percentage);
    }

    @Test
    void _11_percent() {
        var targetHashMap = new HashMap<>();
        targetHashMap.put("egqUNC6d4H", null);
        targetHashMap.put("8uRVSP62xq", null);
        targetHashMap.put("YtR06GqdEp", null);
        targetHashMap.put("EhqfxZx6Zk", null);
        targetHashMap.put("xtqxBLNb6j", null);
        targetHashMap.put("Sg64DIB1IQ", null);
        targetHashMap.put("BMCHcOpqEN", null);
        targetHashMap.put("rr7GWsyNNI", null);
        targetHashMap.put("IDbgU5IBIE", null);

        var metaInfo = analyze(targetHashMap);
        int percentage = EfficiencyAnalyzer.calculateEfficiency(metaInfo);
        assertEquals(11, percentage);
    }

    private static HashMapMetaInfo analyze(HashMap<Object, Object> targetHashMap) {
        var metaInfo = Analyser.analyze(targetHashMap);
        new CompositePrinter(
            true,
            new HashMapInternalStructurePrinter(),
            new SummaryPrinter(),
            new BucketCountBySizeChartPrinter(100)
        ).print(metaInfo);
        return metaInfo;
    }
}
