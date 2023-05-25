package liquibase.ext.singlestore.sqlgenerator;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AddForeignKeyConstraintGenerator;
import liquibase.sqlgenerator.core.DropForeignKeyConstraintGenerator;
import liquibase.statement.core.AddForeignKeyConstraintStatement;
import liquibase.statement.core.DropForeignKeyConstraintStatement;

public class DropForeignKeyConstraintGeneratorSingleStore extends DropForeignKeyConstraintGenerator {

    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }

    @Override
    public boolean supports(DropForeignKeyConstraintStatement statement, Database database) {
        return super.supports(statement, database) && database instanceof liquibase.ext.singlestore.SingleStoreDatabase;
    }

    @Override
    public ValidationErrors validate(DropForeignKeyConstraintStatement addForeignKeyConstraintStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        return new ValidationErrors().addError("SingleStore does not support foreign keys and referential integrity");
    }
}
