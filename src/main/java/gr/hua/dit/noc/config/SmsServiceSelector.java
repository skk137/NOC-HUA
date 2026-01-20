package gr.hua.dit.noc.config;

import gr.hua.dit.noc.core.SmsService;
import gr.hua.dit.noc.core.impl.MockSmsService;
import gr.hua.dit.noc.core.impl.RouteeSmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

// Configuration κλάση που επιλέγει ποια υλοποίηση SmsService θα χρησιμοποιηθεί
// Η επιλογή βασίζεται στη διαθεσιμότητα των credentials της Routee
@Configuration
public class SmsServiceSelector {

    // Logger για ενημερωτικά μηνύματα κατά την εκκίνηση
    private static final Logger LOGGER =
            LoggerFactory.getLogger(SmsServiceSelector.class);

    // Ορισμός bean SmsService με δυναμική επιλογή υλοποίησης
    @Bean
    public SmsService smsService(
            final RouteeProperties routeeProperties,
            final RouteeSmsService routeeSmsService,
            final MockSmsService mockSmsService
    ) {

        // Αν υπάρχουν διαθέσιμα credentials Routee,
        // χρησιμοποιείται η πραγματική υλοποίηση αποστολής SMS
        if (StringUtils.hasText(routeeProperties.getAppId())
                && StringUtils.hasText(routeeProperties.getAppSecret())) {

            LOGGER.info("RouteeSmsService is the default implementation of SmsService");
            return routeeSmsService;

        } else {
            // Διαφορετικά χρησιμοποιείται mock υλοποίηση (dev / test περιβάλλον)
            LOGGER.info("MockSmsService is the default implementation of SmsService");
            return mockSmsService;
        }
    }
}