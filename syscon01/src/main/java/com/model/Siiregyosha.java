package com.model;

public class Siiregyosha {
    private String shiireid;
    private String shiiremei;
    private String shiireaddress;
    private String shiiretel;
    private int shihonkin;
    private int nouki;

    public Siiregyosha() {}

    public Siiregyosha(String shiireid, String shiiremei, String shiireaddress, String shiiretel, int shihonkin, int nouki) {
        this.shiireid = shiireid;
        this.shiiremei = shiiremei;
        this.shiireaddress = shiireaddress;
        this.shiiretel = shiiretel;
        this.shihonkin = shihonkin;
        this.nouki = nouki;
    }

    public String getShiireid() {
        return shiireid;
    }
    public void setShiireid(String shiireid) {
        this.shiireid = shiireid;
    }
    public String getShiiremei() {
        return shiiremei;
    }
    public void setShiiremei(String shiiremei) {
        this.shiiremei = shiiremei;
    }
    public String getShiireaddress() {
        return shiireaddress;
    }
    public void setShiireaddress(String shiireaddress) {
        this.shiireaddress = shiireaddress;
    }
    public String getShiiretel() {
        return shiiretel;
    }
    public void setShiiretel(String shiiretel) {
        this.shiiretel = shiiretel;
    }
    public int getShihonkin() {
        return shihonkin;
    }
    public void setShihonkin(int shihonkin) {
        this.shihonkin = shihonkin;
    }
    public int getNouki() {
        return nouki;
    }
    public void setNouki(int nouki) {
        this.nouki = nouki;
    }
}
