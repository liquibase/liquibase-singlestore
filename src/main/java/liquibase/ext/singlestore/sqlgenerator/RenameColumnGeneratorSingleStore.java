package liquibase.ext.singlestore.sqlgenerator;

import liquibase.database.Database;
import liquibase.ext.singlestore.SingleStoreDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.RenameColumnGenerator;
import liquibase.statement.core.RenameColumnStatement;

public class RenameColumnGeneratorSingleStore extends RenameColumnGenerator {

  @Override
  public int getPriority() {
    return PRIORITY_DATABASE;
  }

  @Override
  public boolean supports(RenameColumnStatement statement, Database database) {
    return super.supports(statement, database)
        && database instanceof liquibase.ext.singlestore.SingleStoreDatabase;
  }

  @Override
  public Sql[] generateSql(RenameColumnStatement statement, Database database,
      SqlGeneratorChain sqlGeneratorChain) {
    if (database instanceof SingleStoreDatabase) {
      String sql = "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(),
          statement.getSchemaName(), statement.getTableName()) + " CHANGE "
          + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(),
          statement.getTableName(), statement.getOldColumnName()) + " "
          + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(),
          statement.getTableName(), statement.getNewColumnName());
      return new Sql[]{
          new UnparsedSql(sql, getAffectedOldColumn(statement), getAffectedNewColumn(statement))
      };
    }
    return super.generateSql(statement, database, sqlGeneratorChain);
  }
}
