package liquibase.ext.singlestore.sqlgenerator;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AddAutoIncrementGenerator;
import liquibase.statement.core.AddAutoIncrementStatement;

public class AddAutoIncrementGeneratorSingleStore extends AddAutoIncrementGenerator {

  @Override
  public int getPriority() {
    return PRIORITY_DATABASE;
  }

  @Override
  public boolean supports(AddAutoIncrementStatement statement, Database database) {
    return super.supports(statement, database)
        && database instanceof liquibase.ext.singlestore.SingleStoreDatabase;
  }

  @Override
  public ValidationErrors validate(AddAutoIncrementStatement statement,
      Database database, SqlGeneratorChain sqlGeneratorChain) {
    return new ValidationErrors().addError("SingleStore does not support adding AUTO_INCREMENT.");
  }
}
