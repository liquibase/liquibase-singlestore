package liquibase.ext.singlestore.sqlgenerator;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.SetTableRemarksGenerator;
import liquibase.statement.core.SetTableRemarksStatement;

public class SetTableRemarksGeneratorSingleStore extends SetTableRemarksGenerator {

  @Override
  public int getPriority() {
    return PRIORITY_DATABASE;
  }

  @Override
  public boolean supports(SetTableRemarksStatement statement, Database database) {
    return super.supports(statement, database)
        && database instanceof liquibase.ext.singlestore.SingleStoreDatabase;
  }

  @Override
  public ValidationErrors validate(SetTableRemarksStatement addForeignKeyConstraintStatement,
      Database database, SqlGeneratorChain sqlGeneratorChain) {
    return new ValidationErrors().addError("SingleStore does not support set table remarks statement");
  }
}
