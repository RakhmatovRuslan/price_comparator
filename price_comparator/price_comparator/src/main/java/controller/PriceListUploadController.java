package controller;

import entity.Drug;
import entity.Supplier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.CompareDataService;
import service.TemplateParser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by ruslan on 5/18/16.
 */
public class PriceListUploadController implements Initializable{
    @FXML
    TableView<File> tblUploadFiles;
    @FXML
    TableColumn<File,String> colFileName;

    FileChooser fileChooser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colFileName.setCellValueFactory(new PropertyValueFactory<File,String>("name"));

        fileChooser = new FileChooser();
        fileChooser.setTitle("Select XLSX file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLSX files", "*.xlsx")
        );
    }

    public void uploadFileEvent(ActionEvent actionEvent) {
        File file = fileChooser.showOpenDialog(new Stage());
        tblUploadFiles.getItems().add(file);

    }

    public void deleteUploadedFileEvent(ActionEvent actionEvent) {
        int index = tblUploadFiles.getSelectionModel().getFocusedIndex();
        tblUploadFiles.getItems().remove(index);
    }

    public void showComparedDrugsEvent(ActionEvent actionEvent) throws IOException {
        List<File> files = tblUploadFiles.getItems();
        List<Supplier> suppliers = new ArrayList<>();
        Supplier supplier = null;
        for (File file : files) {
             supplier = new TemplateParser(file).getSuppliyersDrugsByTemplate();
             suppliers.add(supplier);
        }
        HashMap<String,List<Drug>> tableData = new CompareDataService().prepareDataForComparison(suppliers);
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getClassLoader().getResource("scenes/priceComparingScene.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Сравнение цен поставщиков");
        stage.setScene(scene);



        PriceComparatorController comparatorController = fxmlLoader.getController();
        comparatorController.initData(tableData);

        stage.show();
    }

    /**
     * Created by RUSLAN on 24.06.2016.
     */

}
