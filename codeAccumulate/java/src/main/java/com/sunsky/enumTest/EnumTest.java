package com.sunsky.enumTest;

public enum EnumTest {
    SUCCESS("0", "success"), CHECK_ERROR("1", "field check invalid");

    private String name;
    private String code;

    EnumTest(String code, String name) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
