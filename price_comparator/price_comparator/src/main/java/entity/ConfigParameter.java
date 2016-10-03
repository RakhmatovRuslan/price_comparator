package entity;

/**
 * Created by RUSLAN on 18.06.2016.
 */
public class ConfigParameter {
    private Double allowedDiffPercentage;

    public ConfigParameter() {
    }

    public ConfigParameter(Double allowedDiffPercentage) {
        this.allowedDiffPercentage = allowedDiffPercentage;
    }

    public Double getAllowedDiffPercentage() {
        return allowedDiffPercentage;
    }

    public void setAllowedDiffPercentage(Double allowedDiffPercentage) {
        this.allowedDiffPercentage = allowedDiffPercentage;
    }
}
