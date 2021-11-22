package server;

import lib.commands.User;
import lib.organization.Organization;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.PriorityQueue;

public class Database {
    private final String url;
    private final String user;
    private final String password;

    private Connection connection;

    public Database(String url, String user, String password) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;
        establishConnection();
    }

    private void establishConnection() throws SQLException {
        System.out.println("establishConnection");
        connection = DriverManager.getConnection(url, user, password);
    }

    public PriorityQueue<Organization> readAllOrganizations() {
        System.out.println("readAllOrganizations");
        PriorityQueue<Organization> collection = new PriorityQueue<>();

        try (Statement statement = connection.createStatement()) {
            String sql = "select * from collection;";

            try (ResultSet rs = statement.executeQuery(sql)) {
                while (rs.next()) {
                    collection.add(DatabaseUtil.readOrganizationFromResultSet(rs));
                }
            }

            return collection;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    private int nextIdInSequence() throws SQLException {
        System.out.println("nextIdInSequence");
        try (Statement statement = connection.createStatement()) {
            String sql = "select nextval ('id_seq');";

            try (ResultSet rs = statement.executeQuery(sql)) {
                rs.next();

                return rs.getInt(1);
            }
        }
    }

    public boolean initializeAndInsertOrganozation(Organization organization, User user) {
        System.out.println("initializeAndInsertOrganization");
        try {
            organization.setId(nextIdInSequence());
            organization.setName(user.getName());
            organization.setCreationDate(LocalDate.now());
            return insertOrganizationWithoutInitialization(organization);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean insertOrganizationWithoutInitialization(Organization organization) {
        System.out.println("insertOrganizationWithoutInitialization");
        String sql = DatabaseUtil.getProductInsertionSql();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            DatabaseUtil.initPreparedStatement(preparedStatement, organization);
            int updatedRows = preparedStatement.executeUpdate();
            return updatedRows == 1;
        } catch (SQLException | NullPointerException throwables) {
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
        try (Statement statement = connection.createStatement()) {
            String sql = "delete from collection where id = " + OrganizationId + ";";
            int updatedRows = statement.executeUpdate(sql);
            return updatedRows == 1;
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

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
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

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
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
            connection.close();
        } catch (SQLException throwables) {
        }
    }


}
