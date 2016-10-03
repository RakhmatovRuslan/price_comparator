package util;

/**
 * Created by RUSLAN on 18.06.2016.
 */
public enum FileURL {
    DRUGS_JSON_URL("drugs.json"),
    DRUGS_UNREGISTERED_JSON_URL("unregistered_drugs.json"),
    CONFIG_JSON_URL("config.json");

    private String value;

    FileURL(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
