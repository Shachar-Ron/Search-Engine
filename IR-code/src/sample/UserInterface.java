package sample;

import Model.CorpusStracture.CorpusDictenory;
import Model.CorpusStracture.InfoTerms;
import Model.Exeptions.*;
import Model.Indexer.UnPackingInvertedIndex;
import Model.ReadFile.ReadFile;
import Model.Searcher.Entities;
import Model.Searcher.Searcher;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.File;
import java.io.PrintWriter;
import java.util.*;

/**
 * Class that building the GUI
 */
public class UserInterface {
    public static String path = "";
    public static String path2 = "";
    String pathSaveResults ="";
    String query = "";

    public static CheckBox cb1;
    public static CheckBox cb2;
    public static CheckBox cb3;
    public static CheckBox cb4;

    private static UserInterface ourInstance = new UserInterface();

    private static Stage primaryStage;

    public static UserInterface getInstance() {
        return ourInstance;
    }

    public void init(Stage primaryStage) {
        this.primaryStage = primaryStage;
        cb1 = new CheckBox();
        cb2 = new CheckBox();
        cb3 = new CheckBox();
        cb4 = new CheckBox();
    }
    /**
     *Building the UI
     * @return Scene that includes the UI for IR
     */
    public Scene BuildInterface() {
        VBox root = new VBox();
        addtoVBOXFirstOption(root);
        addtoVBOXSecOption(root);
        addCheckBox(root);
        addFourButtons(root);
        addSearch(root);
        addSearchPath(root);
        addCheckBoxForSave(root);
        addCheckBoxForSemmantic(root);
        Scene scene = new Scene(root, 350, 1000);
        return scene;
    }

    private void addtoVBOXFirstOption(VBox hBox) {
        Label label = new Label("Corpus path :");
        TextField tf = new TextField();
        tf.setMaxWidth(300);
        Button btn = new Button("Browse");
        btn.setOnAction(e ->
        {
            DirectoryChooser file = new DirectoryChooser();
            file.setTitle("Open File");
            File f = file.showDialog(primaryStage);
            tf.setText(f.toString());
            path = f.toString();
        });
        hBox.setSpacing(20);
        hBox.getChildren().addAll(label, tf, btn);
    }

    private void addtoVBOXSecOption(VBox hBox) {
        Label label = new Label("Indexer path :");
        TextField tf = new TextField();
        Button btn = new Button("Browse");
        tf.setMaxWidth(300);
        btn.setOnAction(e ->
        {
            DirectoryChooser file = new DirectoryChooser();
            file.setTitle("Open File");
            File f = file.showDialog(primaryStage);
            tf.setText(f.toString());
            path2 = f.toString();
        });
        hBox.setSpacing(20);
        hBox.getChildren().addAll(label, tf, btn);
    }

    private void addCheckBox(VBox hBox) {
        //A checkbox without a caption
        cb1.setText("Stemming");
        hBox.getChildren().addAll(cb1);
    }

    private void addFourButtons(VBox hBox) {
        HBox hBox1 = new HBox();
        hBox1.setSpacing(20);
        Button start = new Button("Run Corpus");
        start.setOnAction(e ->
        {
            try {
                long a = System.nanoTime();
                boolean withStem = cb1.isSelected();
                if(path.equals(""))
                    throw new NoCorpusPathExeption();
                if(path2.equals(""))
                    throw new NoIndexerPathInsertedExeption();
                ReadFile readFile = new ReadFile(path2, withStem);
                readFile.readFile(path, withStem);
                String str = "";
                if (withStem)
                    str = "Stem";
                UnPackingInvertedIndex.UnPackFile(path2, str);
                long b = System.nanoTime();
                CorpusDictenory corpusDictenory = CorpusDictenory.getInstance();
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setHeaderText("Time Of process :" + ((b - a) * 0.000000001) + "sec\n" +
                           "Number of Articles:" + readFile.getNumOfArticls() + "\n" +
                        "Number of unique terms : " + corpusDictenory.finalDic.size());
                info.showAndWait();
            } catch (NoCorpusPathExeption E1){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("you dont entered corpus path");
                errorAlert.showAndWait();
            }
            catch (NoIndexerPathInsertedExeption E1){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("you dont entered indexer path");
                errorAlert.showAndWait();
            }

            catch (Exception ex) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText(ex.toString());
                errorAlert.showAndWait();
            }
        });

        Button reset = new Button("Reset");
        reset.setOnAction(e ->
        {
            try {
                CorpusDictenory corpusDictenory = CorpusDictenory.getInstance();
                corpusDictenory.reset();
                File f = new File(path2);
                for (File file : f.listFiles())
                    if (!file.isDirectory())
                        file.delete();
            }
            catch (Exception e1){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("There are nothing in the path to delete");
                errorAlert.showAndWait();
            }
        });

        Button Dic = new Button("Show Dictionary");
        Dic.setOnAction(e ->
        {
            try {
                CorpusDictenory temp = CorpusDictenory.getInstance();
                TreeMap<String, String> mapTable = temp.finalDic;
                if (mapTable.size() == 0)
                    throw new NoDictenoryLoadedExeption();
                TreeMap<String, String> table1 = new TreeMap();
                for (Map.Entry<String, String> entry : mapTable.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    String[] str = value.split("\\|");
                    table1.put(key, str[0]);
                }
                TableView<Map.Entry<String, String>> table = new TableView();
                Stage stage = new Stage();
                Scene scene = new Scene(new Group());
                stage.setTitle("Table View Sample");
                stage.setWidth(300);
                stage.setHeight(500);

                table.getItems().addAll(table1.entrySet());

                TableColumn<Map.Entry<String, String>, String> column1 = new TableColumn<>("Term");
                column1.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getKey()));

                TableColumn<Map.Entry<String, String>, String> column2 = new TableColumn<>("Number of Appearance");
                column2.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getValue()));


                table.getColumns().addAll(column1, column2);

                final VBox vbox = new VBox();
                vbox.setSpacing(5);
                vbox.setPadding(new Insets(10, 0, 0, 10));
                vbox.getChildren().addAll(table);

                ((Group) scene.getRoot()).getChildren().addAll(vbox);

                stage.setScene(scene);
                stage.show();
            }
            catch (NoDictenoryLoadedExeption E1){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("you not run corpus or not loaded a Dictionary");
                errorAlert.showAndWait();
            }
            catch (Exception E){
                System.out.println("a");
            }
        });

        Button load = new Button("Load Dictionary");
        load.setOnAction(e ->
        {
            try {
                CorpusDictenory corpusDictenory = CorpusDictenory.getInstance();
                boolean withStem = cb1.isSelected();
                if (path2.equals(""))
                    throw new NoIndexerPathInsertedExeption();
                corpusDictenory.loadDic(path2, withStem);
                if(corpusDictenory.finalDic.size()==0)
                    throw new NoDicInTheFileExeptionExeption();
                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setHeaderText("Finish To Load");
                errorAlert.showAndWait();
            }
            catch (NoDicInTheFileExeptionExeption e1){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("There are no appropriate dictionary in this path");
                errorAlert.showAndWait();
            }
            catch (NoIndexerPathInsertedExeption e1){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("you not gave indexer path");
                errorAlert.showAndWait();
            }

        });
        hBox.getChildren().addAll(start, reset, Dic, load);
    }

    private void addSearch(VBox hBox) {
        Label label = new Label("Enter Query :");
        TextField tf = new TextField();
        Button btn = new Button("Search");
        tf.setMaxWidth(300);
        btn.setOnAction(e ->
        {
            try {
                try {

                    if(cb3.isSelected()&&pathSaveResults.equals("")) {
                        throw new NoPathToSaveResultsExeption();
                    }
                    query = tf.getText();
                    boolean withStemming = cb1.isSelected();
                    boolean withSemantic = cb2.isSelected();
                    boolean withSabing = cb3.isSelected();
                    CorpusDictenory corpusDictenory = CorpusDictenory.getInstance();
                    if (corpusDictenory.finalDic.size() == 0) {
                        throw new NoDictenoryLoadedExeption();
                    }
                    Searcher searcher = new Searcher(withSemantic, withStemming);

                    if (path.equals(""))
                        throw new NoCorpusPathExeption();
                    if (query.equals(""))
                        throw new EmptyTextFieldExeption();

                    TreeMap<Integer, String[]> output = searcher.getTop50ByQueryFile(true, query, path);
                    TreeMap<String, String> table1 = new TreeMap();
                    String toShow ="";
                    for (Map.Entry<Integer, String[]> entry : output.entrySet()) {
                        Integer key = entry.getKey();
                        String[] valueArray = entry.getValue();
                        String value = "";

                        for (String str : valueArray) {
                            value = value + str + "\n";
                            toShow = toShow + str + "\n";
                        }
                        table1.put("" + key, value);
                    }

                    TableView<Map.Entry<String, String>> table = new TableView();
                    Stage stage = new Stage();
                    Scene scene = new Scene(new Group());
                    stage.setTitle("Table View Sample");
                    stage.setWidth(300);
                    stage.setHeight(700);

                    table.getItems().addAll(table1.entrySet());

                    TableColumn<Map.Entry<String, String>, String> column1 = new TableColumn<>("QeuryID");
                    column1.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getKey()));

                    TableColumn<Map.Entry<String, String>, String> column2 = new TableColumn<>("Results");
                    column2.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getValue()));

                    table.getColumns().addAll(column1, column2);
                    addButtonToTable(table, stage);
                    final VBox vbox = new VBox();
                    vbox.setSpacing(5);
                    vbox.setPadding(new Insets(10, 0, 0, 10));
                    vbox.getChildren().addAll(table);

                    ((Group) scene.getRoot()).getChildren().addAll(vbox);

                    stage.setScene(scene);
                    stage.show();
                    if (withSabing) {
                        try {
                            String str="";
                            if(cb1.isSelected())
                                str="Stem";
                            PrintWriter writer = new PrintWriter(pathSaveResults+"\\Results"+str+".txt", "UTF-8");
                            Iterator it = output.entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry pair = (Map.Entry) it.next();
                                int num = (int) pair.getKey();
                                String[] results = (String[]) pair.getValue();
                                for (String a : results) {
                                    writer.println(num + " 0 " + a + " 1" + " 42.38" + " mt");
                                }
                                it.remove(); // avoids a ConcurrentModificationException
                            }

                            writer.close();
                        } catch (Exception e1) {
                        }

                    }
                }catch (EmptyTextFieldExeption e2){
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("you dont entered query");
                    errorAlert.showAndWait();
                }
                catch (NoCorpusPathExeption e2){
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Enter please corpus path where there is the file with the name '05 stop_words.txt'");
                    errorAlert.showAndWait();
                }
            }
            catch (NoDictenoryLoadedExeption e1){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("you not run corpus or not loaded a Dictionary");
                errorAlert.showAndWait();
            }
            catch (NoPathToSaveResultsExeption e1){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("you not entered path to save the results");
                errorAlert.showAndWait();
            }
            catch (Exception e1){
                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setHeaderText(e1.getMessage());
                errorAlert.showAndWait();
            }
        });
        hBox.setSpacing(20);
        hBox.getChildren().addAll(label, tf, btn);
    }

    private void addSearchPath(VBox hBox) {
        Label label = new Label("Query path :");
        TextField tf = new TextField();
        tf.setMaxWidth(300);
        Button btn = new Button("Browse&Search");
        btn.setOnAction(e ->
        {
            try {
                if (path.equals(""))
                    throw new NoCorpusPathExeption();
                CorpusDictenory corpusDictenory = CorpusDictenory.getInstance();
                if(corpusDictenory.finalDic.size()==0)
                    throw new NoDictenoryLoadedExeption();
                if(cb3.isSelected()&&pathSaveResults.equals(""))
                    throw new NoPathToSaveResultsExeption();
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                File f = fileChooser.showOpenDialog(primaryStage);
                tf.setText(f.toString());
                String path = f.toString();
                boolean withStemming = cb1.isSelected();
                boolean withSemantic = cb2.isSelected();
                boolean withSabing = cb3.isSelected();
                Searcher searcher = new Searcher(withSemantic, withStemming);
                TreeMap<Integer, String[]> output = searcher.getTop50ByQueryFile(false,path,this.path);
                TreeMap<String, String> table1 = new TreeMap();
                for (Map.Entry<Integer, String[]> entry : output.entrySet()) {
                    Integer key = entry.getKey();
                    String[] valueArray = entry.getValue();
                    String value = "";
                    for (String str : valueArray)
                        value = value + str + "\n";
                    table1.put("" + key, value);
                }
                TableView<Map.Entry<String, String>> table = new TableView();
                Stage stage = new Stage();
                Scene scene = new Scene(new Group());
                stage.setTitle("Table View Sample");
                stage.setWidth(300);
                stage.setHeight(700);

                table.getItems().addAll(table1.entrySet());

                TableColumn<Map.Entry<String, String>, String> column1 = new TableColumn<>("QeuryID");
                column1.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getKey()));

                TableColumn<Map.Entry<String, String>, String> column2 = new TableColumn<>("Results");
                column2.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getValue()));


                table.getColumns().addAll(column1, column2);
                addButtonToTable(table, stage);

                final VBox vbox = new VBox();
                vbox.setSpacing(5);
                vbox.setPadding(new Insets(10, 0, 0, 10));
                vbox.getChildren().addAll(table);

                ((Group) scene.getRoot()).getChildren().addAll(vbox);

                stage.setScene(scene);
                stage.show();
                if (withSabing) {
                    try {
                        String str ="";
                        if(cb1.isSelected())
                            str="Stem";
                        PrintWriter writer = new PrintWriter(pathSaveResults+"\\Results"+str+".txt", "UTF-8");
                        Iterator it = output.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry pair = (Map.Entry) it.next();
                            int num = (int) pair.getKey();
                            String[] results = (String[]) pair.getValue();
                            for (String a : results) {
                                writer.println(num + " 0 " + a + " 1" + " 42.38" + " mt");
                            }
                            it.remove(); // avoids a ConcurrentModificationException
                        }

                        writer.close();
                    } catch (Exception e1) {

                    }

                }
                System.out.println("finish");
            } catch (NoCorpusPathExeption exeption){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Enter please corpus path where there is the file with the name '05 stop_words.txt'");
                errorAlert.showAndWait();
            }
            catch (NoDictenoryLoadedExeption e1){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("you not run corpus or not loaded a Dictionary");
                errorAlert.showAndWait();
            }
            catch (NoPathToSaveResultsExeption e1){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("you not entered path to save the results");
                errorAlert.showAndWait();
            }
        });
        hBox.setSpacing(20);
        hBox.getChildren().addAll(label, tf, btn);
    }

    private void addCheckBoxForSemmantic(VBox hBox) {
        //A checkbox without a caption
        cb2.setText("Use Offline Semantic");
        hBox.getChildren().addAll(cb2);
        cb4.setText("Use Online Semantic");
        hBox.getChildren().addAll(cb4);
    }

    private void addCheckBoxForSave(VBox hBox) {
        //A checkbox without a caption
        cb3.setText("Save The Results");
        hBox.getChildren().addAll(cb3);
        Label label = new Label("Saving path :");
        TextField tf = new TextField("");
        tf.setMaxWidth(300);
        Button btn = new Button("Save");
        btn.setOnAction(e ->
        {
            DirectoryChooser file = new DirectoryChooser();
            file.setTitle("Open File");
            File f = file.showDialog(primaryStage);
            tf.setText(f.toString());
            pathSaveResults = f.toString();
        });
        hBox.setSpacing(20);
        hBox.getChildren().addAll(label, tf, btn);
    }

    private void addButtonToTable(TableView<Map.Entry<String, String>> table, Stage stage1) {
        TableColumn<Map.Entry<String, String>, Void> colBtn = new TableColumn("Search Entities");

        Callback<TableColumn<Map.Entry<String, String>, Void>, TableCell<Map.Entry<String, String>, Void>> cellFactory = new Callback<TableColumn<Map.Entry<String, String>, Void>, TableCell<Map.Entry<String, String>, Void>>() {
            @Override
            public TableCell<Map.Entry<String, String>, Void> call(final TableColumn<Map.Entry<String, String>, Void> param) {
                final TableCell<Map.Entry<String, String>, Void> cell = new TableCell<Map.Entry<String, String>, Void>() {

                    private final Button btn = new Button("Search Entities ");

                    {
                        Stage stage = new Stage();
                        Scene scene = new Scene(new Group());
                        btn.setOnAction((ActionEvent event) -> {
                            Map.Entry<String, String> data = getTableView().getItems().get(getIndex());
                            //        System.out.println("selectedData: " + data);
                            stage.setTitle("Tableview with button column");
                            stage.setWidth(600);
                            stage.setHeight(600);
                            String[] strings = data.getValue().split("\n");
                            TableView<String> table = new TableView<>();
                            Collection<String> list = new ArrayList<>();
                            for (String str : strings) {
                                list.add(str);
                            }
                            ObservableList<String> details = FXCollections.observableArrayList(list);

                            TableColumn<String, String> col1 = new TableColumn<>();
                            table.getColumns().addAll(col1);

                            col1.setCellValueFactory(data1 -> new SimpleStringProperty(data1.getValue()));
                            table.setItems(details);
                            addButtonToSubTable(table);

                            final VBox vbox = new VBox();
                            vbox.setSpacing(5);
                            vbox.setPadding(new Insets(10, 0, 0, 10));
                            vbox.getChildren().addAll(table);

                            ((Group) scene.getRoot()).getChildren().addAll(vbox);

                            stage.setScene(scene);
                            stage.show();

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        table.getColumns().add(colBtn);
    }

    private void addButtonToSubTable(TableView<String> table) {
        TableColumn<String, Void> colBtn = new TableColumn("Button Column");

        Callback<TableColumn<String, Void>, TableCell<String, Void>> cellFactory = new Callback<TableColumn<String, Void>, TableCell<String, Void>>() {
            @Override
            public TableCell<String, Void> call(final TableColumn<String, Void> param) {
                final TableCell<String, Void> cell = new TableCell<String, Void>() {
                    private final Button btn = new Button("Search Top 5 Entities");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            String path = UserInterface.path2;
                            String data = getTableView().getItems().get(getIndex());
                            String name = InfoTerms.getInstance().getUnConvert(data);
                            Entities entities = new Entities(path, name);
                            String[] output = entities.get5EntitiesForEachDoc();
                            String toShow ="";
                            int i =1 ;
                            for(String str : output){
                                if(str==null)
                                    continue;
                                toShow = toShow + i+" - "  +str + "\n";
                                i++;
                            }
                            Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                            errorAlert.setHeaderText(toShow);
                            errorAlert.showAndWait();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);
        table.getColumns().add(colBtn);

    }
}


