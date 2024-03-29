package C195PA.DAO;

import C195PA.Model.ReportItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

import static C195PA.DAO.Query.getResult;
import static C195PA.DAO.Query.makeQuery;

/**
 * Provides access to the database for report creation
 * */
public class ReportDAO {

    /**
     * Queries the database for result's matching the report
     * */
    public static ObservableList<ReportItem> getAppointmentReport(){
        ObservableList<ReportItem> report = FXCollections.observableArrayList();

        String reportQuery = "select type, MONTHNAME(start) AS appointmentMonth, count(*) AS count from appointments   \n" +
                "GROUP BY type, appointmentMonth;";
        makeQuery(reportQuery);
        ResultSet results = getResult();
        try {
            while (results.next()) {
                ReportItem reportItem = new ReportItem(
                        results.getString("appointmentMonth"),
                        results.getString("type"),
                        results.getInt("count"));
                report.add(reportItem);
            }
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
        return report;
    }

}
