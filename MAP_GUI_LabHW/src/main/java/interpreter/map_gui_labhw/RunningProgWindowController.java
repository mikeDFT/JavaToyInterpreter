package interpreter.map_gui_labhw;

import Controller.Controller;
import Model.*;
import Model.ADTs.*;
import Model.Stmt.IStmt;
import Model.Type.IType;
import Model.Value.IValue;
import Repo.IRepo;
import Repo.Repo;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.util.Random;
import java.util.stream.Collectors;

public class RunningProgWindowController {
    @FXML
    private ListView<String> prgStatesListView;

    @FXML
    private TableView<HeapEntry> heapTableView;
    @FXML
    private TableColumn<HeapEntry, String> addressColumn;
    @FXML
    private TableColumn<HeapEntry, String> valueColumn;

    @FXML
    private ListView<String> outListView;
    @FXML
    private ListView<String> fileTableListView;
    @FXML
    private ListView<String> exeStackListView;

    @FXML
    private TableView<SymbolEntry> symbolTableListView;
    @FXML
    private TableColumn<SymbolEntry, String> variableColumn;
    @FXML
    private TableColumn<SymbolEntry, String> valueColumn2;

    @FXML
    private Label prgStatesCountLabel;

    IList<Controller> controllerList = new List<>();
    Controller currentController = null;

    public void initialize() {
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().getAddress());
        valueColumn.setCellValueFactory(cellData -> cellData.getValue().getValue());
        variableColumn.setCellValueFactory(cellData -> cellData.getValue().getVar());
        valueColumn2.setCellValueFactory(cellData -> cellData.getValue().getValue());


        initControllerList();
        refreshViews();
    }

    private String generateRandomString() {
        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder rndStr = new StringBuilder();
        Random rnd = new Random();
        while (rndStr.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * CHARS.length());
            rndStr.append(CHARS.charAt(index));
        }
        return rndStr.toString();
    }

    public void initControllerList() {
        String logFilePath = generateRandomString();

        int i = 1;
        for (IStmt stmt : HardcodedPrograms.hardcodedPrograms) {
            // type checker
            IDict<String, IType> typeEnv = new Dict<>();
            try {
                stmt.typecheck(typeEnv);
            } catch (MyException e) {
                System.out.println("Type checker failed for stmt " + i + ": " + e.getMessage());
                controllerList.add(null);
                i++;
                continue;
            }

            IStack<IStmt> exeStack = new Stack<>();
            IDict<String, IValue> symTable = new Dict<>();
            IList<IValue> out = new List<>();
            IDict<String, BufferedReader> fileTable = new Dict<>();
            IDict<Integer, IValue> heap = new HeapDict();

            PrgState prgState = new PrgState(exeStack, symTable, out, fileTable, heap, stmt);

            IRepo repo = new Repo(prgState, logFilePath);

            Controller controller = new Controller(repo);

            controllerList.add(controller);
            i += 1;
        }
    }

    @FXML
    public void setProgram(int index) throws MyException {
        if (controllerList.get(index) == null) {
            throw new MyException("Type checker failed");
        }

        currentController = controllerList.get(index);
        currentController.getRepo().resetPrgStates();

        PrgState firstPrgState = currentController.getRepo().getPrgList().getFirst();
        java.util.List<PrgState> newList = new java.util.ArrayList<>();
        newList.add(firstPrgState);
        currentController.getRepo().setPrgList(newList);

        refreshViews();
    }

    public void clearViews() {
        heapTableView.getItems().clear();
        outListView.getItems().clear();
        fileTableListView.getItems().clear();
        symbolTableListView.getItems().clear();
        exeStackListView.getItems().clear();
    }

    public void refreshViews() {
        clearViews();
        if (currentController == null) {
            return;
        }

        java.util.List<String> prgList = currentController.getRepo().getPrgList()
            .stream().map(PrgState::toString).toList();
        prgStatesListView.setItems(javafx.collections.FXCollections.observableList(prgList));

        prgStatesCountLabel.setText(prgStatesListView.getItems().size() + " PrgStates");

//        for (PrgState state: currentController.getRepo().getPrgList()) {
//            prgStatesListView.getItems().add(state.toString());
//        }

        showSelectedProgram();
    }

    public void updateListView() {
        java.util.List<String> prgList = currentController.getRepo().getPrgList()
            .stream().map(PrgState::toString).toList();

        prgStatesListView.setItems(javafx.collections.FXCollections.observableList(prgList));

//        System.out.println(prgList);
//        System.out.println(listViewItems);
//
//        for (String stateStr: prgList) {
//            if (!listViewItems.contains(stateStr))
//                listViewItems.add(stateStr);
//        }
//
//        for (String stateStr: listViewItems) {
//            if (!prgList.contains(stateStr))
//                listViewItems.remove(stateStr);
//        }

//        for (PrgState state: currentController.getRepo().getPrgList()) {
//            if (!prgStatesListView.getItems().contains(state.toString()))
//                prgStatesListView.getItems().add(state.toString());
//        }


        prgStatesCountLabel.setText(prgStatesListView.getItems().size() + " PrgStates");

        showSelectedProgram();
    }

    public void showSelectedProgram() {
        if (currentController == null) {
            System.out.println("No program selected");
            return;
        }

        java.util.List<PrgState> prgList = currentController.getRepo().getPrgList();

        clearViews();
        if (prgList.isEmpty()) {
            return;
        }

        PrgState state = prgList.getFirst();

        HeapDict heap = (HeapDict) state.getHeapTable();
        for (Integer key: heap.keySet()) {
            heapTableView.getItems().add(new HeapEntry(key.toString(), heap.get(key).toString()));
        }

        IList<IValue> out = state.getOut();
        for (IValue value: out) {
            outListView.getItems().add(value.toString());
        }

        IDict<String, BufferedReader> fileTable = state.getFileTable();
        for (String key: fileTable.keySet()) {
            fileTableListView.getItems().add(key);
        }

        int index = prgStatesListView.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            return;
        }
        if (index >= prgList.size()) {
            return;
        }

        state = prgList.get(index);

        IDict<String, IValue> symTable = state.getSymTable();
        for (String key: symTable.keySet()) {
            symbolTableListView.getItems().add(new SymbolEntry(key, symTable.get(key).toString()));
        }

        IStack<IStmt> exeStack = state.getExeStack();
        for (int i = exeStack.size() - 1; i >= 0; i--) {
            exeStackListView.getItems().add(exeStack.get(i).toString());
        }
    }

    public void runOneStep() {
        if (currentController == null) {
            System.out.println("No program selected");
            return;
        }

        currentController.oneStepForAll();
        updateListView();
    }

    public void runAllSteps() {
        if (currentController == null) {
            System.out.println("No program selected");
            return;
        }

        currentController.allSteps();
        updateListView();
    }
}
