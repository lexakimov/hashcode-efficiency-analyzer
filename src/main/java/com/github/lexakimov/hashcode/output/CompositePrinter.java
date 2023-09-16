package com.github.lexakimov.hashcode.output;

import com.github.lexakimov.hashcode.analysis.HashMapMetaInfo;
import java.util.Arrays;
import java.util.List;

public class CompositePrinter implements ResultPrinter {
    private final boolean separateOutputs;
    private final List<ResultPrinter> delegates;

    public CompositePrinter(boolean separateOutputs, ResultPrinter ... delegates) {
        this.separateOutputs = separateOutputs;
        this.delegates = Arrays.asList(delegates);
    }

    @Override
    public void print(HashMapMetaInfo metaInfo) {
        for (ResultPrinter delegate : delegates) {
            delegate.print(metaInfo);
            if (separateOutputs) {
                System.out.println();
            }
        }
    }
}
