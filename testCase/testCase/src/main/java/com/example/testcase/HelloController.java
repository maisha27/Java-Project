package com.example.testcase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HelloController implements Initializable {


    public Label searchLabel;

    @FXML
    private TableView<Criminal> criminalList;
    
    @FXML
    private TableColumn<Criminal,Integer> NoCol;

    @FXML
    private TableColumn<Criminal,String> nameCol;

    @FXML
    private TableColumn<Criminal,String> crimeCol;

    @FXML
    private TableColumn<Criminal,Integer> codeCol;
    @FXML
    private TextField filter;

    ObservableList<Criminal>CriminalList;
    //ObservableList<Criminal>List;
    DbConnection dc;

    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        dc = new DbConnection();
        Connection conn = DbConnection.Connect();
        CriminalList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM criminal_list " +
                    "ORDER BY " +
                    "CASE " +
                    "  WHEN Crime = 'Homicide' THEN 1 " +
                    "  WHEN Crime = 'Murder' THEN 2 " +
                    "  WHEN Crime = 'Arson' THEN 3 " +
                    "  WHEN Crime = 'Kidnapping' THEN 4 " +
                    "  WHEN Crime = 'Robbery' THEN 5 " +
                    "  ELSE 6 " +
                    "END";
            PreparedStatement ps = conn.prepareStatement(sql);
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


        FilteredList<Criminal> filteredData = new FilteredList<>(CriminalList, b -> true);
        filter.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(Criminal -> {
            if (newValue == null || newValue.isEmpty()||newValue.isBlank()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();

            if(Criminal.getIdcriminal_list().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (Criminal.getCriminal_name().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }else // Does not match.
                if (Criminal.getCrime().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }
            else return Criminal.getCode().toLowerCase().contains(lowerCaseFilter);
        }));
        SortedList<Criminal> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(criminalList.comparatorProperty());
        criminalList.setItems(sortedData);
    }




}