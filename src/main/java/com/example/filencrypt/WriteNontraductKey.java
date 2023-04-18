package com.example.filencrypt;

import java.io.*;
import java.security.SecureRandom;

public class WriteNontraductKey {

    private final int[] CNT = {2, 6, 10, 13, 16, 19, 30, 36, 41, 44, 52, 55, 57, 60, 71, 72, 74, 82, 83, 85, 86, 92, 93, 100, 102, 107, 108, 110, 117, 118, 120, 122, 131, 137, 139, 140, 143, 144, 147, 149, 163, 166, 167, 172};
    private final int[] MST = {7, 8, 13, 15, 23, 26, 27, 35, 45, 46, 51, 58, 65, 67, 68, 70, 74, 78, 90, 92, 98, 100, 101, 107, 108, 110, 113, 114, 117, 118, 137, 142, 145, 148, 153, 158, 162, 164, 172, 173, 174, 178, 185, 188, 190, 194, 197, 206, 208, 209, 211, 215, 219, 220, 227, 230, 231, 234, 236, 238};

    public String genRandCodeForContent(int streamSize) {
        String chrs = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz/+=";
        SecureRandom secureRandom = new SecureRandom();
        // 5 is the length of the string you want
        String customTag = secureRandom.ints(streamSize, 0, chrs.length()).mapToObj(i -> chrs.charAt(i))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
        return customTag;
    }

    public String genRandCodeForMaster(int streamSize) {
        String chrs = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz/$.";
        SecureRandom secureRandom = new SecureRandom();
        // 5 is the length of the string you want
        String customTag = secureRandom.ints(streamSize, 0, chrs.length()).mapToObj(i -> chrs.charAt(i))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
        return customTag;
    }

    public void writeHiddenContentKey(String keyStr, File keysFile) throws IOException {

        String base132RandStr = genRandCodeForContent(132); // length 180

        for (int i = 0; i < keyStr.length(); i++) {
            base132RandStr = new StringBuilder(base132RandStr).insert(CNT[i], keyStr.charAt(i)).toString();
        }

        BufferedReader reader = new BufferedReader(new FileReader(keysFile));
        String[] unitedKeys = reader.readLine().split("!!");
        reader.close();
        String contentKey =  unitedKeys[0] + "!!" + base132RandStr; //unitedKeys[0] = masterKey

        BufferedWriter writer = new BufferedWriter(new FileWriter(keysFile));
        writer.write(contentKey);
        writer.close();


//        String nontraductKey = genRandCodeForContent(5) + keyStr.substring(0, 4) +
//                genRandCodeForContent(2) + keyStr.substring(4, 8) +
//                genRandCodeForContent(6) + keyStr.substring(8, 12) +
//                genRandCodeForContent(5) + keyStr.substring(12, 16) +
//                genRandCodeForContent(2) + keyStr.substring(16, 20) +
//                genRandCodeForContent(9) + keyStr.substring(20, 24) +
//                genRandCodeForContent(4) + keyStr.substring(24, 28) +
//                genRandCodeForContent(8) + keyStr.substring(28, 32) +
//                genRandCodeForContent(3) + keyStr.substring(32, 36) +
//                genRandCodeForContent(7) + keyStr.substring(36, 40) +
//                genRandCodeForContent(1) + keyStr.substring(40, 43) + genRandCodeForContent(11);
//
//        BufferedReader reader = new BufferedReader(new FileReader(keysFile));
//        String[] unitedKeys = reader.readLine().split("!!");
//        reader.close();
//        String contentKey =  unitedKeys[0] + "!!" + nontraductKey; //unitedKeys[0] = masterKey
//
//        BufferedWriter writer = new BufferedWriter(new FileWriter(keysFile));
//        writer.write(contentKey);
//        writer.close();
    }


    public void writeHiddenMasterKey(String keyStr, File keysFile) throws IOException {

        String base180RandStr = genRandCodeForMaster(180); // length 180

        for (int i = 0; i < keyStr.length(); i++) {
            base180RandStr = new StringBuilder(base180RandStr).insert(MST[i], keyStr.charAt(i)).toString();
        }

        BufferedReader reader = new BufferedReader(new FileReader(keysFile));
        String[] unitedKeys = reader.readLine().split("!!");
        reader.close();
        String contentKey =  base180RandStr + "!!" + unitedKeys[1]; //unitedKeys[1] = contentKey

        BufferedWriter writer = new BufferedWriter(new FileWriter(keysFile));
        writer.write(contentKey);
        writer.close();
    }

    public int[] getCNT() {
        return CNT;
    }

    public int[] getMST() {
        return MST;
    }
}
