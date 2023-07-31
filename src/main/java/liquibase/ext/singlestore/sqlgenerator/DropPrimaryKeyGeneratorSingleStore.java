package liquibase.ext.singlestore.sqlgenerator;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.DropPrimaryKeyGenerator;
import liquibase.statement.core.DropPrimaryKeyStatement;

public class DropPrimaryKeyGeneratorSingleStore extends DropPrimaryKeyGenerator {

  @Override
  public int getPriority() {
    return PRIORITY_DATABASE;
  }

  @Override
  public boolean supports(DropPrimaryKeyStatement statement, Database database) {
    return super.supports(statement, database)
        && database instanceof liquibase.ext.singlestore.SingleStoreDatabase;
  }

  @Override
  public ValidationErrors validate(DropPrimaryKeyStatement dropPrimaryKeyStatement,
      Database database, SqlGeneratorChain sqlGeneratorChain) {
    return new ValidationErrors().addError("SingleStore does not support altering primary key.");
  }
}
