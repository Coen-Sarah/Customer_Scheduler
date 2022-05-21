package C195PA.Controller;

import C195PA.Model.ReportItem;
import C195PA.Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.ResourceBundle;

import static C195PA.DAO.ReportDAO.getAppointmentReport;

/**
 * Provides creation and control of the reports page
 * */
public class ReportController extends ApplicationController implements Initializable {

    public TableView appointmentReport;
    public TableColumn appointmentType;
    public TableColumn appointmentMonth;
    public TableColumn appointmentCount;

    public TableView contactOneTable;
    public TableColumn oneAppointmentId;
    public TableColumn oneTitle;
    public TableColumn oneDescription;
    public TableColumn oneType;
    public TableColumn oneCustomerId;
    public TableColumn oneStart;
    public TableColumn oneEnd;

    public TableView contactTwoTable;
    public TableColumn twoAppointmentId;
    public TableColumn twoTitle;
    public TableColumn twoDescription;
    public TableColumn twoType;
    public TableColumn twoCustomerId;
    public TableColumn twoStart;
    public TableColumn twoEnd;

    public TableView contactThreeTable;
    public TableColumn threeAppointmentId;
    public TableColumn threeTitle;
    public TableColumn threeDescription;
    public TableColumn threeType;
    public TableColumn threeCustomerId;
    public TableColumn threeStart;
    public TableColumn threeEnd;

    public TableView canceledAppointments;
    public TableColumn appointmentId;
    public TableColumn appointmentTitle;
    public TableColumn appointmentDescription;
    public TableColumn startTime;
    public TableColumn endTime;
    public TableColumn customerId;
    public TableColumn contactId;
    public TableColumn userId;

    public ObservableList<ReportItem> reportItems = FXCollections.observableArrayList();
    public ObservableList<Appointment> canceledAppointmentsList = FXCollections.observableArrayList();

    /**
     * Initializes the Report page
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateHeader();
        initializeAppointmentReport();
        initializeContactSchedule();
        getCanceledAppointments();

    }

    /**
     * Initializes the first appointment report
     * */
    private void initializeAppointmentReport() {
        reportItems = getAppointmentReport();
        System.out.println(reportItems.get(0).getMonth());
        appointmentReport.setItems(reportItems);
        appointmentMonth.setCellValueFactory( new PropertyValueFactory<>("month"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appointmentCount.setCellValueFactory(new PropertyValueFactory<>("count"));

    }

    /**
     * Initializes the contact schedules
     * */
    private void initializeContactSchedule() {
        ObservableList<Appointment> contactOneSchedule = FXCollections.observableArrayList();
        ObservableList<Appointment> contactTwoSchedule = FXCollections.observableArrayList();
        ObservableList<Appointment> contactThreeSchedule = FXCollections.observableArrayList();

        for(int i = 0; i < allAppointments.size(); i++){
            if(allAppointments.get(i).getContactId() == 1){
                contactOneSchedule.add(allAppointments.get(i));
            }else if(allAppointments.get(i).getContactId() == 2){
                contactTwoSchedule.add(allAppointments.get(i));
            }else{
                contactThreeSchedule.add(allAppointments.get(i));
            }
        }

        contactOneTable.setItems(contactOneSchedule);
        oneAppointmentId.setCellValueFactory(new PropertyValueFactory("appointmentId"));
        oneTitle.setCellValueFactory(new PropertyValueFactory("title"));
        oneDescription.setCellValueFactory(new PropertyValueFactory("description"));
        oneType.setCellValueFactory(new PropertyValueFactory("type"));
        oneCustomerId.setCellValueFactory(new PropertyValueFactory("customerId"));
        oneStart.setCellValueFactory(new PropertyValueFactory("startTime"));
        oneEnd.setCellValueFactory(new PropertyValueFactory("endTime"));
        
        contactTwoTable.setItems(contactTwoSchedule);
        twoAppointmentId.setCellValueFactory(new PropertyValueFactory("appointmentId"));
        twoTitle.setCellValueFactory(new PropertyValueFactory("title"));
        twoDescription.setCellValueFactory(new PropertyValueFactory("description"));
        twoType.setCellValueFactory(new PropertyValueFactory("type"));
        twoCustomerId.setCellValueFactory(new PropertyValueFactory("customerId"));
        twoStart.setCellValueFactory(new PropertyValueFactory("startTime"));
        twoEnd.setCellValueFactory(new PropertyValueFactory("endTime"));

        contactThreeTable.setItems(contactThreeSchedule);
        threeAppointmentId.setCellValueFactory(new PropertyValueFactory("appointmentId"));
        threeTitle.setCellValueFactory(new PropertyValueFactory("title"));
        threeDescription.setCellValueFactory(new PropertyValueFactory("description"));
        threeType.setCellValueFactory(new PropertyValueFactory("type"));
        threeCustomerId.setCellValueFactory(new PropertyValueFactory("customerId"));
        threeStart.setCellValueFactory(new PropertyValueFactory("startTime"));
        threeEnd.setCellValueFactory(new PropertyValueFactory("endTime"));
        

    }

    /**
     * Retrieves the canceled appointments information
     * */
    private void getCanceledAppointments() {

        try{
            File file = new File("canceledAppointments.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while(line.length() != 0){
             System.out.println(Arrays.toString(line.split("::")));
             String appointmentTextArray[] = line.split("::");
                System.out.println("In report");
             canceledAppointmentsList.add(new Appointment(
                     Integer.parseInt(appointmentTextArray[3]),
                     appointmentTextArray[1],
                     appointmentTextArray[5],
                     "None",
                     "None",
                     LocalDateTime.parse(appointmentTextArray[7]),
                     LocalDateTime.parse(appointmentTextArray[9]),
                     Integer.parseInt(appointmentTextArray[11]),
                     Integer.parseInt(appointmentTextArray[13]),
                     Integer.parseInt(appointmentTextArray[15])
             ));
             line = reader.readLine();

            }

            canceledAppointments.setItems(canceledAppointmentsList);
            appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            contactId.setCellValueFactory(new PropertyValueFactory<>("contactId"));
            userId.setCellValueFactory(new PropertyValueFactory<>("userId"));

        }catch (IOException ioe){
            ioe.printStackTrace();
        }

    }

}
