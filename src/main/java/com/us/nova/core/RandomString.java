package com.us.nova.core;

import java.util.Random;

public class RandomString {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SECTION_LENGTH = 4;
    private static final int NUM_SECTIONS = 4;
    private static final String SEPARATOR = "-";

    public static String generateRandomString() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < NUM_SECTIONS; i++) {
            for (int j = 0; j < SECTION_LENGTH; j++) {
                sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
            }
            if (i < NUM_SECTIONS - 1) {
                sb.append(SEPARATOR);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(generateRandomString());
    }
}
