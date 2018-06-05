package cmcciot.onenet.nbapi.sdk.entity;

import cmcciot.onenet.nbapi.sdk.config.Config;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by zhuocongbin
 * date 2018/3/16
 */
public class Device extends CommonEntity{
    // 璁惧鍚嶇О锛屽瓧绗﹀拰鏁板瓧缁勬垚鐨勫瓧绗︿覆锛屽繀濉弬鏁�
    private String title;
    // 璁惧鎻忚堪淇℃伅锛屽彲濉弬鏁�
    private String desc;
    // 璁惧鏍囩锛屽彲濉弬鏁�
    private List<String> tags;
    // 璁惧鎺ュ叆鍗忚锛岃繖閲屾寚瀹氫负: LWM2M锛屽繀濉弬鏁�
    private String protocol;
    // 璁惧鍦扮悊浣嶇疆锛屾牸寮忎负锛歿"lon": 106, "lat": 29, "ele": 370}锛屽彲濉弬鏁�
    private JSONObject location;
    // 璁惧IMSI锛屽繀濉弬鏁�
    private String imsi;
    // 璁惧鎺ュ叆骞冲彴鏄惁鍚敤鑷姩璁㈤槄鍔熻兘锛屽彲濉弬鏁�
    private Boolean obsv;
    // 鍏朵粬淇℃伅锛屽彲濉弬鏁�
    private JSONObject other;

    /**
     * @param title锛屾湁瀛楃鎴栬�呮暟瀛楃粍鎴愶紝蹇呭～
     * @param imei锛岃姹傚湪OneNET骞冲彴鍞竴锛屽繀濉�
     * @param imsi锛屽繀濉�
     */
    public Device(String title, String imei, String imsi) {
        this.title = title;
        this.imei = imei;
        this.imsi = imsi;
        this.protocol = "LWM2M";
    }

    public void setObsv(Boolean obsv) {
        this.obsv = obsv;
    }

    public void setOther(JSONObject other) {
        this.other = other;
    }

    public void setLocation(JSONObject location) {
        this.location = location;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", this.title);
        if (null != this.desc && !"".equals(this.desc)) {
            jsonObject.put("desc", this.desc);
        }
        if (null != this.tags && !this.tags.isEmpty()) {
            jsonObject.put("tags", this.tags);
        }
        jsonObject.put("protocol", this.protocol);
        if (this.location != null) {
            jsonObject.put("location", this.location);
        }
        JSONObject authInfo = new JSONObject();
        authInfo.put(imei, imsi);
        jsonObject.put("auth_info", authInfo);
        if (this.obsv != null) {
            jsonObject.put("obsv", this.obsv);
        }
        if (this.other != null) {
            jsonObject.put("other", this.other);
        }

        return jsonObject;
    }
    @Override
    public String toUrl() {
        StringBuilder url = new StringBuilder(Config.getDomainName());
        url.append("/devices");
        return url.toString();
    }
}
