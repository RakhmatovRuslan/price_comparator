package controller;

import entity.Drug;
import entity.Supplier;
import entity.TemplateFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.CompareDataService;
import service.TemplateParser;
import source.DrugsSource;
import util.FileURL;
import validate.SuppliersValidation;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by ruslan on 5/23/16.
 */
public class MainSceneController implements Initializable {

    private Stage stage;

    private FileChooser fileChooser;

    private static Integer numbering = 0;

    @FXML
    TableView<TemplateFile> tblUploadFiles;
    @FXML
    TableColumn<TemplateFile, String> colFileName;
    @FXML
    TableColumn<TemplateFile, Boolean> colCheck;
    @FXML
    TableColumn<TemplateFile, Integer> colNumber;
    @FXML
    private CheckBox chbAll;
    @FXML
    private Label lblFileUpload;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblFileUpload.setTextFill(Color.RED);
        colNumber.setCellValueFactory(new PropertyValueFactory<TemplateFile, Integer>("id"));
        colFileName.setCellValueFactory(new PropertyValueFactory<TemplateFile, String>("fileName"));
        colCheck.setCellValueFactory(new PropertyValueFactory<TemplateFile, Boolean>("checked"));
        colCheck.setCellFactory(param -> new CheckBoxTableCell<TemplateFile, Boolean>() {
            {
                setAlignment(Pos.CENTER);
            }
        });

        tblUploadFiles.setEditable(true);

        fileChooser = new FileChooser();
        fileChooser.setTitle("Select XLSX file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLSX files", "*.xlsx")
        );
    }

    public void openEtalonScene(ActionEvent actionEvent) throws IOException {
        stage = new Stage();
        Parent root = FXMLLoader.load(this.getClass().getClassLoader().getResource("scenes/nomenclaturaScene.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Номенклатура продуктов");
        stage.setScene(scene);
        stage.show();

    }

    public void openPriceListScene(ActionEvent actionEvent) throws IOException {
//        stage = new Stage();
//        Parent root = FXMLLoader.load(this.getClass().getClassLoader().getResource("fileUploadScene.fxml"));
//        Scene scene = new Scene(root,400,400);
//        stage.setTitle("Загрузка прайслистов");
//        stage.setScene(scene);
//        stage.show();
    }

    public void openPercentConfigScene(ActionEvent actionEvent) throws IOException {
        stage = new Stage();
        Parent root = FXMLLoader.load(this.getClass().getClassLoader().getResource("scenes/configScene.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Настройки");
        stage.setScene(scene);
        stage.show();
    }

    public void uploadFileEvent(ActionEvent actionEvent) {
        numbering++;
        lblFileUpload.setText("");
        File file = fileChooser.showOpenDialog(new Stage());
        TemplateFile templateFile = new TemplateFile(file);
        templateFile.setFileName(file.getName());
        templateFile.setId(numbering);
        tblUploadFiles.getItems().add(templateFile);

    }

    public void deleteUploadedFileEvent(ActionEvent actionEvent) {
        lblFileUpload.setText("");
        Iterator<TemplateFile> templatefiles = tblUploadFiles.getItems().iterator();
        while (templatefiles.hasNext()) {
            TemplateFile templateFile = templatefiles.next();
            if (templateFile.getChecked()) {
                templatefiles.remove();
            }
        }
        numbering = updateRowNumbering();
    }

    public void showComparedDrugsEvent(ActionEvent actionEvent) throws IOException {
        List<TemplateFile> files = tblUploadFiles.getItems();
        List<Supplier> suppliers = new ArrayList<>();
        Supplier supplier = null;
        for (TemplateFile file : files) {
            supplier = new TemplateParser(file.getTemplate()).getSuppliyersDrugsByTemplate();
            suppliers.add(supplier);
        }
        boolean isSuppliersNameValid = SuppliersValidation.isSuppliersNameValid(suppliers);
        if (isSuppliersNameValid) {
            CompareDataService compareDataService = new CompareDataService();
            HashMap<String, List<Drug>> tableData = compareDataService.prepareDataForComparison(suppliers);
            for (Map.Entry entry : tableData.entrySet()) {
                List<Drug> drugs = (List<Drug>) entry.getValue();
                if (drugs != null && drugs.size() >= 1) {
                    compareDataService.sortBestPriceOfDrug(drugs);
                }
            }
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("scenes/priceComparingScene.fxml"));

            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Сравнение цен поставщиков");
            stage.setScene(scene);

            PriceComparatorController comparatorController = fxmlLoader.getController();
            comparatorController.initData(tableData);

            //save unregistered drugs into file
            if (suppliers != null) {
                Supplier unregisteredDrugs = compareDataService.getNonExistingDrugsInEtalon(suppliers);
                Supplier nonExistingUnregisteredDrugs = compareDataService.getNonExistingDrugsInUnregisteredDrugs(unregisteredDrugs);
                new DrugsSource(FileURL.DRUGS_UNREGISTERED_JSON_URL.getValue()).addToJson(nonExistingUnregisteredDrugs);
            }
            stage.show();
        } else {
            lblFileUpload.setText("Вы добавили прайслиты одинаковых поставщиков");
        }
    }

    private Integer updateRowNumbering() {
        for (int i = 0; i < tblUploadFiles.getItems().size(); i++) {
            tblUploadFiles.getItems().get(i).setId(i + 1);
        }
        return tblUploadFiles.getItems().size();
    }

    public void openUnregisteredScene(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getClassLoader().getResource("scenes/unregisteredNomenclaturaScene.fxml"));
        Scene scene = new Scene(root);
        stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void onClickCheckAll(ActionEvent actionEvent) {
        boolean isChecked = chbAll.isSelected();
        for (TemplateFile templateFile : tblUploadFiles.getItems()) {
            templateFile.setChecked(isChecked);
        }
    }
}
