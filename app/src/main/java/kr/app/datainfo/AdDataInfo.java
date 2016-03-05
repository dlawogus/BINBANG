package kr.app.datainfo;

/**
 * Created by imjaehyun on 15. 9. 8..
 */
public class AdDataInfo {
    private String _id;
    private String url;
    private String call;
    private String date;

    public void set_id(String _id){ this._id = _id; }
    public void setUrl(String url){ this.url = url; }
    public void setCall(String call){ this.call = call; }
    public void setDate(String date){ this.date = date; }

    public String get_id(){ return _id; }
    public String getUrl(){ return url; }
    public String getCall(){ return call; }
    public String getDate(){ return date; }
}
