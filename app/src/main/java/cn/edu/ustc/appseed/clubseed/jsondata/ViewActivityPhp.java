package cn.edu.ustc.appseed.clubseed.jsondata;

import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Hengruo on 2015/2/6.
 */
public class ViewActivityPhp {
    private int error;
    private String errormessage;
    private ViewActivityPhpDetail data;

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

    public ViewActivityPhpDetail getData() {
        return data;
    }

    public void setData(ViewActivityPhpDetail data) {
        this.data = data;
    }

    public ViewActivityPhp(){}

    public ViewActivityPhp(int error, String errormessage, ViewActivityPhpDetail data){
        this.error = error;
        this.errormessage = errormessage;
        this.data = data;
    }
}
