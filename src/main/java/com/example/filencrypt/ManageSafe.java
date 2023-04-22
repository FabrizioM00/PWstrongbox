package com.example.filencrypt;

import com.example.model.Acc;
import com.example.model.WebData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.InternetDomainName;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.List;

public class ManageSafe {

    private List<WebData> data;
    private String jsonStr = "[]";
    private File contentDataFile = new File(System.getProperty("user.dir"), "file2"); // contentdata

    private String myPwd;

    public ManageSafe() {
        if(!contentDataFile.exists()) {
            try {
                contentDataFile.createNewFile();
                myPwd = "1234";
                toEncrypt(myPwd);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public SecretKey genSKFromPwd(String pwd) throws InvalidKeySpecException, NoSuchAlgorithmException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] salt = {-71, -33, 110, 124, 83, 90, 66, -25};
        KeySpec spec = new PBEKeySpec(pwd.toCharArray(), salt, 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }

    public void toEncrypt(String editPw) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {

        // genera chiave complessa da 256 bit (32 byte) con l'algoritmo Advanced Encryption Standard
        SecretKey secretKey = genSKFromPwd(editPw);
        // cripta il file utilizzando la chiave
        FileEncrypterDecrypter fileEncrypterDecrypter
                = new FileEncrypterDecrypter(secretKey, "AES/CBC/PKCS5Padding");
        fileEncrypterDecrypter.encrypt(this.jsonStr, contentDataFile);
    }


    public boolean toDecrypt(String loginStr) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        SecretKey originalKey = genSKFromPwd(loginStr);
        // cripta il file utilizzando la chiave presa dal file managed.txt
        FileEncrypterDecrypter fileEncrypterDecrypter
                = new FileEncrypterDecrypter(originalKey, "AES/CBC/PKCS5Padding");
        String decryptedContent = "";
        try {
            decryptedContent = fileEncrypterDecrypter.decrypt(contentDataFile);
        } catch (IOException | InvalidKeyException e) {
            return false;
        }
        // se la pwd inserita in login è corretta setto in memoria la mia pwd funzionante attuale
        myPwd = loginStr;
        // converto il mio json file decriptato in oggetti java di WebData
        ObjectMapper objectMapper = new ObjectMapper();
        List<WebData> webDatas = objectMapper.readValue(decryptedContent, new TypeReference<List<WebData>>(){});
        this.data = webDatas;
        this.jsonStr = objectMapper.writeValueAsString(webDatas);
        return true;
    }

    public void setNewDataIntoJsonStr(List<WebData> newData) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.jsonStr = objectMapper.writeValueAsString(newData);
    }

    public List<WebData> getData() {
        if(data == null)
            throw new RuntimeException("Data is empty");
        return data;
    }

    public void setData(List<WebData> data) {
        this.data = data;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public List<Acc> getAccsByWebsiteUrl(String websiteUrl) {
        for (WebData wb: data) {
            if(wb.getWebsiteUrl().equals(websiteUrl)) {
                return wb.getAccs();
            }
        }
        return null;
    }


    public boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) { // INVALID URL
            return false;
        }
    }

    public int transformURLAndAddToList(String urlString) throws URISyntaxException, NoSuchPaddingException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        String domain = InternetDomainName.from(new URI(urlString).getHost()).topPrivateDomain().toString();
        for (WebData wb : data) {
            if(wb.getWebsiteUrl().equals(domain)) { //se l'url da aggiungere esiste già
                return data.indexOf(wb);
            }
        }
        data.add(new WebData(domain));
        setNewDataIntoJsonStr(data);
        toEncrypt(myPwd);
        return data.size()-1;
    }

    public void removeWebsiteFromList(String websiteToRemove) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        for (WebData wb : data) {
            if(wb.getWebsiteUrl().equals(websiteToRemove)) {
                data.remove(wb);
                setNewDataIntoJsonStr(data);
                toEncrypt(myPwd);
                return;
            }
        }
    }

    public void removeWebsitesFromList(List<String> websitesToRemove) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        for (String str : websitesToRemove) {
            data.removeIf(wb -> wb.getWebsiteUrl().equals(str));
        }
        setNewDataIntoJsonStr(data);
        toEncrypt(myPwd);
    }

    public void addAccToSite(String websiteName, String username, String email, String pw) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        for (WebData wb : data) {
            if(wb.getWebsiteUrl().equals(websiteName)) {
                wb.getAccs().add(new Acc(username, email, pw));
                setNewDataIntoJsonStr(data);
                toEncrypt(myPwd);
                return;
            }
        }
    }

    public void removeAccFromSite(int row, String website) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        getAccsByWebsiteUrl(website).remove(row);
        setNewDataIntoJsonStr(data);
        toEncrypt(myPwd);
    }

    public String getMyPwd() {
        return myPwd;
    }

    public void setMyPwd(String myPwd) {
        this.myPwd = myPwd;
    }
}
