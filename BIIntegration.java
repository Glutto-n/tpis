import com.tableau.hyperapi.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BIIntegration {

    public static void main(String[] args) {
        Path databasePath = Paths.get("student_performance.hyper");

        try (HyperProcess hyper = new HyperProcess(Telemetry.SEND_USAGE_DATA_TO_TABLEAU);
             Connection connection = new Connection(hyper.getEndpoint(), databasePath.toString(), CreateMode.CREATE_AND_REPLACE)) {

            TableDefinition tableDefinition = new TableDefinition(new TableName("Extract", "StudentPerformance"))
                    .addColumn("StudentID", SqlType.text())
                    .addColumn("Name", SqlType.text())
                    .addColumn("PredictedGrade", SqlType.doublePrecision());

            connection.getCatalog().createTable(tableDefinition);

            List<Row> dataToInsert = new ArrayList<>();
            dataToInsert.add(new Row("1", "John Doe", 85.0));
            dataToInsert.add(new Row("2", "Jane Smith", 90.5));

            Inserter inserter = new Inserter(connection, tableDefinition);
            inserter.addRows(dataToInsert);
            inserter.execute();

            System.out.println("Data successfully inserted into Tableau Hyper file");
        } catch (HyperException e) {
            e.printStackTrace();
        }
    }
}

