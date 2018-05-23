package advprog.bot.feature.anisong.util.service;

import javax.sound.sampled.SourceDataLine;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    static Connection c;

    public static Connection datasource() throws Exception {
        String url =
                "postgres://efcwxsimifwxdq:1278577f38e02de1ac4499f900dbd5cd7d57b4d577f2e4fb5b1eaf78dd99081f@ec2" +
                        "-54-235-132-202.compute-1.amazonaws.com:5432/d6us30tomamiij";
        URI dbUri = new URI(url);
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri
                .getPath() + "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
        return DriverManager.getConnection(dbUrl, username, password);
    }

    public static void createConnection() throws Exception {
        try {
            c = datasource();
            System.out.println("Database Connected");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Statement s = c.createStatement();
        s.execute("SET SEARCH_PATH TO public");
    }

    public static void closeConnection() throws SQLException {
        c.close();
    }

    public static int addSong(String id, String title) throws Exception {
        createConnection();
        Statement s = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = s.executeQuery("SELECT * FROM Users WHERE userid = '" + id + "'" +
        "AND songname = '" + title + "'");
        rs.last();
        int count = rs.getRow();
        rs.beforeFirst();
        if (count > 0) {
            return -1;
        } else {
            s.execute(
                    String.format(
                            "INSERT INTO users VALUES ('%s','%s')",
                            id, title
                    )
            );
        }
        return 1;
    }

    public static List<String> getSong(String id) throws Exception {
        createConnection();
        Statement s = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = s.executeQuery("SELECT * FROM Users WHERE userid = '" + id + "'"
                );
        ArrayList<String> songs = new ArrayList<>();
        while(rs.next()) {
            songs.add(rs.getString(2));
        }
        return songs;
    }

    public static void deleteSong(String id, String title) throws Exception {
        createConnection();
        Statement s = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        s.execute("DELETE FROM Users WHERE userid = '" + id + "'" +
                "AND songname = '" + title + "'");

    }


//    public static void main(String[] args) throws Exception {
//        createConnection();
//        addSong("test","Sukutomo");
//        List<String> list = getSong("test");
//        System.out.println(list);
//    }


}
