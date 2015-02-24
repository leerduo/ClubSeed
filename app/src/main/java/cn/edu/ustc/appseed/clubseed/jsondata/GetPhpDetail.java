package cn.edu.ustc.appseed.clubseed.jsondata;

/**
 * Created by gdshen on 2/5/15.
 */
public class GetPhpDetail {

    private String ID;
    private String PhotoURL;
    private String title;
    private String AID;
    private String URL;

    public GetPhpDetail() {
    }

    public GetPhpDetail(String id, String photoURL, String title, String aid, String url) {
        ID = id;
        PhotoURL = photoURL;
        this.title = title;
        AID = aid;
        URL = url;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPhotoURL() {
        return PhotoURL;
    }

    public void setPhotoURL(String photoURL) {
        PhotoURL = photoURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAID() {
        return AID;
    }

    public void setAID(String AID) {
        this.AID = AID;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}