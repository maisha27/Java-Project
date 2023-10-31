package com.example.testcase2;

public class Criminal {

    private String idcriminal_list;
    private String Criminal_name;
    private String  Crime;
    private String Code;

    public Criminal(String idcriminal_list, String criminal_name, String crime, String code) {
        this.idcriminal_list = idcriminal_list;
        Criminal_name = criminal_name;
        Crime = crime;
        Code = code;
    }

    public void setIdcriminal_list(String idcriminal_list) {
        this.idcriminal_list = idcriminal_list;
    }

    public void setCriminal_name(String criminal_name) {
        Criminal_name = criminal_name;
    }

    public void setCrime(String crime) {
        Crime = crime;
    }

    public String getIdcriminal_list() {
        return idcriminal_list;
    }

    public String getCriminal_name() {
        return Criminal_name;
    }

    public String getCrime() {
        return Crime;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

}
