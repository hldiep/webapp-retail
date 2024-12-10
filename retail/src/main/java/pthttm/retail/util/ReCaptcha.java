package pthttm.retail.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "google.recaptcha.key")
public class ReCaptcha {
    private String site;
    private String secret;
    private float thresold;

    public float getThresold() {
        return thresold;
    }

    public void setThresold(float thresold) {
        this.thresold = thresold;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
