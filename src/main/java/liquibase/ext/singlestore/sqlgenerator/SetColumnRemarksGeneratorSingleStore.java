package liquibase.ext.singlestore.sqlgenerator;

import liquibase.database.Database;
import liquibase.datatype.DataTypeFactory;
import liquibase.ext.singlestore.SingleStoreDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.SetColumnRemarksGenerator;
import liquibase.statement.core.SetColumnRemarksStatement;
import liquibase.util.StringUtil;

public class SetColumnRemarksGeneratorSingleStore extends SetColumnRemarksGenerator {

  @Override
  public int getPriority() {
    return PRIORITY_DATABASE;
  }

  @Override
  public boolean supports(SetColumnRemarksStatement statement, Database database) {
    return super.supports(statement, database)
        && database instanceof liquibase.ext.singlestore.SingleStoreDatabase;
  }

  @Override
  public Sql[] generateSql(SetColumnRemarksStatement statement, Database database,
      SqlGeneratorChain sqlGeneratorChain) {
    if (database instanceof SingleStoreDatabase) {
      String remarksEscaped = database.escapeStringForDatabase(
          StringUtil.trimToEmpty(statement.getRemarks()));
      String sql = "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(),
          statement.getSchemaName(), statement.getTableName()) + " MODIFY COLUMN "
          + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(),
          statement.getTableName(), statement.getColumnName()) + " " + DataTypeFactory.getInstance()
          .fromDescription(statement.getColumnDataType(), database).toDatabaseDataType(database)
          + " COMMENT '" + remarksEscaped + "'";
      return new Sql[]{new UnparsedSql(sql)};
    }
    return super.generateSql(statement, database, sqlGeneratorChain);
  }
}
