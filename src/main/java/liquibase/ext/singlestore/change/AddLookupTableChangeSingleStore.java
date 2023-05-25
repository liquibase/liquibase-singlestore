package liquibase.ext.singlestore.change;

import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.change.core.AddLookupTableChange;
import liquibase.database.Database;
import liquibase.ext.singlestore.SingleStoreDatabase;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.AddForeignKeyConstraintStatement;

import java.util.ArrayList;
import java.util.List;

@DatabaseChange(name="addLookupTable",
        description = "Creates a lookup table containing values stored in a column and creates a foreign key to the new table.",
        priority = ChangeMetaData.PRIORITY_DATABASE, appliesTo = "column")
public class AddLookupTableChangeSingleStore extends AddLookupTableChange {

    @Override
    public boolean supports(Database database) {
        return database instanceof SingleStoreDatabase;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        List<SqlStatement> returnStatements = new ArrayList<>();

        for (SqlStatement statement : super.generateStatements(database)) {
            if (statement instanceof AddForeignKeyConstraintStatement) {
                continue;
            }
            returnStatements.add(statement);
        }
        return returnStatements.toArray(new SqlStatement[0]);
    }
}
