package server;

import com.fasterxml.jackson.core.JsonProcessingException;
import lib.commands.User;
import lib.organization.Organization;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.PriorityQueue;

public class Database {
    private final String url;
    private final String user;
    private final String password;
    private final DatabaseUtil databaseUtil;

//    private Connection connection;
    private static BasicDataSource ds = new BasicDataSource(); //пул конекшенов

    public Database(String url,
                    String user,
                    String password,
                    DatabaseUtil databaseUtil) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;
        this.databaseUtil = databaseUtil;
        ds.setUrl(url);
        ds.setUsername(user);
        ds.setPassword(password);
        ds.setMinIdle(5);
        ds.setMaxIdle(20);
        ds.setMaxOpenPreparedStatements(100);
        establishConnection();
    }

    private void establishConnection() throws SQLException {
        System.out.println("establishConnection");

//        connection = DriverManager.getConnection(url, user, password);
    }
    public synchronized Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public PriorityQueue<Organization> readAllOrganizations() {
        System.out.println("readAllOrganizations");
        PriorityQueue<Organization> collection = new PriorityQueue<>();

        try (Statement statement = getConnection().createStatement()) {
            String sql = "select * from collection;";

            try (ResultSet rs = statement.executeQuery(sql)) {
                while (rs.next()) {
                    collection.add(databaseUtil.readOrganization(rs));
                }
            }

            return collection;
        } catch (SQLException | JsonProcessingException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    private int nextIdInSequence() throws SQLException {
        System.out.println("nextIdInSequence");
        try (Statement statement = getConnection().createStatement()) {
            String sql = "select nextval('collection_id_seq');";

            try (ResultSet rs = statement.executeQuery(sql)) {
                rs.next();

                return rs.getInt(1);
            }
        }
    }

    public boolean initializeAndInsertOrganization(Organization organization, User user) {
        System.out.println("initializeAndInsertOrganization");
        try {
            organization.setId(nextIdInSequence());
            organization.setOwnerName(user.getName());
            organization.setCreationDate(ZonedDateTime.now());
            return insertOrganizationWithoutInitialization(organization);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean insertOrganizationWithoutInitialization(Organization organization) {
        System.out.println("insertOrganizationWithoutInitialization");
        String sql = databaseUtil.getOrganizationInsert();

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            databaseUtil.initPS(preparedStatement, organization);
            int updatedRows = preparedStatement.executeUpdate();
            return updatedRows == 1;
        } catch (SQLException | NullPointerException | JsonProcessingException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean authorizeUser(User user) throws InvalidUsernameOrPasswordException {
        System.out.println("authorizeUser");
        User fromDb = getUserOrNull(user.getName());

        if (fromDb == null || !fromDb.getPassword().equals(user.getPassword()))
            throw new InvalidUsernameOrPasswordException();

        return true;
    }

    public boolean removeOrganization(Integer OrganizationId) {
        System.out.println("removeOrganization");

        try (Statement statement = getConnection().createStatement()) {
            String sql = "delete from collection where id = " + OrganizationId + ";";
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean removeOrganization(Organization organization) {
        System.out.println("removeOrganization");
        return removeOrganization(organization.getId());
    }

    public boolean registerUser(User user) throws UserAlreadyExistsException {
        System.out.println("registerUser");
        if (getUserOrNull(user.getName()) != null)
            throw new UserAlreadyExistsException();

        String sql = "insert into users (username, password) values (?, ?);";

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            int updatedRows = statement.executeUpdate();
            return updatedRows == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    private User getUserOrNull(String username) {
        System.out.println("getUserOrNull");
        final String sql = "select * from users where username = ?;";

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, username);

            try (ResultSet rs = statement.executeQuery()) {
                if (!rs.next())
                    return null;

                return new User(rs.getString(1), rs.getString(2));
            }

        } catch (SQLException th) {
            th.printStackTrace();
        }
        return null;
    }

    public void close() throws IOException {
        try {
            ds.close();
        } catch (SQLException throwables) {
        }
    }
}
