package ru.netology;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final AtomicInteger countLength3 = new AtomicInteger(0);
    private static final AtomicInteger countLength4 = new AtomicInteger(0);
    private static final AtomicInteger countLength5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindromeChecker = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    incrementCounter(text.length());
                }
            }
        });

        Thread sameLetterChecker = new Thread(() -> {
            for (String text : texts) {
                if (isSameLetter(text)) {

                    incrementCounter(text.length());
                }
            }
        });

        Thread ascendingOrderChecker = new Thread(() -> {
            for (String text : texts) {
                if (isAscendingOrder(text)) {
                    incrementCounter(text.length());
                }
            }
        });

        palindromeChecker.start();
        sameLetterChecker.start();
        ascendingOrderChecker.start();

        palindromeChecker.join();
        sameLetterChecker.join();
        ascendingOrderChecker.join();

        System.out.println("Красивых слов с длиной 3: " + countLength3 + " шт");
        System.out.println("Красивых слов с длиной 4: " + countLength4 + " шт");
        System.out.println("Красивых слов с длиной 5: " + countLength5 + " шт");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    private static boolean isPalindrome(String text) {
        int n = text.length();
        for (int i = 0; i < n / 2; i++) {
            if (text.charAt(i) != text.charAt(n - i - 1)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isSameLetter(String text) {
        char first = text.charAt(0);
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != first) {
                return false;
            }
        }
        return true;
    }

    private static boolean isAscendingOrder(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) > text.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    private static void incrementCounter(int length) {
        switch (length) {
            case 3:
                countLength3.incrementAndGet();
                break;
            case 4:
                countLength4.incrementAndGet();
                break;
            case 5:
                countLength5.incrementAndGet();
                break;
        }
    }
}