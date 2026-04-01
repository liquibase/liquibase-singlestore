package liquibase.ext.singlestore

import liquibase.database.jvm.JdbcConnection
import spock.lang.Specification

import java.sql.Connection
import java.sql.DatabaseMetaData

class SingleStoreDatabaseTest extends Specification {

    // ---- Target Uniqueness ----

    def "getTargetUniquenessAttributes schema is null and catalog is populated"() {
        given:
        def db = new SingleStoreDatabase()
        db.setConnection(mockSingleStoreConnection("jdbc:singlestore://host:3306/mydb", "mydb", "SingleStore"))

        when:
        def attrs = db.getTargetUniquenessAttributes()

        then:
        attrs != null
        attrs.schema == null
        attrs.catalog == "mydb"
    }

    def "resolveUrl and resolveDatabaseProductName normalize all drivers to singlestore"() {
        given:
        def db1 = new SingleStoreDatabase()
        db1.setConnection(mockSingleStoreConnection("jdbc:singlestore://host:3306/mydb", "mydb", "SingleStore"))
        def db2 = new SingleStoreDatabase()
        db2.setConnection(mockSingleStoreConnection("jdbc:mysql://host:3306/mydb", "mydb", "MySQL"))
        def db3 = new SingleStoreDatabase()
        db3.setConnection(mockSingleStoreConnection("jdbc:mariadb://host:3306/mydb", "mydb", "MariaDB"))

        when:
        def attrs1 = db1.getTargetUniquenessAttributes()
        def attrs2 = db2.getTargetUniquenessAttributes()
        def attrs3 = db3.getTargetUniquenessAttributes()

        then:
        attrs1.sanitizedUrl == "jdbc:singlestore://host:3306/mydb"
        attrs2.sanitizedUrl == "jdbc:singlestore://host:3306/mydb"
        attrs3.sanitizedUrl == "jdbc:singlestore://host:3306/mydb"
        attrs1.databaseName == "SingleStore"
        attrs2.databaseName == "SingleStore"
        attrs3.databaseName == "SingleStore"
        attrs1.targetId == attrs2.targetId
        attrs1.targetId == attrs3.targetId
    }

    def "getTargetUniquenessAttributes different databases produce different target IDs"() {
        given:
        def db1 = new SingleStoreDatabase()
        db1.setConnection(mockSingleStoreConnection("jdbc:singlestore://host:3306/orders", "orders", "SingleStore"))
        def db2 = new SingleStoreDatabase()
        db2.setConnection(mockSingleStoreConnection("jdbc:singlestore://host:3306/inventory", "inventory", "SingleStore"))

        when:
        def attrs1 = db1.getTargetUniquenessAttributes()
        def attrs2 = db2.getTargetUniquenessAttributes()

        then:
        attrs1.targetId != attrs2.targetId
    }

    private JdbcConnection mockSingleStoreConnection(String url, String catalog, String productName) {
        def conn = Mock(JdbcConnection)
        def underlying = Mock(Connection)
        def metadata = Mock(DatabaseMetaData)

        conn.getURL() >> url
        conn.getCatalog() >> catalog
        conn.getConnectionUserName() >> "testuser"
        conn.getAutoCommit() >> false
        conn.getUnderlyingConnection() >> underlying
        conn.getDatabaseProductName() >> productName
        conn.isClosed() >> false
        underlying.getSchema() >> null
        underlying.getMetaData() >> metadata
        metadata.supportsMixedCaseIdentifiers() >> false

        return conn
    }
}
