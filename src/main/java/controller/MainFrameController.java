package controller;

import DAO.DateGoalResultDAO;
import DAO.GoalDAO;
import exception.FaultlException;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DateGoalResult;
import model.DateSelected;
import model.Goal;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class MainFrameController {
    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label pointsLabel;
    @FXML
    private Label levelLabel;
    @FXML
    private Label currentSuccessNumberLab;
    @FXML
    private Label recordSuccessNumberLab;
    @FXML
    private Label successPercentageLab;
    @FXML
    private Label monthLabel;
    @FXML
    private Label labPlus;
    @FXML
    private Label labMinus;
    @FXML
    private Button d1;
    @FXML
    private Button d2;
    @FXML
    private Button d3;
    @FXML
    private Button d4;
    @FXML
    private Button d5;
    @FXML
    private Button d6;
    @FXML
    private Button d7;
    @FXML
    private Button d8;
    @FXML
    private Button d9;
    @FXML
    private Button d10;
    @FXML
    private Button d11;
    @FXML
    private Button d12;
    @FXML
    private Button d13;
    @FXML
    private Button d14;
    @FXML
    private Button d15;
    @FXML
    private Button d16;
    @FXML
    private Button d17;
    @FXML
    private Button d18;
    @FXML
    private Button d19;
    @FXML
    private Button d20;
    @FXML
    private Button d21;
    @FXML
    private Button d22;
    @FXML
    private Button d23;
    @FXML
    private Button d24;
    @FXML
    private Button d25;
    @FXML
    private Button d26;
    @FXML
    private Button d27;
    @FXML
    private Button d28;
    @FXML
    private Button d29;
    @FXML
    private Button d30;
    @FXML
    private Button d31;
    @FXML
    private Button d32;
    @FXML
    private Button d33;
    @FXML
    private Button d34;
    @FXML
    private Button d35;
    @FXML
    private Button d36;
    @FXML
    private Button d37;
    @FXML
    private Button d38;
    @FXML
    private Button d39;
    @FXML
    private Button d40;
    @FXML
    private Button d41;
    @FXML
    private Button d42;
    @FXML
    private Button refreshBtn;
    @FXML
    private Label goalLabell;
    @FXML
    private ComboBox<String> goalCombo;
    @FXML
    private TableView<Goal> activeGoalsTable;
    @FXML
    private TableView<Goal> inactiveGoalsTable;
    @FXML
    private MenuItem deleteGoal;
    @FXML
    private MenuItem editGoal;
    @FXML
    private MenuItem activeGoal;
    @FXML
    private MenuItem inactiveGoal;
    @FXML
    private TableColumn<Goal, String> colGoalName;
    @FXML
    private TextField newGoalTextField;
    @FXML
    private Button newGoalBtn;
    @FXML
    private Label goalID;
    @FXML
    private Button reset;
    @FXML
    private TextArea textAreaText;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab inactiveTab;
    @FXML
    private Tab activeTab;

    private boolean onOffDialog = true;
    private DateSelected dateSelected;
    private List<Button> buttonList = new ArrayList<>();
    private ObjectProperty<File> fileProperty;
    private BooleanProperty changedProperty;
    private ObjectProperty<Font> fontProperty;
    private ObjectProperty<Charset> charsetProperty;
    private ObjectProperty<EOL> eolProperty;
    private Integer goalId;
    private ObservableList<Goal> goalObservableList = FXCollections.observableArrayList();
    private ObservableList<String> goalStringObservableList = FXCollections.observableArrayList();
    private StringProperty goalStringProperty = new SimpleStringProperty();
    private Points pointsField;

    public MainFrameController() throws IOException {
        this.changedProperty = new SimpleBooleanProperty(false);
        this.fontProperty = new SimpleObjectProperty<>(Font.getDefault());
        this.charsetProperty = new SimpleObjectProperty<>(StandardCharsets.UTF_8);
        this.eolProperty = new SimpleObjectProperty<>(EOL.getEOLFromString(System.getProperty("line.separator")));
        this.fileProperty = new SimpleObjectProperty<>();
        pointsField = new Points();
    }

    @FXML
    public void initialize() {
        initializeFrame();
        goalObservableList.clear();
        goalStringObservableList.clear();

        GoalDAO.getInstance().findAllInactive().forEach(e -> {
            goalObservableList.add(e);
        });

        goalObservableList.forEach(e -> {
            goalStringObservableList.add(e.getGoalName());
        });
        this.goalCombo.setItems(goalStringObservableList);
        goalStringProperty.bind(this.goalCombo.valueProperty());
        colGoalName.setCellValueFactory(new PropertyValueFactory<>("goalName"));
        activeGoalsTable.getItems().clear();

        List<Goal> activeGoalList = GoalDAO.getInstance().findAllActive();
        activeGoalsTable.getItems().addAll(activeGoalList);
        initializeDragAndDrop();
//        installEventHandler(goalCombo);

        if (eolProperty.get() == null) {
            throw new IllegalStateException("Could not determine system default end-of-line marker");
        }

        textAreaText.textProperty()
                .addListener((o) -> {
                    if (!changedProperty.get()) {
                        changedProperty.set(true);
                    }
                    if (fileProperty.get() == null
                            && textAreaText.getText().isEmpty()) {
                        changedProperty.set(false);
                    }
                });

        textAreaText.fontProperty()
                .bind(fontProperty);
        textAreaText.caretPositionProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                        int to = newValue.intValue() + 1;
                        if (to > textAreaText.getLength()) {
                            to = textAreaText.getLength();
                        }

                        String[] lines = textAreaText
                                .getText(0, to)
                                .lines()
                                .toArray(String[]::new);
                        int position = newValue.intValue() + 1;

                        if (lines.length > 1) {
                            for (int i = 0; i < lines.length - 1; i++) {
                                position -= lines[i].length() + eolProperty
                                        .get()
                                        .getSeperator()
                                        .length();
                            }
                        }
                    }
                });

        tabPane.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> ov, Tab oldTab, Tab newTab) -> {
            if (newTab == activeTab) {
                System.out.println("activeTab");
                activeGoalList.forEach(e -> {
                    goalObservableList.add(e);
                });
                goalObservableList.forEach(e -> {
                    goalStringObservableList.add(e.getGoalName());
                });
                this.goalCombo.setItems(goalStringObservableList);
                activeGoalsTable.getItems().clear();
                activeGoalsTable.getItems().addAll(activeGoalList);
            } else if (newTab == inactiveTab) {
                System.out.println("inactiveTab");
                List<Goal> inactiveGoalList = GoalDAO.getInstance().findAllInactive();
                inactiveGoalList.forEach(e -> {
                    goalObservableList.add(e);
                });
                goalObservableList.forEach(e -> {
                    goalStringObservableList.add(e.getGoalName());
                });
                this.goalCombo.setItems(goalStringObservableList);
                inactiveGoalsTable.getItems().clear();
                inactiveGoalsTable.getItems().addAll(inactiveGoalList);
            }
        });


        updatePoints();
    }

    private void updatePoints() {
        pointsLabel.setText(String.valueOf(pointsField.getPointsNumber()));
        levelLabel.setText(String.valueOf(pointsField.getLevel()));
        progressBar.setProgress((double) (pointsField.getPointsNumber() - pointsField.getA()[pointsField.getLevel() - 1]) / (pointsField.getA()[pointsField.getLevel()] - pointsField.getA()[pointsField.getLevel() - 1]));
    }

    private void initializeFrame() {
        dateSelected = new DateSelected();
        addButtons();
        setCalendar();
    }

    private void setCalendar() {
        //kasowanie poprzedniej zawartości mapy databuttom
        dateSelected.clearMapOfBtnDay();

        //wyczyszczenie poprzednich kolorow
        buttonList.forEach(e -> {
            e.setStyle(null);
        });

        int numberButtons = 42;
        String selectedMonth = dateSelected.getYearMonth().getMonth().getDisplayName(TextStyle.FULL_STANDALONE, new Locale("pl"));
        int selectedYear = dateSelected.getYearMonth().getYear();
        monthLabel.setText(selectedMonth + " " + selectedYear);

        int currentIndentation = dateSelected.getIndentation();
        int numberDaysInSelectedMonth = dateSelected.getYearMonth().lengthOfMonth();

        Map<String, LocalDate> mapOfBtnDay = new HashMap<>();
        //set for current month
        for (int day = 1, numberOfBut = currentIndentation; day <= numberDaysInSelectedMonth; day++, numberOfBut++) {
            buttonList.get(numberOfBut - 1).setText(String.valueOf(day));
            buttonList.get(numberOfBut - 1).setTextFill(Paint.valueOf("black"));
            mapOfBtnDay.put("d" + numberOfBut, LocalDate.of(selectedYear, dateSelected.getYearMonth().getMonth().getValue(), day));
        }
//        poprzednie
        int prevMonthSize = dateSelected.getYearMonth().minusMonths(1).lengthOfMonth();
        YearMonth minusMonths = dateSelected.getYearMonth().minusMonths(1);
        for (int numberOfBut = currentIndentation - 1, day = prevMonthSize; numberOfBut > 0; numberOfBut--, day--) {
            buttonList.get(numberOfBut - 1).setText(String.valueOf(day));
            buttonList.get(numberOfBut - 1).setTextFill(Paint.valueOf("gray"));
            mapOfBtnDay.put("d" + numberOfBut, LocalDate.of(minusMonths.getYear(), minusMonths.getMonth().getValue(), day));
        }
        YearMonth plusMonths = dateSelected.getYearMonth().plusMonths(1);
        for (int numberOfBut = numberDaysInSelectedMonth + currentIndentation, day = 1; numberOfBut <= buttonList.size(); numberOfBut++, day++) {
            buttonList.get(numberOfBut - 1).setText(String.valueOf(day));
            buttonList.get(numberOfBut - 1).setTextFill(Paint.valueOf("brown"));
            //zobaczymy
            mapOfBtnDay.put("d" + numberOfBut, LocalDate.of(plusMonths.getYear(), plusMonths.getMonth().getValue(), day));
//            dateSelected.addToMapOfDayBtn(LocalDate.of(year, dateSelected.getYearMonth().getMonth().getValue(), day), "d" + (x + 1));
        }

        Map<String, LocalDate> result = mapOfBtnDay.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        dateSelected.addMapOfBtnDay(result);

        //pobieranie z bazy danych i wyświetlanie
        try {
            onActionCombobox();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void decreaseMon(MouseEvent mouseEvent) {
        dateSelected.decreaseMonth();
        setCalendar();

    }

    public void increaseMon(MouseEvent mouseEvent) {
        dateSelected.increaseMonth();
        setCalendar();
    }

    @FXML
    public void clickedBtn(MouseEvent mouseEvent) {
        Node node = (Node) mouseEvent.getSource();
        if (getGoalStringProperty() != "" && getGoalStringProperty() != null) {

            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/Dialog.fxml"));
            Parent parent = null;
            try {
                parent = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(parent);

            Stage stage = new Stage();
            stage.setX(mouseEvent.getSceneX() + 100);
            stage.setY(mouseEvent.getSceneY() - 90);
            stage.setAlwaysOnTop(true);
            DialogController dialogController = loader.getController();
            dialogController.setButtonId(node.getId());
            dialogController.setMainFrameController(this);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setTitle("Wybierz odpowiednią opcję!");
            stage.show();
//        DialogController.loadView(node.getId());
        } else {

            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Błąd");
                alert.setContentText("Musisz wybrać cel!");
                alert.showAndWait();
            });
        }
    }

    public void greenBtn(String buttonId) throws IOException {
//        dateGoalResultService.findByGoal();
        for (Button x : buttonList) {
            if (x.getId().equals(buttonId)) {

                if (x.getStyle().equals("")) {
                    saveToDb(dateSelected.getMapOfBtnDay().get(buttonId), 1);
                } else {
                    updateToDb(dateSelected.getMapOfBtnDay().get(buttonId), 1);
                }
                x.setStyle("-fx-background-color: green");

            }
        }
    }

    public void redBtn(String buttonId) throws IOException {
        for (Button x : buttonList) {
            if (x.getId().equals(buttonId)) {
                if (x.getStyle().equals("")) {
                    saveToDb(dateSelected.getMapOfBtnDay().get(buttonId), 2);
                } else {
                    updateToDb(dateSelected.getMapOfBtnDay().get(buttonId), 2);
                }
                x.setStyle("-fx-background-color: red");
            }
        }
    }

    public void yellowBtn(String buttonId) throws IOException {
        for (Button x : buttonList) {
            if (x.getId().equals(buttonId)) {
                if (x.getStyle().equals("")) {
                    saveToDb(dateSelected.getMapOfBtnDay().get(buttonId), 3);
                } else {
                    updateToDb(dateSelected.getMapOfBtnDay().get(buttonId), 3);
                }
                x.setStyle("-fx-background-color: yellow");

            }
        }
    }

    public void grayBtn(String buttonId) throws IOException {
        for (Button x : buttonList) {
            if (x.getId().equals(buttonId)) {
                if (x.getStyle().equals("")) {
                    saveToDb(dateSelected.getMapOfBtnDay().get(buttonId), 4);
                } else {
                    updateToDb(dateSelected.getMapOfBtnDay().get(buttonId), 4);
                }
                x.setStyle("-fx-background-color: gray");
            }
        }
    }

    public void cancelColor(String buttonId) throws IOException {
        for (Button x : buttonList) {
            if (x.getId().equals(buttonId)) {
                deleteFromDb(dateSelected.getMapOfBtnDay().get(buttonId));
                x.setStyle(null);
            }
        }
    }

    private void addButtons() {
        buttonList.add(d1);
        buttonList.add(d2);
        buttonList.add(d3);
        buttonList.add(d4);
        buttonList.add(d5);
        buttonList.add(d6);
        buttonList.add(d7);
        buttonList.add(d8);
        buttonList.add(d9);
        buttonList.add(d10);
        buttonList.add(d11);
        buttonList.add(d12);
        buttonList.add(d13);
        buttonList.add(d14);
        buttonList.add(d15);
        buttonList.add(d16);
        buttonList.add(d17);
        buttonList.add(d18);
        buttonList.add(d19);
        buttonList.add(d20);
        buttonList.add(d21);
        buttonList.add(d22);
        buttonList.add(d23);
        buttonList.add(d24);
        buttonList.add(d25);
        buttonList.add(d26);
        buttonList.add(d27);
        buttonList.add(d28);
        buttonList.add(d29);
        buttonList.add(d30);
        buttonList.add(d31);
        buttonList.add(d32);
        buttonList.add(d33);
        buttonList.add(d34);
        buttonList.add(d35);
        buttonList.add(d36);
        buttonList.add(d37);
        buttonList.add(d38);
        buttonList.add(d39);
        buttonList.add(d40);
        buttonList.add(d41);
        buttonList.add(d42);
    }

    private void saveToDb(LocalDate date, int result) throws IOException {
        Goal goal = GoalDAO.getInstance().findByGoalName(getGoalStringProperty());
        DateGoalResultDAO.getInstance().persist(new DateGoalResult(date, goal, result));
        if (result == 1) {
            pointsField.addPoints(2);
            updatePoints();
        } else if (result == 2) {
            pointsField.subtractPoints(1);
            updatePoints();
        }

    }

    private void updateToDb(LocalDate date, int result) throws IOException {
        Goal goal = GoalDAO.getInstance().findByGoalName(getGoalStringProperty());
        List<DateGoalResult> list = DateGoalResultDAO.getInstance().findByLocalDateAndGoalId(date, goal.getId());
        if (!list.isEmpty()) {
            DateGoalResult dateGoalResult = list.get(0);
            if (dateGoalResult.getResult() == 1) {
                pointsField.subtractPoints(2);
                updatePoints();
            } else if (dateGoalResult.getResult() == 2) {
                pointsField.addPoints(1);
                updatePoints();
            }
            dateGoalResult.setResult(result);
            DateGoalResultDAO.getInstance().persist(dateGoalResult);
            if (result == 1) {
                pointsField.addPoints(2);
                updatePoints();
            } else if (result == 2) {
                pointsField.subtractPoints(1);
                updatePoints();
            }
        }
    }

    public void deleteFromDb(LocalDate date) throws IOException {
        Goal goal = GoalDAO.getInstance().findByGoalName(getGoalStringProperty());
        List<DateGoalResult> dateGoalResult = DateGoalResultDAO.getInstance().findByLocalDateAndGoalId(date, goal.getId());
        if (!dateGoalResult.isEmpty()) {
            DateGoalResult dao = dateGoalResult.get(0);
            if (dao.getResult() == 1) {
                pointsField.subtractPoints(2);
                updatePoints();
            } else if (dao.getResult() == 2) {
                pointsField.addPoints(1);
                updatePoints();
            }
            if (goal.getDateGoalResultList() != null) {
                goal.getDateGoalResultList().remove(dao);
            }
            DateGoalResultDAO.getInstance().delete(dao);
        }
    }

    public DateSelected getDateSelected() {
        return dateSelected;
    }

    public void setGoalId(Integer goalId) {
        this.goalId = goalId;
    }

    public void setCurrentSuccessNumberText(String text) {
        this.currentSuccessNumberLab.setText(text);
    }

    public void setRecordSuccessNumberText(String text) {
        this.recordSuccessNumberLab.setText(text);
    }

    public void setSuccessPercentageText(String text) {
        this.successPercentageLab.setText(text);
    }

    @FXML
    public void refreshMethod() {
        Goal goal = GoalDAO.getInstance().findByGoalName(getGoalStringProperty());

        List<DateGoalResult> allByGoalId = DateGoalResultDAO.getInstance().findAllByGoalId(goal.getId());
        statsGoalMethod(goal, allByGoalId);
    }

    @FXML
    public void goalsShortcuts(KeyEvent keyEvent) throws IOException {
        switch (keyEvent.getCode()) {
            case Q:
                previousGoal();
                onActionOpen();
                if (this.onOffDialog) dialogShourtcut();
                break;
            case A:
                nextGoal();
                onActionOpen();
                //wlaczenie dla danego dnia dialogu
                if (this.onOffDialog) dialogShourtcut();
                break;
        }
    }

    private void dialogShourtcut() {
        LocalDate now = LocalDate.now();
        String buttonId = "";
        LocalDate today = LocalDate.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        for (Map.Entry<String, LocalDate> entry : dateSelected.getMapOfBtnDay().entrySet()) {
            if (entry.getValue().equals(today)) {
                buttonId = entry.getKey();
            }
        }

        Goal goal = GoalDAO.getInstance().findByGoalName(getGoalStringProperty());
        Integer id = goal.getId();
        List<DateGoalResult> dateGoalResult = DateGoalResultDAO.getInstance().findByLocalDateAndGoalId(today, id);
        if (dateGoalResult.isEmpty()) {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/Dialog.fxml"));
            Parent parent = null;
            try {
                parent = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }


            Scene scene = new Scene(parent);

            Stage stage = new Stage();
            stage.setX(1100);
            stage.setY(100);
            stage.setAlwaysOnTop(true);

            DialogController dialogController = loader.getController();
            dialogController.setButtonId(buttonId);
            dialogController.setMainFrameController(this);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setTitle("Wybierz odpowiednią opcję!");
            stage.show();
        }
    }

    @FXML
    public void onOffDialog(ActionEvent actionEvent) {
        if (this.onOffDialog)
            this.onOffDialog = false;
        else this.onOffDialog = true;
//        progressBar.setProgress();
    }

    public void setGoalStringProperty(String goalStringProperty) {
        this.goalStringProperty.set(goalStringProperty);
    }

    public String getGoalStringProperty() {
        return goalStringProperty.get();
    }

    public StringProperty goalStringPropertyProperty() {
        return goalStringProperty;
    }

    public void initializeDragAndDrop() {
        activeGoalsTable.setRowFactory(tv -> {
            TableRow<Goal> row = new TableRow<>();

            row.setOnDragDetected(event -> {
                if (!row.isEmpty()) {
                    Integer index = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(SERIALIZED_MIME_TYPE, index);
                    db.setContent(cc);
                    event.consume();
                }
            });

            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    if (row.getIndex() != (Integer) db.getContent(SERIALIZED_MIME_TYPE)) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
                    Goal draggedGoal = activeGoalsTable.getItems().remove(draggedIndex);

                    int dropIndex;

                    if (row.isEmpty()) {
                        dropIndex = activeGoalsTable.getItems().size();
                    } else {
                        dropIndex = row.getIndex();
                    }

                    activeGoalsTable.getItems().add(dropIndex, draggedGoal);

                    event.setDropCompleted(true);
                    activeGoalsTable.getSelectionModel().select(dropIndex);
                    try {
                        changeGoalIndex(dropIndex, draggedGoal);
                    } catch (FaultlException e) {
                        e.printStackTrace();
                    }
                    event.consume();
                }
            });

            return row;
        });

        inactiveGoalsTable.setRowFactory(tv -> {
            TableRow<Goal> row = new TableRow<>();

            row.setOnDragDetected(event -> {
                if (!row.isEmpty()) {
                    Integer index = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(SERIALIZED_MIME_TYPE, index);
                    db.setContent(cc);
                    event.consume();
                }
            });

            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    if (row.getIndex() != (Integer) db.getContent(SERIALIZED_MIME_TYPE)) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
                    Goal draggedGoal = inactiveGoalsTable.getItems().remove(draggedIndex);

                    int dropIndex;

                    if (row.isEmpty()) {
                        dropIndex = inactiveGoalsTable.getItems().size();
                    } else {
                        dropIndex = row.getIndex();
                    }

                    inactiveGoalsTable.getItems().add(dropIndex, draggedGoal);

                    event.setDropCompleted(true);
                    inactiveGoalsTable.getSelectionModel().select(dropIndex);
                    try {
                        changeGoalIndex(dropIndex, draggedGoal);
                    } catch (FaultlException e) {
                        e.printStackTrace();
                    }
                    event.consume();
                }
            });

            return row;
        });

    }

    public void changeGoalIndex(int dropIndex, Goal draggedGoal) throws FaultlException {

        int goalTableIndex = draggedGoal.getSequence();
        GoalDAO.getInstance().merge(draggedGoal);
        if (goalTableIndex > dropIndex) {
            int k = goalTableIndex - 1;
            for (; k >= dropIndex; k--) {
                Goal goal1 = GoalDAO.getInstance().findBySequence(k);
                goal1.setSequence(k + 1);
                GoalDAO.getInstance().merge(goal1);
            }
            Goal goal2 = GoalDAO.getInstance().findByGoalName(draggedGoal.getGoalName());
            goal2.setSequence(dropIndex);
            GoalDAO.getInstance().merge(goal2);
        } else {
            int k = goalTableIndex + 1;
            for (; k <= dropIndex; k++) {
                Goal goal1 = GoalDAO.getInstance().findBySequence(k);
                goal1.setSequence(k - 1);
                GoalDAO.getInstance().merge(goal1);
            }
            Goal goal2 = GoalDAO.getInstance().findByGoalName(draggedGoal.getGoalName());
            goal2.setSequence(dropIndex);
            GoalDAO.getInstance().merge(goal2);
        }
    }

    public <String, LocalDate> String getKey(Map<String, LocalDate> map, LocalDate value) {
        for (Map.Entry<String, LocalDate> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void previousGoal() {
        this.goalCombo.getSelectionModel().selectPrevious();
    }

    public void nextGoal() {
        this.goalCombo.getSelectionModel().selectNext();
    }

    public void onActionCombobox() throws IOException {
        buttonList.forEach(e -> {
            e.setStyle(null);
        });
        String value = goalStringProperty.getValue();

        if (value != null) {
            goalLabell.setText(value);
            Goal goal = GoalDAO.getInstance().findByGoalName(value);
            setGoalId(goal.getId());
            List<DateGoalResult> dateGoalResultListByGoal = DateGoalResultDAO.getInstance().findAllByGoalIdAndLocalDateBetween(goal.getId(), getDateSelected().getMapOfBtnDay().get("d1"), getDateSelected().getMapOfBtnDay().get("d42"));

            //Ustawienie koloru dla danego dnia; Przejście po każdym elemencie listy DataCelRezultat; Przejście po każdym elemencie listy dat dla datego miesiąca-przycisków;Przyrównanie po datach i ustawienie odpowidniego koloru
            for (DateGoalResult element : dateGoalResultListByGoal) {
                for (LocalDate e : getDateSelected().getMapOfBtnDay().values()) {
                    if (element.getLocalDate().isEqual(e)) {
                        String key = getKey(getDateSelected().getMapOfBtnDay(), e).substring(1);
                        Button button = buttonList.get(Integer.parseInt(key) - 1);
                        switch (element.getResult()) {
                            case 1:
                                button.setStyle("-fx-background-color: green");
                                break;
                            case 2:
                                button.setStyle("-fx-background-color: red");
                                break;
                            case 3:
                                button.setStyle("-fx-background-color: yellow");
                                break;
                            case 4:
                                button.setStyle("-fx-background-color: gray");
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
            List<DateGoalResult> allDateGoalResultListByGoal = DateGoalResultDAO.getInstance().findAllByGoalId(goal.getId());
            statsGoalMethod(goal, allDateGoalResultListByGoal);
            onActionOpen();
        }
    }

    @FXML
    public void addNewGoal() {

        String newGoalText;
        Goal goal;

        if (goalID.getText() == null || goalID.getText().equals("")) {
            newGoalText = newGoalTextField.getText();
            goal = new Goal(newGoalText);
            try {
                if (newGoalText.length() <= 300) {
                    GoalDAO.getInstance().persist(goal);
                    int maxSeq = GoalDAO.getInstance().findMaxSeq();
                    goal.setSequence(maxSeq + 1);
                    GoalDAO.getInstance().merge(goal);
//                    GoalDAO.getInstance().newGoal(goal);
                    System.out.println("ID celu = " + goal.getId());
                    goalObservableList.add(goal);
                    goalStringObservableList.add(newGoalText);
                    this.goalCombo.setItems(goalStringObservableList);
                    this.inactiveGoalsTable.getItems().add(goal);
                    goalID.setText("");
                    //odkomentowac
//                    initialize();
                    newGoalTextField.setText(null);

                    File newFile = new File(goal.getId() + ".txt");
                    newFile.createNewFile();
                } else {
                    addAlert("Błąd", "Max dł. tekstu to 300 znaków!");
                }

            } catch (FaultlException e) {
                System.out.println("Nie udalo sie dodać nowego");
                addAlert("Header", "Content");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                goal = GoalDAO.getInstance().findById(Integer.parseInt(goalID.getText()));

                if (newGoalTextField.getText().length() <= 300) {
                    for (Goal e : goalObservableList) {
                        if (e.getId().equals(goalID.getText())) {
                            e.setGoalName(newGoalTextField.getText());
                            break;
                        }
                    }
                    int index = goalStringObservableList.indexOf(goal.getGoalName());
                    goalStringObservableList.set(index, newGoalTextField.getText());

                    goal.setGoalName(newGoalTextField.getText());
                    GoalDAO.getInstance().merge(goal);
                    goalID.setText("");
                    //odkomentowac
//                    initialize();
                    newGoalTextField.setText(null);
                } else {
                    addAlert("Błąd", "Max dł. tekstu to 300 znaków!");
                }
            } catch (FaultlException e) {
                System.out.println("Nie udalo sie edytować");
                addAlert("Header", "Content");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addAlert(String header, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }

    public void statsGoalMethod(Goal goal, List<DateGoalResult> dateGoalResultList) {
        displayCurrentSuccessAndSuccessRecordNumber(goal, dateGoalResultList);
        displaySuccessPercentage(goal, dateGoalResultList);
    }

    public void displayCurrentSuccessAndSuccessRecordNumber(Goal goal, List<DateGoalResult> dateGoalResultList) {
        //przejsc przez listę poczynając od ostatniego elementu i ma byc warunek
        Collections.reverse(dateGoalResultList);
        int currentSuccessNumber = 0;

        for (DateGoalResult x : dateGoalResultList) {
            if (x.getResult() == 1) {
                currentSuccessNumber++;
            } else if (x.getResult() == 2) {
                break;
            }
        }
        setCurrentSuccessNumberText(Integer.toString(currentSuccessNumber));

        int successNumber = 0;
        List<Integer> successSequenceList = new ArrayList<>();
        Iterator<DateGoalResult> iterator = dateGoalResultList.iterator();
        while (iterator.hasNext()) {
            DateGoalResult nextDateGoalResult = iterator.next();
            if (nextDateGoalResult.getResult() == 1) {
                successNumber++;
            } else if (nextDateGoalResult.getResult() == 2) {
                successSequenceList.add(successNumber);
                successNumber = 0;
            } else if (nextDateGoalResult.getResult() == 3 && nextDateGoalResult.getResult() == 4) {
            }
            if (!iterator.hasNext()) {
                successSequenceList.add(successNumber);
            }
        }
        int maxSuccessNumber = 0;
        if (successSequenceList.size() != 0) {
            maxSuccessNumber = Collections.max(successSequenceList);
        }
        setRecordSuccessNumberText(Integer.toString(maxSuccessNumber));
    }

    public void displaySuccessPercentage(Goal goal, List<DateGoalResult> dateGoalResultList) {
        int green = 0;
        int red = 0;
        int yellow = 0;
        int all = 0;
        for (DateGoalResult x : dateGoalResultList) {
            if (x.getResult() == 1)
                green++;
            else if (x.getResult() == 3)
                yellow++;
            else if (x.getResult() == 2)
                red++;
        }
        int successPercentage = (int) Math.round(((green + 0.5 * yellow) * 100) / (green + yellow + red));
        setSuccessPercentageText(Integer.toString(successPercentage) + "%");
    }

    @FXML
    public void deleteGoalMethod(ActionEvent actionEvent) {
        Goal goal = activeGoalsTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            try {
                GoalDAO.getInstance().delete(goal);
                Files.delete(Paths.get(goal.getId() + ".txt"));
                goalObservableList.clear();
                goalStringObservableList.clear();

                if (goal.isActive()) {
                    List<Goal> activeGoalList = GoalDAO.getInstance().findAllActive();
                    activeGoalList.forEach(e -> {
                        goalObservableList.add(e);
                    });
                    goalObservableList.forEach(e -> {
                        goalStringObservableList.add(e.getGoalName());
                    });
                    this.goalCombo.setItems(goalStringObservableList);
                    activeGoalsTable.getItems().clear();
                    activeGoalsTable.getItems().addAll(activeGoalList);
                } else {
                    List<Goal> inactiveGoalList = GoalDAO.getInstance().findAllInactive();
                    inactiveGoalList.forEach(e -> {
                        goalObservableList.add(e);
                    });
                    goalObservableList.forEach(e -> {
                        goalStringObservableList.add(e.getGoalName());
                    });
                    this.goalCombo.setItems(goalStringObservableList);
                    activeGoalsTable.getItems().clear();
                    activeGoalsTable.getItems().addAll(inactiveGoalList);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void editGoalMethod(ActionEvent actionEvent) {
        if (activeTab.isSelected()) {
            System.out.println("active tab is selected");
            Goal goal = activeGoalsTable.getSelectionModel().getSelectedItem();
            newGoalTextField.setText(goal.getGoalName());
            goalID.setText(Integer.toString(goal.getId()));
        } else {
            System.out.println("inactive tab is selected");
            Goal goal = inactiveGoalsTable.getSelectionModel().getSelectedItem();
            newGoalTextField.setText(goal.getGoalName());
            goalID.setText(Integer.toString(goal.getId()));
        }
    }

    @FXML
    public void reset(ActionEvent actionEvent) {
        goalID.setText(null);
        newGoalTextField.setText(null);
    }

    @FXML
    public void changeToActiveGoalMethod(ActionEvent actionEvent) {
        Goal goal = inactiveGoalsTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to change status to ACTIVE?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            goal.setActive(true);
            try {
                GoalDAO.getInstance().merge(goal);

            } catch (FaultlException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void changeToInactiveGoalMethod(ActionEvent actionEvent) {
        Goal goal = activeGoalsTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to change status to INACTIVE?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            goal.setActive(false);
            try {
                GoalDAO.getInstance().merge(goal);
            } catch (FaultlException e) {
                e.printStackTrace();
            }
        }
    }


    private static final class Key {
        private final KeyCode keyCode;
        private final BooleanProperty pressedProperty;

        public Key(final KeyCode keyCode) {
            this.keyCode = keyCode;
            this.pressedProperty = new SimpleBooleanProperty(this, "pressed");
        }

        public KeyCode getKeyCode() {
            return keyCode;
        }

        public boolean isPressed() {
            return pressedProperty.get();
        }

        public void setPressed(final boolean value) {
            pressedProperty.set(value);
        }

        public Node createNode() {
            final StackPane keyNode = new StackPane();
            keyNode.setFocusTraversable(true);
            installEventHandler(keyNode);

            final Rectangle keyBackground = new Rectangle(50, 50);
            keyBackground.fillProperty().bind(
                    Bindings.when(pressedProperty)
                            .then(Color.RED)
                            .otherwise(Bindings.when(keyNode.focusedProperty())
                                    .then(Color.LIGHTGRAY)
                                    .otherwise(Color.WHITE)));
            keyBackground.setStroke(Color.BLACK);
            keyBackground.setStrokeWidth(2);
            keyBackground.setArcWidth(12);
            keyBackground.setArcHeight(12);

            final Text keyLabel = new Text(keyCode.getName());
            keyLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

            keyNode.getChildren().addAll(keyBackground, keyLabel);

            return keyNode;
        }

        private void installEventHandler(final Node keyNode) {
            // handler for enter key press / release events, other keys are
            // handled by the parent (keyboard) node handler
            final EventHandler<KeyEvent> keyEventHandler =
                    new EventHandler<KeyEvent>() {
                        public void handle(final KeyEvent keyEvent) {
                            if (keyEvent.getCode() == KeyCode.ENTER) {
                                setPressed(keyEvent.getEventType()
                                        == KeyEvent.KEY_PRESSED);

                                keyEvent.consume();
                            }
                        }
                    };

            keyNode.setOnKeyPressed(keyEventHandler);
            keyNode.setOnKeyReleased(keyEventHandler);
        }
    }

    @FXML
    void onActionSave(ActionEvent event) {
        writeFile();
        readFile();
    }

    private void writeFile() {
        if (fileProperty.isNotNull().get()) {
            StringBuilder stringBuilder = new StringBuilder();
            textAreaText
                    .getText()
                    .lines()
                    .forEach((line) -> {
                        stringBuilder
                                .append(line)
                                .append(eolProperty
                                        .get()
                                        .getSeperator());
                    });

            try {
                Files.writeString(fileProperty
                                .get()
                                .toPath(),
                        stringBuilder,
                        StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING,
                        StandardOpenOption.WRITE);

                changedProperty.set(false);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    private void readFile() {
        if (fileProperty.isNotNull().get()) {
            try {
                byte[] readAllBytes = Files
                        .readAllBytes(fileProperty
                                .get()
                                .toPath());
                charsetProperty
                        .set(CharsetUtil
                                .determineCharset(readAllBytes));
                String text = new String(readAllBytes, charsetProperty.get());


                textAreaText
                        .textProperty()
                        .set(text);
                changedProperty.set(false);

            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    void onActionOpen() throws IOException {

        if (changedProperty.get()) {
            if (cancelClicked()) {
                return;
            }
            readFile();
        }

        //tu ma byc otwarcie pliku tekstowego, który jest w katalogu w którym znajduje się plik programu jar. Nazwa pliku bierze się od id celu
//        goal
        String path = new File(".").getCanonicalPath();
        File selectedFile = new File(path + "\\" + goalId + ".txt");

        fileProperty.set(selectedFile);
        readFile();
    }

    private boolean cancelClicked() {
        String title = "Save?";
        String contentText = String
                .format("Do you want to save changes to %s?",
                        getFileName());
        SaveDialogResult result = createDialog(title, contentText);
        if (result.equals(SaveDialogResult.SAVE)) {
            writeFile();
        }
        return result.equals(SaveDialogResult.CANCEL);
    }

    private String getFileName() {
        return fileProperty
                .isNull()
                .get()
                ? "Untitled"
                : fileProperty
                .get()
                .getAbsolutePath();
    }

    private SaveDialogResult createDialog(String title, String contentText) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText("");
        dialog.setContentText(contentText);
        ButtonType saveButtonType = new ButtonType("Save",
                ButtonBar.ButtonData.YES);
        ButtonType dontSaveButtonType = new ButtonType("Don't Save",
                ButtonBar.ButtonData.NO);
        dialog.getDialogPane()
                .getButtonTypes()
                .addAll(saveButtonType, dontSaveButtonType, ButtonType.CANCEL);
        dialog.showAndWait();
        ButtonType result = dialog.getResult();
        if (result.equals(saveButtonType)) {
            return SaveDialogResult.SAVE;
        } else if (result.equals(dontSaveButtonType)) {
            return SaveDialogResult.DONTSAVE;
        }
        return SaveDialogResult.CANCEL;
    }
}



