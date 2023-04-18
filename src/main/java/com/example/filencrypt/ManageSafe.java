package com.example.filencrypt;

import com.example.model.Acc;
import com.example.model.WebData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.InternetDomainName;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

public class ManageSafe {

    private List<WebData> data;
    private String jsonStr = "[]";

    private File keysFile = new File(System.getProperty("user.dir"), "file1"); // keys
    private File contentDataFile = new File(System.getProperty("user.dir"), "file2"); // contentdata

    public ManageSafe() {
        if(!keysFile.exists()) {
            try {
                keysFile.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(keysFile));
                writer.write("a!!a");
                writer.close();
                genNewPw("1234");
                toEncrypt();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if(!contentDataFile.exists()) {
            try {
                contentDataFile.createNewFile();
                toEncrypt();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setNewDataIntoJsonStr(List<WebData> newData) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.jsonStr = objectMapper.writeValueAsString(newData);
    }

    public void genNewPw(String plainpw) throws IOException { //scrive su file
        String hashedPassword = BCrypt.hashpw(plainpw, BCrypt.gensalt(12));
        new WriteNontraductKey().writeHiddenMasterKey(hashedPassword, keysFile);
    }

    public boolean checkPw(String plainpw) throws IOException { //legge da file
        ReadNonTraductKey rntk = new ReadNonTraductKey();
        return BCrypt.checkpw(plainpw, rntk.translateSafe(new WriteNontraductKey().getMST(), keysFile, true));
    }

    public void toEncrypt() throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {

        // genera chiave complessa da 256 bit (32 byte) con l'algoritmo Advanced Encryption Standard
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey secretKey = keyGenerator.generateKey();
        // scrive la chiave salvandola nel file keys spezzettandola in una stringa random
        new WriteNontraductKey().writeHiddenContentKey(Base64.getEncoder().encodeToString(secretKey.getEncoded()), keysFile);

        // cripta il file utilizzando la chiave
        FileEncrypterDecrypter fileEncrypterDecrypter
                = new FileEncrypterDecrypter(secretKey, "AES/CBC/PKCS5Padding");
        fileEncrypterDecrypter.encrypt(this.jsonStr, contentDataFile);
    }


    public void toDecrypt() throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
        // leggo e traduco la chiave spezzettata dal file managed.txt salvato
        ReadNonTraductKey rntk = new ReadNonTraductKey();
        String traductKey = rntk.translateSafe(new WriteNontraductKey().getCNT(), keysFile, false);
        // converto la stringa della chiave in una secret key
        byte[] decodedKey = Base64.getDecoder().decode(traductKey);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        // cripta il file utilizzando la chiave presa dal file managed.txt
        FileEncrypterDecrypter fileEncrypterDecrypter
                = new FileEncrypterDecrypter(originalKey, "AES/CBC/PKCS5Padding");
        String decryptedContent = fileEncrypterDecrypter.decrypt(contentDataFile);
        // converto il mio json file decriptato in oggetti java di WebData
        ObjectMapper objectMapper = new ObjectMapper();
        List<WebData> webDatas = objectMapper.readValue(decryptedContent, new TypeReference<List<WebData>>(){});
        this.data = webDatas;
        this.jsonStr = objectMapper.writeValueAsString(webDatas);
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

    public int transformURLAndAddToList(String urlString) throws URISyntaxException, NoSuchPaddingException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        String domain = InternetDomainName.from(new URI(urlString).getHost()).topPrivateDomain().toString();
        for (WebData wb : data) {
            if(wb.getWebsiteUrl().equals(domain)) { //se l'url da aggiungere esiste già
                return data.indexOf(wb);
            }
        }
        data.add(new WebData(domain));
        setNewDataIntoJsonStr(data);
        toEncrypt();
        return data.size()-1;
    }

    public void removeWebsiteFromList(String websiteToRemove) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        for (WebData wb : data) {
            if(wb.getWebsiteUrl().equals(websiteToRemove)) {
                data.remove(wb);
                setNewDataIntoJsonStr(data);
                toEncrypt();
                return;
            }
        }
    }

    public void removeWebsitesFromList(List<String> websitesToRemove) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        for (String str : websitesToRemove) {
            data.removeIf(wb -> wb.getWebsiteUrl().equals(str));
        }
        setNewDataIntoJsonStr(data);
        toEncrypt();
    }

    public void addAccToSite(String websiteName, String username, String email, String pw) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        for (WebData wb : data) {
            if(wb.getWebsiteUrl().equals(websiteName)) {
                wb.getAccs().add(new Acc(username, email, pw));
                setNewDataIntoJsonStr(data);
                toEncrypt();
                return;
            }
        }
    }

    public void removeAccFromSite(int row, String website) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        getAccsByWebsiteUrl(website).remove(row);
        setNewDataIntoJsonStr(data);
        toEncrypt();
    }

}
