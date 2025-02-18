package com.model;

public class Tabyouin {
    private String tabyouinid;
    private String tabyouinmei;
    private String tabyouinaddress;
    private String tabyouintel;
    private int tabyouinshihonkin;
    private int kyukyu;  // 1:救急, 0:非救急

    public Tabyouin() {}

    public String getTabyouinid() {
        return tabyouinid;
    }
    public void setTabyouinid(String tabyouinid) {
        this.tabyouinid = tabyouinid;
    }
    public String getTabyouinmei() {
        return tabyouinmei;
    }
    public void setTabyouinmei(String tabyouinmei) {
        this.tabyouinmei = tabyouinmei;
    }
    public String getTabyouinaddress() {
        return tabyouinaddress;
    }
    public void setTabyouinaddress(String tabyouinaddress) {
        this.tabyouinaddress = tabyouinaddress;
    }
    public String getTabyouintel() {
        return tabyouintel;
    }
    public void setTabyouintel(String tabyouintel) {
        this.tabyouintel = tabyouintel;
    }
    public int getTabyouinshihonkin() {
        return tabyouinshihonkin;
    }
    public void setTabyouinshihonkin(int tabyouinshihonkin) {
        this.tabyouinshihonkin = tabyouinshihonkin;
    }
    public int getKyukyu() {
        return kyukyu;
    }
    public void setKyukyu(int kyukyu) {
        this.kyukyu = kyukyu;
    }
}
