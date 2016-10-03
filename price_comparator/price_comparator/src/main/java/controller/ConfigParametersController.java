package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import service.ConfigParamService;
import util.NumberTextField;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by RUSLAN on 18.06.2016.
 */
public class ConfigParametersController implements Initializable{
    @FXML
    private NumberTextField tfAllowedDiffPercentage;
    @FXML
    private Label lbInvPercent;
    private Properties properties;
    private ConfigParamService configParamService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // force the field to be numeric only
        configParamService = new ConfigParamService();
        properties = configParamService.getProperties();
        tfAllowedDiffPercentage.setText(properties.getProperty("allowedDiffPercentage"));
    }

    public void onClickAddProperty(ActionEvent actionEvent) {
        lbInvPercent.setText("");
        String allowedDiffPercentage = tfAllowedDiffPercentage.getText();
        try{
             Double.valueOf(allowedDiffPercentage);
             properties.setProperty("allowedDiffPercentage",allowedDiffPercentage);
            configParamService.saveProperties(properties);
            lbInvPercent.setText("Процент успешно сохранен!");
        }catch (NumberFormatException ex){
            lbInvPercent.setText("Введите только цифры!");
        } catch (IOException e) {
            lbInvPercent.setText("Ошибка при записи!");
        }
    }
}
