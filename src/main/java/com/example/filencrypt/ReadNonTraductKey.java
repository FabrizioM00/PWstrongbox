package com.example.filencrypt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadNonTraductKey {

    // flag true == MASTER
    // flag false == CONTENT
    public String translateSafe(int[] cntmst, File keysFile, boolean flag) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(keysFile));
        String[] unitedKeys = reader.readLine().split("!!");
        reader.close();

        StringBuilder traductKey = new StringBuilder();

        if(flag) {
            for (int j : cntmst)
                traductKey.append(unitedKeys[0].charAt(j)); //unitedKeys[0] = masterKey
        } else {
            for (int j : cntmst)
                traductKey.append(unitedKeys[1].charAt(j)); //unitedKeys[1] = contentKey
        }

        return traductKey.toString();
    }


//    private String traductKey;

//    public void translateSafe(File keysFile) throws IOException {
//        String nontraductKey = readText(keysFile);
//        traductKey = nontraductKey.substring(5, 9) + nontraductKey.substring(11, 15) +
//                        nontraductKey.substring(21, 25) + nontraductKey.substring(30, 34) +
//                            nontraductKey.substring(36, 40) + nontraductKey.substring(49, 53) +
//                                nontraductKey.substring(57, 61) + nontraductKey.substring(69, 73) +
//                                    nontraductKey.substring(76, 80) + nontraductKey.substring(87, 91) +
//                                        nontraductKey.substring(92, 95) + "=";
//    }
//
//    //legge prima linea del file managed.txt
//    public String readText(File keysFile) throws IOException {
//        BufferedReader reader = new BufferedReader(new FileReader(keysFile));
//        String[] unitedKeys = reader.readLine().split("!!");
//        reader.close();
//        return unitedKeys[1]; //unitedKeys[1] = contentKey
//    }
//
//    public String getTraductKey() {
//        return traductKey;
//    }
//
//    public void setTraductKey(String traductKey) {
//        this.traductKey = traductKey;
//    }


}
