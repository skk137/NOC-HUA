package gr.hua.dit.noc.core.impl;

import gr.hua.dit.noc.config.RouteeProperties;
import gr.hua.dit.noc.core.SmsService;
import gr.hua.dit.noc.core.model.SendSmsRequest;
import gr.hua.dit.noc.core.model.SendSmsResult;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

// Πραγματική υλοποίηση του SmsService που αποστέλλει SMS μέσω Routee API
// Χρησιμοποιεί OAuth2 client credentials για απόκτηση access token
@Service
public class RouteeSmsService implements SmsService {

    // Logger για καταγραφή requests/responses προς την Routee
    private static final Logger LOGGER =
            LoggerFactory.getLogger(RouteeSmsService.class);

    // Endpoint για λήψη OAuth2 access token
    private static final String AUTHENTICATION_URL =
            "https://auth.routee.net/oauth/token";

    // Endpoint για αποστολή SMS μέσω Routee
    private static final String SMS_URL =
            "https://connect.routee.net/sms";

    // HTTP client για κλήσεις προς Routee
    private final RestTemplate restTemplate;

    // Ρυθμίσεις Routee (appId, appSecret, sender) από configuration properties
    private final RouteeProperties routeeProperties;

    // Constructor  των dependencies
    public RouteeSmsService(
            final RestTemplate restTemplate,
            final RouteeProperties routeeProperties
    ) {
        this.restTemplate = restTemplate;
        this.routeeProperties = routeeProperties;
    }

    // Ζητά access token από Routee με client_credentials grant
    // Η τιμή γίνεται cache ώστε να μην γίνεται token request σε κάθε SMS
    @SuppressWarnings("rawtypes")
    @Cacheable("routeeAccessToken")
    public String getAccessToken() {

        // Καταγραφή για debugging και παρακολούθηση
        LOGGER.info("Requesting Routee Access Token");

        // Δημιουργία Basic Auth header: base64(appId:appSecret)
        final String credentials =
                this.routeeProperties.getAppId() + ":" + this.routeeProperties.getAppSecret();

        final String encoded =
                Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Basic " + encoded);
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Routee token request payload (client_credentials)
        final HttpEntity<String> request =
                new HttpEntity<>("grant_type=client_credentials", httpHeaders);

        // Κλήση στο authentication endpoint και ανάκτηση access_token από το response
        final ResponseEntity<Map> response =
                this.restTemplate.exchange(
                        AUTHENTICATION_URL,
                        HttpMethod.POST,
                        request,
                        Map.class
                );

        return (String) response.getBody().get("access_token");
    }

    // Αποστολή SMS μέσω Routee
    @Override
    public SendSmsResult send(@Valid final SendSmsRequest sendSmsRequest) {

        // Έλεγχοι εγκυρότητας input
        if (sendSmsRequest == null) throw new NullPointerException();

        final String e164 = sendSmsRequest.e164();
        final String content = sendSmsRequest.content();

        if (e164 == null || e164.isBlank())
            throw new IllegalArgumentException("Phone cannot be blank");

        if (content == null || content.isBlank())
            throw new IllegalArgumentException("Content cannot be blank");

        // Απόκτηση access token (από cache ή με νέο request)
        final String token = this.getAccessToken();

        // Headers για το SMS request (Bearer token + JSON)
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + token);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // Body του SMS request σύμφωνα με το Routee API
        final Map<String, Object> body = Map.of(
                "body", content,
                "to", e164,
                "from", this.routeeProperties.getSender()
        );

        // Εκτέλεση POST request προς το Routee SMS endpoint
        final HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(body, httpHeaders);

        final ResponseEntity<String> response =
                this.restTemplate.postForEntity(SMS_URL, entity, String.class);

        // Καταγραφή response για debugging
        LOGGER.info("Routee response: {}", response);

        // Έλεγχος επιτυχίας με βάση status code
        if (!response.getStatusCode().is2xxSuccessful()) {
            LOGGER.error("SMS send to {} failed", e164);
            return new SendSmsResult(false);
        }

        return new SendSmsResult(true);
    }
}