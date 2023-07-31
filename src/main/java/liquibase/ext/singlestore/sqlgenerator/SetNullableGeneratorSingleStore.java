package liquibase.ext.singlestore.sqlgenerator;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.SetNullableGenerator;
import liquibase.statement.core.SetNullableStatement;

public class SetNullableGeneratorSingleStore extends SetNullableGenerator {

  @Override
  public int getPriority() {
    return PRIORITY_DATABASE;
  }

  @Override
  public boolean supports(SetNullableStatement statement, Database database) {
    return super.supports(statement, database)
        && database instanceof liquibase.ext.singlestore.SingleStoreDatabase;
  }

  @Override
  public ValidationErrors validate(SetNullableStatement statement,
      Database database, SqlGeneratorChain sqlGeneratorChain) {
    return new ValidationErrors().addError("SingleStore does not support altering to NULL/NOT NULL.");
  }
}
