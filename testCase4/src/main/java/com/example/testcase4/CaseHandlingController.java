package com.example.testcase4;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CaseHandlingController implements Initializable {

    @FXML
    private TextField JudgeId;

    @FXML
    private TextField Name;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnInsert;

    @FXML
    private TableColumn<Police_Judges,String> caseCol;

    @FXML
    private TextField caseId;

    @FXML
    private TableView<Police_Judges> judgesList;

    @FXML
    private TextField expertise;

    @FXML
    private TableColumn<Police_Judges,String> expertiseCol;

    @FXML
    private TextField filter;

    @FXML
    private TableColumn<Police_Judges,String> id_Col;

    @FXML
    private TableColumn<Police_Judges,String> nameCol;


    ObservableList<Police_Judges> JudgesList;
    //ObservableList<Criminal>List;
    JudgeConnection dc;

    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {


        loadData();


    }
    private void loadData()
    {
        dc = new JudgeConnection();
        Connection conn = JudgeConnection.Connect();
        JudgesList = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM judgeslist");
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {

                JudgesList.add(new Police_Judges(rs.getString("Judge_Id"), rs.getString("Judge_Name"), rs.getString("Expertise"), rs.getString("Case_Id")));
            }


        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }

        id_Col.setCellValueFactory(new PropertyValueFactory<>("Judge_Id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Judge_Name"));
        expertiseCol.setCellValueFactory(new PropertyValueFactory<>("Expertise"));
        caseCol.setCellValueFactory(new PropertyValueFactory<>("Case_Id"));

        //criminalList.setItems(null);

        judgesList.setItems(JudgesList);
        searchFilterData();
    }
    private void searchFilterData()
    {
        FilteredList<Police_Judges> filteredData = new FilteredList<>(JudgesList, b -> true);
        filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Police_Judges-> {
                if (newValue == null || newValue.isEmpty()||newValue.isBlank()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if(Police_Judges.getJudge_Id().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (Police_Judges.getJudge_Name().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if (Police_Judges.getExpertise().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if ((Police_Judges.getCase_Id().toLowerCase().contains(lowerCaseFilter)))
                    return true;

                else
                    return false; // Does not match.
            });
        });
        SortedList<Police_Judges> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(judgesList.comparatorProperty());
        judgesList.setItems(sortedData);
    }
    @FXML
    void DeleteFromDataBase(ActionEvent event) {


        //dc = new DbConnection();
        Connection conn = JudgeConnection.Connect();
        String sql = "delete judgeslist where Case_Id = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(3, caseId.getText());
            pst.execute();
        } catch (Exception e) {
            System.err.println("Error" + e);
        }
        loadData();
    }

    @FXML
    void InsertIntoDataBase(ActionEvent event) {
        Connection conn = JudgeConnection.Connect();
        String sql = "insert into judgeslist(Judge_Id,Judge_Name,Expertise,Case_Id)values(?,?,?,? )";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, JudgeId.getText());
            pst.setString(2, Name.getText());
            pst.setString(3, expertise.getText());
            pst.setString(4, caseId.getText());
            pst.execute();
        } catch (Exception e) {
            System.err.println("Error" + e);
        }
        loadData();

    }

}