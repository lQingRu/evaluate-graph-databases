package com.qingru.graph.service.TigerGraph;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;

// Reference: https://github.com/tigergraph/ecosys/blob/master/tools/etl/tg-jdbc-driver/tg-jdbc-examples/src/main/java/com/tigergraph/jdbc/examples/RunQuery.java


@Service
@AllArgsConstructor
public class TigerGraphTestService {

    private DataSource dataSource;

    public void displayBuiltinsInTabularForm() throws SQLException {
        String query = "builtins stat_vertex_number";
        Connection connection = dataSource.getConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData metaData = rs.getMetaData();
            System.out.println("Table: " + metaData.getCatalogName(1));

            for (int i = 2; i <= metaData.getColumnCount(); ++i) {
                System.out.print("\t" + metaData.getColumnName(i));
            }
            System.out.println("");
            while (rs.next()) {
                System.out.print(rs.getObject(1));
                for (int i = 2; i <= metaData.getColumnCount(); ++i) {
                    Object obj = rs.getObject(i);
                    System.out.println("\t" + String.valueOf(obj));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //    CREATE QUERY findPerson(/* Parameters here */) FOR GRAPH social {
    //        result = SELECT name FROM person:name;
    //        print result;
    //    }
    public void displayAllPersonInTabularForm() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println("Running interpreted query \"run interpreted(a=?, b=?)\"...");
        String query = "run interpreted()";
        String query_body = "INTERPRET QUERY () FOR GRAPH social {\n"
                + "result = SELECT name FROM person:name;\n" + "print result;" + "}\n";
        try (java.sql.PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, query_body); // The query body is passed as a parameter.
            try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                do {
                    java.sql.ResultSetMetaData metaData = rs.getMetaData();
                    System.out.println("Table: " + metaData.getCatalogName(1));
                    System.out.print(metaData.getColumnName(1));
                    for (int i = 2; i <= metaData.getColumnCount(); ++i) {
                        System.out.print("\t" + metaData.getColumnName(i));
                    }
                    System.out.println("");
                    while (rs.next()) {
                        System.out.print(rs.getObject(1));
                        for (int i = 2; i <= metaData.getColumnCount(); ++i) {
                            Object obj = rs.getObject(i);
                            System.out.println("\t" + String.valueOf(obj));
                        }
                    }
                } while (!rs.isLast());
            }
        } catch (SQLException e) {
            System.out.println("Failed to createStatement: " + e);
        }
    }
}
