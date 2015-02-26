package cn.edu.ustc.appseed.clubseed.jsondata;

import java.util.LinkedList;

/**
 * Created by gdshen on 2/6/15.
 */
public class ListPhp {
    private int error;
    private String errormessage;
    private LinkedList<ListPhpDetail> data;

    public ListPhp() {
    }

    public ListPhp(int error, String errormessage, LinkedList<ListPhpDetail> data) {
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

    public LinkedList<ListPhpDetail> getData() {
        return data;
    }

    public void setData(LinkedList<ListPhpDetail> data) {
        this.data = data;
    }

    public void appendData(LinkedList<ListPhpDetail> data) {
        this.data.addAll(data);
    }

    public void resetData(LinkedList<ListPhpDetail> data){
        this.data.clear();
        this.data = data;
    }
}
