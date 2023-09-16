package com.github.lexakimov.hashcode.output;

import com.github.lexakimov.hashcode.analysis.HashMapMetaInfo;

@SuppressWarnings("unused")
public interface ResultPrinter {

    String RESET = "\033[0m";
    String RED = "\033[0;31m";
    String GREEN = "\033[0;32m";
    String BLUE = "\033[0;34m";
    String YELLOW = "\033[0;33m";
    String PURPLE = "\033[0;35m";
    String CYAN = "\033[0;36m";
    String GRAY = "\033[0;255m";

    void print(HashMapMetaInfo metaInfo);
}
