package kr.app.datainfo;

/**
 * Created by imjaehyun on 15. 9. 4..
 */
public class BangDataInfo {
    String _id  = "";
    String is_available = "";
    String building_name = "";
    String building_hosu = "";
    String dong = "";
    String sangse_juso = "";
    String deposit = "";
    String monthly_rental = "";
    String empty = "";
    //String price;
    String price_type = "";
    String manage_price = "";
    String bang_type = "";
    String call = "";
    String lat = "";
    String lng = "";
    String img_url = "";
    String is_favorite = "";
    boolean is_calllay_open = false;

    public void set_id(String _id){ this._id = _id; }
    public void setBuilding_name(String building_name){ this.building_name = building_name; }
    public void setBuilding_hosu(String building_hosu){ this.building_hosu = building_hosu; }
    public void setDong(String dong){ this.dong = dong; }
    public void setSangse_juso(String sangse_juso){ this.sangse_juso = sangse_juso; }
    public void setDeposit(String deposit){ this.deposit = deposit; }
    public void setMonthly_rental(String monthly_rental){ this.monthly_rental = monthly_rental; }
    public void setEmpty(String empty){ this.empty = empty; }
    public void setPrice_type(String price_type){ this.price_type = price_type; }
    public void setManage_price(String manage_price){ this.manage_price = manage_price; }
    public void setBang_type(String bang_type){ this.bang_type = bang_type; }
    public void setCall(String call){ this.call = call; }
    public void setLat(String lat){ this.lat = lat; }
    public void setLng(String lng){ this.lng = lng; }
    public void setImg_url(String img_url){ this.img_url = img_url; }
    public void setIs_favorite(String is_favorite){ this.is_favorite = is_favorite; }
    public void setIs_available(String is_available){ this.is_available = is_available; }
    public void setIs_calllay_open(boolean is_calllay_open){ this.is_calllay_open = is_calllay_open; }
    //public void setPrice(String price){ this.price = price; }

    public String get_id(){ return _id; }
    public String getBuilding_name(){ return building_name; }
    public String getBuilding_hosu(){ return building_hosu; }
    public String getDong(){ return dong; }
    public String getSangse_juso(){ return sangse_juso; }
    public String getDeposit(){ return deposit; }
    public String getMonthly_rental(){ return monthly_rental; }
    public String getEmpty(){ return empty; }
    public String getPrice_type(){ return price_type; }
    public String getManage_price(){ return manage_price; }
    public String getBang_type(){ return bang_type; }
    public String getCall(){ return call; }
    public String getLat(){ return lat; }
    public String getLng(){ return lng; }
    public String getImg_url(){ return img_url; }
    public String getIs_favorite(){ return is_favorite; }
    public String getIs_available(){ return  is_available; }
    public boolean getIs_calllay_open(){ return is_calllay_open; }
    //public String getPrice(){ return price; }

    @Override
    public boolean equals(Object o) {
        BangDataInfo bang = (BangDataInfo)o;
        if( _id.equals(bang.get_id()) ) return true;
        else return false;
    }

/*    public boolean f_equals(Object o){
        return false;
    }*/
}
