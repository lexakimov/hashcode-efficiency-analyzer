package com.github.lexakimov.hashcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String GRAY = "\033[0;255m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";

    public static void main(String[] args) {
        var object = new Object();

        // 0.75f
        // 16
        var hashMap = new HashMap<>(20, 20f);

        for (int i = 0; i < 100; i++) {
            hashMap.put(generateRandomNumber(), object);
            hashMap.put(generateRandomString(), object);
        }

        var loadFactor = getFieldValue(hashMap, "loadFactor", Float.class);
        var table = getFieldValue(hashMap, "table", Object[].class);

        ArrayList<Object>[] keysByBuckets = new ArrayList[table.length];

        var quantiles = new TreeMap<Integer, Integer>();
        var maxQuantile = new AtomicInteger();

        for (int i = 0; i < table.length; i++) {
            var bucketContent = new ArrayList<>();
            keysByBuckets[i] = bucketContent;

            Object node = table[i];
            if (node == null) {
                quantiles.compute(0, (k, v) -> (v == null) ? 1 : ++v);
                maxQuantile.set(Math.max(maxQuantile.get(), quantiles.get(0)));
                continue;
            }

            processHashMapNode(node, bucketContent);
            quantiles.compute(bucketContent.size(), (k, v) -> (v == null) ? 1 : ++v);
            maxQuantile.set(Math.max(maxQuantile.get(), quantiles.get(bucketContent.size())));
        }


        printResult(keysByBuckets, quantiles, maxQuantile, hashMap.size(), loadFactor);
    }

    private static <T> T getFieldValue(Object object, String fieldName, Class<T> returnType) {
        try {
            try {
                var field = object.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                return returnType.cast(field.get(object));
            } catch (NoSuchFieldException nsfe) {
                var field = object.getClass().getSuperclass().getSuperclass().getDeclaredField(fieldName);
                field.setAccessible(true);
                return returnType.cast(field.get(object));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void processHashMapNode(Object node, ArrayList<Object> bucketContent) {
        if (node == null) {
            return;
        }
        if (node.getClass().getName().equals("java.util.HashMap$Node")) {
            while (node != null) {
                var storedKey = getFieldValue(node, "key", Object.class);
                bucketContent.add(storedKey);
                node = getFieldValue(node, "next", Object.class);
            }
        } else if (node.getClass().getName().equals("java.util.HashMap$TreeNode")) {
            Object prevNode = node;
            Object firstNode = prevNode;
            while (prevNode != null) {
                firstNode = prevNode;
                prevNode = getFieldValue(node, "prev", Object.class);
            }
            Object nextNode = firstNode;
            while (nextNode != null) {
                var storedKey = getFieldValue(nextNode, "key", Object.class);
                bucketContent.add(storedKey);
                nextNode = getFieldValue(nextNode, "next", Object.class);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static void printResult(
        ArrayList<Object>[] keysByBuckets,
        TreeMap<Integer, Integer> quantiles,
        AtomicInteger maxQuantile,
        int size,
        float loadFactor
    ) {

        boolean printStructure = false;

        if (printStructure) {
            System.out.println();
            System.out.println(GREEN + "HASH MAP INTERNAL STRUCTURE VISUALIZATION:" + RESET);
            System.out.println();
        }

        int empty = 0;
        int biggestBucket = 0;

        for (int i = 0; i < keysByBuckets.length; i++) {
            ArrayList<Object> keysByBucket = keysByBuckets[i];
            if (printStructure) {
                System.out.printf("BUCKET %3s [%3s ] : ", i, keysByBucket.size());
            }
            if (keysByBucket.isEmpty()) {
                empty++;
                if (printStructure) {
                    System.out.printf(YELLOW + "no keys" + RESET);
                }
            }
            biggestBucket = Math.max(biggestBucket, keysByBucket.size());
            if (printStructure) {
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
        if (printStructure) {
            System.out.println();
        }

        System.out.println();
        int columns = 200;
        System.out.println(GREEN + "QUANTILES: [bucket size : bucket count]" + RESET);
        quantiles.forEach((k, v) -> {
            System.out.printf("%3s : %4s ", k, v);
            var barLength = ((double) v) / maxQuantile.get() * columns;
            System.out.print(k == 0 ? GRAY : GREEN);
            for (int i = 0; i < barLength; i++) {
                System.out.print("█");
            }
            System.out.print("\n" + RESET);
        });
        System.out.println();

        System.out.println(GREEN + "MAP PROPERTIES:" + RESET);
        System.out.printf("Size:              %5s\n", size);
        System.out.printf("LoadFactor:        %5s\n", loadFactor);
        System.out.printf("Buckets:           %5s\n", keysByBuckets.length);
        System.out.println();

        System.out.println(GREEN + "SUMMARY:" + RESET);
        System.out.printf("Empty buckets:     %5s\n", empty);
        System.out.printf("Non-empty buckets: %5s\n", keysByBuckets.length - empty);
        System.out.printf("Longest bucket:    %5s\n", biggestBucket);
        System.out.printf("Efficiency:        %4s%%\n", "TODO");
    }

    public static String generateRandomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        var random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
            .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }

    public static Integer generateRandomNumber() {
        var random = new Random();
        return random.nextInt(Integer.MAX_VALUE);
    }
}