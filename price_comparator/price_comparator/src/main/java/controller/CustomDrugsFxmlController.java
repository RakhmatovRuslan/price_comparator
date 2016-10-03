package controller;

import entity.Drug;
import entity.Supplier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import source.DrugsSource;
import util.FileURL;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by ruslan on 5/14/16.
 */
public class CustomDrugsFxmlController implements Initializable {
    DrugsSource drugsSource;

    @FXML
    private TableView<Drug> drugsTable;
    @FXML
    private TableColumn<Drug, String> colDrugName;
    @FXML
    private TableColumn<Drug, Boolean> colSelDrug;
    @FXML
    private TextField drugNameTxt;
    @FXML
    private Label message;
    @FXML
    private Label saveEtalonStatus;
    @FXML
    private CheckBox chbAll;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        drugsSource = new DrugsSource(FileURL.DRUGS_JSON_URL.getValue());
        Supplier supplier = drugsSource.getSupplier();

        colDrugName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSelDrug.setCellValueFactory(new PropertyValueFactory<>("checked"));
        colSelDrug.setCellFactory(param -> new CheckBoxTableCell<Drug, Boolean>() {
            {
                setAlignment(Pos.CENTER);
            }

        });
        drugsTable.setEditable(true);
        drugsTable.getItems().setAll(supplier);
    }

    public void addCustomProduct(ActionEvent actionEvent) {
        String drugName = drugNameTxt.getText();
        if (drugName != null && !drugName.equals("")) {
            drugsTable.getItems().add(new Drug(drugName));
            drugNameTxt.clear();
            message.setText("");
            if (saveEtalonStatus.getText() != null || !saveEtalonStatus.getText().equals(""))
                saveEtalonStatus.setText("");
        } else {
            message.setText("Пожалуйста введите название");
        }
    }

    public void deleteCustomProduct(ActionEvent actionEvent) {
        Iterator<Drug> drugs = drugsTable.getItems().iterator();
        while (drugs.hasNext()) {
            Drug drug = drugs.next();
            if (drug.getChecked()) {
                drugs.remove();
            }
        }
        if (saveEtalonStatus.getText() != null || !saveEtalonStatus.getText().equals(""))
            saveEtalonStatus.setText("");
    }

    public void saveCustomProductsIntoFile(ActionEvent actionEvent) {
        List<Drug> nomenclatures = drugsTable.getItems();
        try {
            Supplier supplier = new Supplier();
            for (Drug drug : nomenclatures) {
                supplier.add(drug);
            }
            drugsSource.saveToJson(supplier);
            saveEtalonStatus.setText("Вы успешно сохранили номенклатуру!");
        } catch (IOException e) {
            message.setText("Error occurred while saving drugs into file");
            saveEtalonStatus.setText("Произошла ошибка при сохранении номенклатуры");
        }
    }

    public void onClickCheckAll(ActionEvent actionEvent) {
        boolean isChecked = chbAll.isSelected();
        for (Drug drug : drugsTable.getItems()) {
            drug.setChecked(isChecked);
        }
    }


}
