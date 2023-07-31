package liquibase.ext.singlestore.sqlgenerator;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.RenameViewGenerator;
import liquibase.statement.core.RenameViewStatement;

public class RenameViewGeneratorSingleStore extends RenameViewGenerator {

  @Override
  public int getPriority() {
    return PRIORITY_DATABASE;
  }

  @Override
  public boolean supports(RenameViewStatement statement, Database database) {
    return super.supports(statement, database)
        && database instanceof liquibase.ext.singlestore.SingleStoreDatabase;
  }

  @Override
  public ValidationErrors validate(RenameViewStatement statement,
      Database database, SqlGeneratorChain sqlGeneratorChain) {
    return new ValidationErrors().addError("SingleStore does not support rename view statement");
  }
}
