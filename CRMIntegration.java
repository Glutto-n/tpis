import com.sforce.async.AsyncApiException;
import com.sforce.async.BulkConnection;
import com.sforce.async.CsvReader;
import com.sforce.async.JobInfo;
import com.sforce.async.OperationEnum;
import com.sforce.async.QueryResultList;
import com.sforce.async.SalesforceConnection;
import com.sforce.async.SalesforceConnectionUtil;

import java.io.IOException;

public class CRMIntegration {

    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";
    private static final String TOKEN = "your_token";

    public static void main(String[] args) {
        try {
            BulkConnection connection = SalesforceConnectionUtil.getBulkConnection(USERNAME, PASSWORD, TOKEN);

            String soql = "SELECT Id, Name, Email, ActivityDate FROM Contact WHERE ActivityDate > LAST_YEAR";
            JobInfo job = SalesforceConnectionUtil.createJob(connection, "Contact", OperationEnum.query);
            QueryResultList results = SalesforceConnectionUtil.query(connection, job, soql);

            for (String resultId : results.getResult()) {
                CsvReader csvReader = new CsvReader(resultId);
                while (csvReader.hasNextRecord()) {
                    String[] record = csvReader.nextRecord();
                    System.out.println("Record: " + record);
                    // Process each record as needed
                }
            }
        } catch (AsyncApiException | IOException e) {
            e.printStackTrace();
        }
    }
}

