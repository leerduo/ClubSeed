package cn.edu.ustc.appseed.clubseed.jsondata;

/**
 * Created by Hengruo on 2015/2/6.
 */
public class ViewActivityPhpDetail {
    String ID;
    String title;
    String clubid;
    String clubname;
    String summary;
    String content;
    String place;
    String recommend;
    String tag;
    String createtime;
    String activitytime;

    public ViewActivityPhpDetail() {
    }

    public ViewActivityPhpDetail(String ID, String title, String clubid, String clubname, String summary, String content, String place, String recommend, String tag, String createtime, String activitytime) {
        this.ID = ID;
        this.title = title;
        this.clubid = clubid;
        this.clubname = clubname;
        this.summary = summary;
        this.content = content;
        this.place = place;
        this.recommend = recommend;
        this.tag = tag;
        this.createtime = createtime;
        this.activitytime = activitytime;
    }

    public String getActivitytime() {
        return activitytime;
    }

    public void setActivitytime(String activitytime) {
        this.activitytime = activitytime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getClubname() {
        return clubname;
    }

    public void setClubname(String clubname) {
        this.clubname = clubname;
    }

    public String getClubid() {
        return clubid;
    }

    public void setClubid(String clubid) {
        this.clubid = clubid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

}
