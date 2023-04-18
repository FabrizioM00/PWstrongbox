package com.example.model;

import java.util.ArrayList;
import java.util.List;

public class WebData {
    private String websiteUrl;
    List<Acc> accs = new ArrayList<>();

    public WebData() {
    }

    public WebData(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public WebData(String websiteUrl, List<Acc> accs) {
        this.websiteUrl = websiteUrl;
        this.accs = accs;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public List<Acc> getAccs() {
        return accs;
    }

    public void setAccs(List<Acc> accs) {
        this.accs = accs;
    }

    public WebData addAcc(Acc acc) {
        this.accs.add(acc);
        return this;
    }

    public void addAllAcc(List <Acc> accs) {
        this.accs.addAll(accs);
    }

    @Override
    public String toString() {
        return "WebData{" +
                "websiteUrl='" + websiteUrl + '\'' +
                ", accs=" + accs +
                '}';
    }
}
