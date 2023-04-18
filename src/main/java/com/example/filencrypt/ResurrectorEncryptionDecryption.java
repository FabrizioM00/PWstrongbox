//package com.example.filencrypt;
//
//
//import com.example.model.WebData;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//
//import javax.crypto.KeyGenerator;
//import javax.crypto.NoSuchPaddingException;
//import javax.crypto.SecretKey;
//import java.io.File;
//import java.io.IOException;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.util.Base64;
//import java.util.List;
//
//public class ResurrectorEncryptionDecryption {
//
//    public static void main(String[] args) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
//
//        String originalContent = "[{\"websiteUrl\":\"https://google.com/\",\"accs\":[{\"username\":\"paperina\",\"email\":\"papera@libero.it\",\"pw\":\"zucca90\"},{\"username\":\"topolino\",\"email\":\"topolino@outlook.com\",\"pw\":\"carotina321\"}]},{\"websiteUrl\":\"https://example.com/\",\"accs\":[{\"username\":\"rocco\",\"email\":\"rocco@gmail.com\",\"pw\":\"stocazzo22\"},{\"username\":null,\"email\":\"epicode@internet.com\",\"pw\":\"chitarra123\"}]},{\"websiteUrl\":\"http://gmail.com/\",\"accs\":[{\"username\":null,\"email\":\"cicciopasticcio@outlook.com\",\"pw\":\"lamiabellapw321\"},{\"username\":null,\"email\":\"iwannabecool@cool.com\",\"pw\":\"pianoforte321\"}]},{\"websiteUrl\":\"https://facebook.com/\",\"accs\":[{\"username\":null,\"email\":\"fabry@internet.com\",\"pw\":\"ginn29\"}]}]";
//        File keysFile = new File(System.getProperty("user.dir"), "keys");
//        File contentDataFile = new File(System.getProperty("user.dir"), "contentdata");
//
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        List<WebData> webDatas = objectMapper.readValue(originalContent, new TypeReference<List<WebData>>(){});
//
////        System.out.println(webDatas.get(0).getAccs().get(1).getEmail());
////
////        System.out.println(objectMapper.writeValueAsString(webDatas));
//
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        keyGenerator.init(256);
//        SecretKey secretKey = keyGenerator.generateKey();
//        WriteNontraductKey.writeHiddenContentKey(Base64.getEncoder().encodeToString(secretKey.getEncoded()), keysFile);
//
//
//
//        System.out.println(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
//        ReadNonTraductKey rntk = new ReadNonTraductKey();
//        rntk.translateSafe(keysFile);
//        System.out.println(rntk.getTraductKey());
//
////        byte[] decodedKey = Base64.getDecoder().decode("wpo0MBH1WspWa+trHDHWIK+Ob6yBwgwiTkwq/RxyyVY=");
////        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
//
//        FileEncrypterDecrypter fileEncrypterDecrypter
//                = new FileEncrypterDecrypter(secretKey, "AES/CBC/PKCS5Padding");
//        fileEncrypterDecrypter.encrypt(originalContent, contentDataFile);
//
//
//        String decryptedContent = fileEncrypterDecrypter.decrypt(contentDataFile);
//            System.out.println(decryptedContent);
//
////            byte[] rawData = getPasswordBasedKey("rntk.getTraductKey()").getEncoded();
////        System.out.println(Base64.getEncoder().encodeToString(rawData));
////
////        byte[] rawDatas = getPasswordBasedKey("arraydicaratteri".toCharArray()).getEncoded();
////        System.out.println(Base64.getEncoder().encodeToString(rawDatas));
//
//    }
//
//}
