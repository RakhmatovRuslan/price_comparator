package controller;

import entity.Drug;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class DrugInfoSceneController implements Initializable {
    @FXML
    private Label lbSupplierName;
    @FXML
    private Label lbDrugName;
    @FXML
    private Label lbDrugPrice;
    @FXML
    private Label lbDrugExpireDate;
    @FXML
    private Label lbProducerName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lbSupplierName.setTextFill(Color.GREEN);
        lbDrugPrice.setTextFill(Color.GREEN);
        lbProducerName.setTextFill(Color.GREEN);
        lbDrugExpireDate.setTextFill(Color.GREEN);
        lbDrugName.setTextFill(Color.GREEN);
    }

    public void initData(Drug drug) {
        lbSupplierName.setText(drug.getSuppliyerName());
        lbDrugName.setText(drug.getName());
        lbDrugPrice.setText(String.valueOf(drug.getPrice()));
        lbProducerName.setText(drug.getProducer());
        lbDrugExpireDate.setText(drug.getExpireDate().toString());
    }
}