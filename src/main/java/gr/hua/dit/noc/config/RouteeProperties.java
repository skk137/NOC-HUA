package gr.hua.dit.noc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

// Configuration class για δέσμευση ρυθμίσεων από το application configuration
// Χρησιμοποιείται για παραμέτρους της εξωτερικής υπηρεσίας Routee
@Configuration
@ConfigurationProperties(prefix = "routee")
public class RouteeProperties {

    // Αναγνωριστικό εφαρμογής (app id) για το Routee API
    private String appId;

    // Μυστικό κλειδί εφαρμογής (app secret) για το Routee API
    private String appSecret;

    // Αποστολέας (sender) που εμφανίζεται στα εξερχόμενα SMS
    private String sender;

    // Getter για το appId
    public String getAppId() {
        return appId;
    }

    // Setter για το appId
    public void setAppId(String appId) {
        this.appId = appId;
    }

    // Getter για το appSecret
    public String getAppSecret() {
        return appSecret;
    }

    // Setter για το appSecret
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    // Getter για τον sender
    public String getSender() {
        return sender;
    }

    // Setter για τον sender
    public void setSender(String sender) {
        this.sender = sender;
    }
}