package com.example.testcase2;

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

public class HelloController implements Initializable {

    @FXML
    private TextField Id;

    @FXML
    private TextField Name;

    @FXML
    private TableColumn<Criminal,String> NoCol;

    public Button btnDelete;

    public Button btnInsert;

    public Button btnUpdate;

    @FXML
    private TextField code;

    @FXML
    private TableColumn<Criminal,String> codeCol;

    @FXML
    private TextField crime;
    @FXML
    private TextField filter;

    @FXML
    private TableColumn<Criminal,String> crimeCol;

    @FXML
    private TableColumn<Criminal,String> nameCol;
    @FXML
    private TableView<Criminal> criminalList;

    ObservableList<Criminal> CriminalList;
    //ObservableList<Criminal>List;
    DbConnection dc;

    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {


        loadData();


    }
    private void loadData()
    {
        dc = new DbConnection();
        Connection conn = DbConnection.Connect();
        CriminalList = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM criminal_list");
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {

                CriminalList.add(new Criminal(rs.getString("idcriminal_list"), rs.getString("Criminal_name"), rs.getString("Crime"), rs.getString("Code")));
            }


        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }

        NoCol.setCellValueFactory(new PropertyValueFactory<>("idcriminal_list"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Criminal_name"));
        crimeCol.setCellValueFactory(new PropertyValueFactory<>("Crime"));
        codeCol.setCellValueFactory(new PropertyValueFactory<>("Code"));

        //criminalList.setItems(null);

        criminalList.setItems(CriminalList);
        searchFilterData();
    }
    private void searchFilterData()
    {
        FilteredList<Criminal> filteredData = new FilteredList<>(CriminalList, b -> true);
        filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Criminal -> {
                if (newValue == null || newValue.isEmpty()||newValue.isBlank()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if(Criminal.getIdcriminal_list().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (Criminal.getCriminal_name().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if (Criminal.getCrime().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if ((Criminal.getCode().toLowerCase().contains(lowerCaseFilter)))
                    return true;

                else
                    return false; // Does not match.
            });
        });
        SortedList<Criminal> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(criminalList.comparatorProperty());
        criminalList.setItems(sortedData);
    }
    @FXML
    void DeleteFromDataBase(ActionEvent event) {


        //dc = new DbConnection();
        Connection conn = DbConnection.Connect();
        String sql = "delete from criminal_list where Criminal_name = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, Name.getText());
            pst.execute();
        } catch (Exception e) {
            System.err.println("Error" + e);
        }
    }

    @FXML
    void InsertIntoDataBase(ActionEvent event) {
        Connection conn = DbConnection.Connect();
        String sql = "insert into criminal_list(idcriminal_list,Criminal_name,Crime,Code)values(?,?,?,? )";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, Id.getText());
            pst.setString(2, Name.getText());
            pst.setString(3, crime.getText());
            pst.setString(4, code.getText());
            pst.execute();
        } catch (Exception e) {
            System.err.println("Error" + e);
        }
    }

    @FXML
    void UpdateTheTableView(ActionEvent event) {

        loadData();

    }
}