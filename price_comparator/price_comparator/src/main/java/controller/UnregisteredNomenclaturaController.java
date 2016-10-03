package controller;

import entity.Drug;
import entity.Supplier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import source.DrugsSource;
import util.Converter;
import util.FileURL;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by RUSLAN on 18.06.2016.
 */
public class UnregisteredNomenclaturaController implements Initializable {
    @FXML
    private TableView<Drug> drugsTable;
    @FXML
    private TableColumn<Drug, String> colDrugName;
    @FXML
    private TableColumn<Drug, Boolean> colSelDrug;
    @FXML
    private CheckBox chbAll;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Supplier supplier = new DrugsSource(FileURL.DRUGS_UNREGISTERED_JSON_URL.getValue()).getSupplier();

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

    public void onClickAddToEtalon(ActionEvent actionEvent) {
        try {
            List<Drug> checkedDrugs = deleteFromTable();
            new DrugsSource(FileURL.DRUGS_JSON_URL.getValue()).addToJson(Converter.convetToSupplier(checkedDrugs));
            new DrugsSource(FileURL.DRUGS_UNREGISTERED_JSON_URL.getValue()).saveToJson(Converter.convetToSupplier(drugsTable.getItems()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickDeleteFromTable(ActionEvent actionEvent) {
        List<Drug> drugToRegister = drugsTable.getItems();
        deleteFromTable();
        try {
            new DrugsSource(FileURL.DRUGS_UNREGISTERED_JSON_URL.getValue()).saveToJson(Converter.convetToSupplier(drugToRegister));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Drug> deleteFromTable() {
        Iterator<Drug> iterator = drugsTable.getItems().iterator();
        List<Drug> checkedDrugs = new ArrayList<>();
        while (iterator.hasNext()) {
            Drug drug = iterator.next();
            if (drug.getChecked()) {
                iterator.remove();
                drug.setChecked(false);
                checkedDrugs.add(drug);
            }
        }
        return checkedDrugs;
    }

    public void onClickCheckAll(ActionEvent actionEvent) {
        boolean isChecked = chbAll.isSelected();
        for (Drug drug : drugsTable.getItems()) {
            drug.setChecked(isChecked);
        }
    }
}
