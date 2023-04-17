package liquibase.ext.singlestore;

import liquibase.database.DatabaseConnection;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SingleStoreDatabase extends MySQLDatabase {

    public static final String PRODUCT_NAME = "SingleStore";

    public SingleStoreDatabase() {

    }

    @Override
    public String getShortName() {
        return "singlestore";
    }

    @Override
    public String getDefaultDatabaseProductName() {
        return PRODUCT_NAME;
    }

    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }

    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection conn) throws DatabaseException {
        if (conn.getDatabaseProductName().equals("MySQL")) {
            try (Statement stmt = ((JdbcConnection) conn).createStatement();
                 ResultSet rs = stmt.executeQuery("select @@memsql_version")) {
                if (rs.next()) {
                    if (!rs.getString(1).isEmpty()) {
                        return true;
                    }
                }
            } catch (SQLException e) {
                return false;
            }
        }
        return PRODUCT_NAME.equalsIgnoreCase(conn.getDatabaseProductName());
    }

    @Override
    public String getDefaultDriver(String url) {
        if (url.startsWith("jdbc:singlestore")) {
            return "com.singlestore.jdbc.Driver";
        }
        return super.getDefaultDriver(url);
    }


}