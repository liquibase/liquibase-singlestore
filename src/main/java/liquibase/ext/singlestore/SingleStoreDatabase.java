package liquibase.ext.singlestore;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import liquibase.database.DatabaseConnection;
import liquibase.database.core.MariaDBDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;

public class SingleStoreDatabase extends MariaDBDatabase {

    public static final String PRODUCT_NAME = "SingleStore";

    public SingleStoreDatabase() {
        super.sequenceNextValueFunction = null;
        super.unmodifiableDataTypes = new ArrayList<>();
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

    @Override
    protected String getMinimumVersionForFractionalDigitsForTimestamp() {
      return "6.8.0";
    }

    @Override
    public boolean supportsSequences() {
      return false;
    }

    /**
     * SingleStore can be reached via {@code jdbc:singlestore:}, {@code jdbc:mysql:}, or {@code jdbc:mariadb:} URLs.
     * Normalize to {@code jdbc:singlestore:} so all produce the same target ID.
     */
    @Override
    protected String resolveUrl(JdbcConnection jdbcConn) {
        String url = super.resolveUrl(jdbcConn);
        if (url != null) {
            url = url.replace("jdbc:mariadb:", "jdbc:singlestore:");
            url = url.replace("jdbc:mysql:", "jdbc:singlestore:");
        }
        return url;
    }

    /**
     * When connected via MySQL or MariaDB drivers, {@code getDatabaseProductName()} may return
     * "MySQL" or "MariaDB" instead of "SingleStore". Always return "SingleStore" for consistent
     * target identification.
     */
    @Override
    protected String resolveDatabaseProductName() {
        return PRODUCT_NAME;
    }
}