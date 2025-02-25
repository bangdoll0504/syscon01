package com.model;

public class Medicine {
    private String medicineid;
    private String medicinename;
    private String unit;

    // デフォルトコンストラクタ
    public Medicine() {
    }

    // 引数付きコンストラクタ
    public Medicine(String medicineid, String medicinename, String unit) {
        this.medicineid = medicineid;
        this.medicinename = medicinename;
        this.unit = unit;
    }

    public String getMedicineid() {
        return medicineid;
    }

    public void setMedicineid(String medicineid) {
        this.medicineid = medicineid;
    }

    public String getMedicinename() {
        return medicinename;
    }

    public void setMedicinename(String medicinename) {
        this.medicinename = medicinename;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
