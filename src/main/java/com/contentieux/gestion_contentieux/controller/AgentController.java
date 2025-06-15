package com.contentieux.gestion_contentieux.controller;

import com.contentieux.gestion_contentieux.dao.AgentDAO;
import com.contentieux.gestion_contentieux.dao.ServiceDAO;
import com.contentieux.gestion_contentieux.model.Agent;
import com.contentieux.gestion_contentieux.model.Service;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AgentController {

    @FXML private TableView<Agent> agentTableView;
    @FXML private TableColumn<Agent, String> matriculeColumn;
    @FXML private TableColumn<Agent, String> nomColumn;
    @FXML private TableColumn<Agent, String> prenomColumn;
    @FXML private TableColumn<Agent, String> gradeColumn;
    @FXML private TableColumn<Agent, String> serviceColumn;
    @FXML private TableColumn<Agent, Boolean> actifColumn;

    @FXML private TextField matriculeField;
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField gradeField;
    @FXML private ComboBox<Service> serviceComboBox;
    @FXML private CheckBox actifCheckBox;

    private final AgentDAO agentDAO = new AgentDAO();
    private final ServiceDAO serviceDAO = new ServiceDAO();
    private final ObservableList<Agent> agentList = FXCollections.observableArrayList();
    private Map<Integer, Service> serviceMap;

    @FXML
    public void initialize() {
        setupTableColumns();
        loadServices();
        loadAgents();

        agentTableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> showAgentDetails(newSelection));
    }

    private void setupTableColumns() {
        matriculeColumn.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
        actifColumn.setCellValueFactory(new PropertyValueFactory<>("actif"));

        // Pour le service, on affiche le nom du service, pas son ID
        serviceColumn.setCellValueFactory(cellData -> {
            Agent agent = cellData.getValue();
            Service service = serviceMap.get(agent.getServiceId());
            return new SimpleStringProperty(service != null ? service.getNomService() : "N/A");
        });
    }

    private void loadServices() {
        List<Service> services = serviceDAO.getAll();
        serviceComboBox.setItems(FXCollections.observableArrayList(services));
        serviceMap = services.stream().collect(Collectors.toMap(Service::getId, service -> service));
    }

    private void loadAgents() {
        agentList.setAll(agentDAO.getAll());
        agentTableView.setItems(agentList);
    }

    private void showAgentDetails(Agent agent) {
        if (agent != null) {
            matriculeField.setText(agent.getMatricule());
            nomField.setText(agent.getNom());
            prenomField.setText(agent.getPrenom());
            gradeField.setText(agent.getGrade());
            serviceComboBox.setValue(serviceMap.get(agent.getServiceId()));
            actifCheckBox.setSelected(agent.isActif());
        } else {
            clearFields();
        }
    }

    @FXML
    private void handleAdd() {
        if (isInputValid()) {
            Agent newAgent = new Agent(0, matriculeField.getText(), nomField.getText(), prenomField.getText(),
                    gradeField.getText(), serviceComboBox.getValue().getId(), actifCheckBox.isSelected());
            agentDAO.add(newAgent);
            loadAgents();
            clearFields();
        }
    }

    @FXML
    private void handleUpdate() {
        Agent selectedAgent = agentTableView.getSelectionModel().getSelectedItem();
        if (selectedAgent != null && isInputValid()) {
            selectedAgent.setMatricule(matriculeField.getText());
            selectedAgent.setNom(nomField.getText());
            selectedAgent.setPrenom(prenomField.getText());
            selectedAgent.setGrade(gradeField.getText());
            selectedAgent.setServiceId(serviceComboBox.getValue().getId());
            selectedAgent.setActif(actifCheckBox.isSelected());
            agentDAO.update(selectedAgent);
            loadAgents();
            clearFields();
        }
    }

    @FXML
    private void handleDelete() {
        Agent selectedAgent = agentTableView.getSelectionModel().getSelectedItem();
        if (selectedAgent != null) {
            agentDAO.delete(selectedAgent.getId());
            loadAgents();
            clearFields();
        }
    }

    @FXML
    private void handleClear() {
        clearFields();
    }

    private void clearFields() {
        agentTableView.getSelectionModel().clearSelection();
        matriculeField.clear();
        nomField.clear();
        prenomField.clear();
        gradeField.clear();
        serviceComboBox.getSelectionModel().clearSelection();
        actifCheckBox.setSelected(true);
    }

    private boolean isInputValid() {
        // Ajouter des validations plus poussées si nécessaire
        return !nomField.getText().trim().isEmpty() && !prenomField.getText().trim().isEmpty()
                && serviceComboBox.getValue() != null;
    }
}