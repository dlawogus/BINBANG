package kr.app.datainfo;

/**
 * Created by imjaehyun on 15. 9. 9..
 */
public class NoticeDataInfo {
    private String _id;
    private String title;
    private String url;
    private String date;

    public void set_id(String _id){ this._id = _id; }
    public void setTitle(String title){ this.title = title; }
    public void setUrl(String url){ this.url = url; }
    public void setDate(String date){ this.date = date; }

    public String get_id(){ return _id; }
    public String getTitle(){ return title; }
    public String getUrl(){ return url; }
    public String getDate(){ return date; }

}
