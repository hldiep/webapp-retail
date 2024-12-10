package pthttm.retail.model;

public class TokenInfo {
    String uid;
    String phone;

    public TokenInfo(String uid, String phone) {
        this.uid = uid;
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
