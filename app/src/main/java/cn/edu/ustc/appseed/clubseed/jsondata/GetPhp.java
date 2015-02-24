package cn.edu.ustc.appseed.clubseed.jsondata;

import java.util.LinkedList;

public class GetPhp {
    private int error;
    private String errormessage;
    private LinkedList<GetPhpDetail> data;

    public GetPhp() {
    }

    public GetPhp(int error, String errormessage, LinkedList<GetPhpDetail> data) {
        this.error = error;
        this.errormessage = errormessage;
        this.data = data;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }

    public LinkedList<GetPhpDetail> getData() {
        return data;
    }

    public void setData(LinkedList<GetPhpDetail> data) {
        this.data = data;
    }
}
