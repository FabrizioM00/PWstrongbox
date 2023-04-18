//package com.example.filencrypt;
//
//import java.io.*;
//import java.security.SecureRandom;
//import java.util.Arrays;
//import java.util.Comparator;
//
//public class RandomizerTests {
//
//
//    public static String genRandCodeForMaster(int streamSize) {
//        String chrs = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz/$.";
//        SecureRandom secureRandom = new SecureRandom();
//        // 5 is the length of the string you want
//        String customTag = secureRandom.ints(streamSize, 0, chrs.length()).mapToObj(i -> chrs.charAt(i))
//                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
//        return customTag;
//    }
//
//    public static String addChar(String str, char ch, int position) {
//        return new StringBuilder(str).insert(position, ch).toString();
//    }
//
//    public static void main(String[] args) throws IOException {
////
////        String hashedPw = "$2a$12$N3VfPDYlTK20UsnSLWctguNto2SoaMb5PypvV"; // length = 44
////        String base180RandStr = genRandCodeForMaster(132); // length 132
////        String newGarbledStr = base180RandStr;
////
////        SecureRandom r = new SecureRandom();
////        int[] a = r.ints(0, 175).distinct().limit(44).sorted().toArray();
////
////
////        for (int i = 0; i < hashedPw.length(); i++) {
////            newGarbledStr = new StringBuilder(newGarbledStr).insert(a[i], hashedPw.charAt(i)).toString();
////        }
////
////        System.out.println(hashedPw);
////        System.out.println("base180RandStr: " + base180RandStr);
////        System.out.println(Arrays.toString(a));
////        System.out.println(newGarbledStr);
////
////        StringBuilder traductKey = new StringBuilder();
////        for (int j : a)
////            traductKey.append(newGarbledStr.charAt(j));
////
////        System.out.println(traductKey.toString());
//
//        File keysFile = new File(System.getProperty("user.dir"), "keys");
//        BufferedReader reader = new BufferedReader(new FileReader(keysFile));
//        String[] unitedKeys = reader.readLine().split("!!");
//        reader.close();
//
//        StringBuilder traductKey = new StringBuilder();
//        int[] cnt = {2, 6, 10, 13, 16, 19, 30, 36, 41, 44, 52, 55, 57, 60, 71, 72, 74, 82, 83, 85, 86, 92, 93, 100, 102, 107, 108, 110, 117, 118, 120, 122, 131, 137, 139, 140, 143, 144, 147, 149, 163, 166, 167, 172};
//
//        for (int j : cnt)
//                traductKey.append(unitedKeys[1].charAt(j)); //unitedKeys[1] = contentKey
//
//        System.out.println(traductKey.toString());
//
//
//
//
//    }
//
//}
