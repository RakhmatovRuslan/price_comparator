package controller;

import entity.Drug;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by ruslan on 5/19/16.
 */
public class PriceComparatorController implements Initializable {

    private List<TableColumn<Map, Drug>> tableColumns;
    private List<String> columnNames;
    private Stage stage;

    @FXML
    private TableView comparedDrugsTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnNames = new ArrayList<>();
        columnNames.add("Наименование");
    }

    public void initData(HashMap<String, List<Drug>> tableData) {
        tableColumns = new ArrayList<>();
        List<Drug> suppliyersOfferedDrugs = tableData.entrySet().iterator().next().getValue();
        for (Drug suppliyersOfferedDrug : suppliyersOfferedDrugs) {
            columnNames.add(suppliyersOfferedDrug.getSuppliyerName());
        }

        tableColumns.addAll(createTableColumns(columnNames));
        tableColumns.get(0).setCellFactory(new Callback<TableColumn<Map, Drug>, TableCell<Map, Drug>>() {
            @Override
            public TableCell<Map, Drug> call(TableColumn<Map, Drug> param) {
                return new TableCell<Map, Drug>() {
                    @Override
                    protected void updateItem(Drug item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            setText(item.getName());
//                            setTextFill(Color.BLUE);
                        }
                    }
                };
            }
        });
        for (int i = 1; i < tableColumns.size(); i++) {
            tableColumns.get(i).setCellFactory(new Callback<TableColumn<Map, Drug>, TableCell<Map, Drug>>() {
                @Override
                public TableCell<Map, Drug> call(TableColumn<Map, Drug> param) {
                    return new TableCell<Map, Drug>() {
                        @Override
                        protected void updateItem(Drug item, boolean empty) {
                            super.updateItem(item, empty);
                            if (!empty) {
                                setText(String.valueOf(item.getPrice()));
                                if (item.isBestPrice() && item.getPrice() != 0){
                                    setTextFill(Color.GREEN);
                                    Button info = new Button("Инфо");
                                    info.setOnAction(e ->{
                                        FXMLLoader root = null;
                                        try {
                                            root =new  FXMLLoader(this.getClass().getClassLoader().getResource("scenes/drugInfoScene.fxml"));
                                            DrugInfoSceneController drugInfoSceneController = root.getController();
                                            drugInfoSceneController.initData(item);
                                            Scene scene = new Scene(root.load());
                                            stage.setTitle("Подробная информация");
                                            stage.setScene(scene);
                                            stage.show();
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }
                                    });
                                    setGraphic(info);
                                }
                            }
//
//                            else
//                                setTextFill(Color.RED);
                        }
                    };
                }
            });
        }
        comparedDrugsTable.getColumns().setAll(tableColumns);
        comparedDrugsTable.setItems(generateDataInMap(tableData));
    }

    public List<TableColumn<Map, Drug>> createTableColumns(List<String> columnNames) {
        List<TableColumn<Map, Drug>> tableColumns = new ArrayList<>();
        for (String columnName : columnNames) {
            TableColumn<Map, Drug> column = new TableColumn<>(columnName);
            column.setCellValueFactory(new MapValueFactory<>(columnName));
            tableColumns.add(column);
        }
        return tableColumns;
    }

    private ObservableList<Map> generateDataInMap(HashMap<String, List<Drug>> tableData) {
        ObservableList<Map> allData = FXCollections.observableArrayList();
        Map<String, Drug> dataRow = null;
        for (Map.Entry entry : tableData.entrySet()) {
            dataRow = new HashMap<>();
            dataRow.put(columnNames.get(0), new Drug((String) entry.getKey()));
            for (Drug drug : (List<Drug>) entry.getValue()) {
                dataRow.put(drug.getSuppliyerName(), drug);
            }
            allData.add(dataRow);
        }

        return allData;
    }

    public void onClickShowUnregisteredDrugs(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getClassLoader().getResource("scenes/unregisteredNomenclaturaScene.fxml"));
        Scene scene = new Scene(root);
        stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
