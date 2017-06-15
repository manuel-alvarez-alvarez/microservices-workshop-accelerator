package es.malvarez.microservices.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * Yep... we have to configure several things ...
 */
@Component
@ConfigurationProperties(prefix = "detector")
public class DetectorSettings {

    /**
     * Unique name of the detector
     */
    @NotNull
    private String name;

    /**
     * Location of the API.
     */
    @NotNull
    private String apiHref;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApiHref() {
        return apiHref;
    }

    public void setApiHref(String apiHref) {
        this.apiHref = apiHref;
    }
}
