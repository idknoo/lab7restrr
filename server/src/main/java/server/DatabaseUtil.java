package server;

import lib.organization.*;

import java.sql.*;

public class DatabaseUtil {

    public static Organization readOrganizationFromResultSet(ResultSet rs) throws SQLException {
        Organization organization = new Organization();
        organization.setName(rs.getString(1));
        organization.setId(rs.getInt(2));
        organization.setFullName(rs.getString(3));
        organization.setCoordinates(new Coordinates(rs.getDouble(4), rs.getFloat(5)));
        Timestamp ts = rs.getTimestamp(6); //kik
        organization.setCreationDate(ts.toLocalDateTime().toLocalDate()); //не понимаю
        organization.setAnnualTurnover(rs.getDouble(7));
        organization.setEmployeesCount(rs.getInt(8));
        organization.setType(OrganizationType.valueOf(rs.getString(9));
        organization.setPostalAddress(new Address(rs.getString(10), rs.getObject(11, Location()))); // доделать
        return organization;
    }

    public static void initPreparedStatement(PreparedStatement ps, Organization organization) throws SQLException {
        ps.setString(1, organization.getName());
        ps.setInt(2, organization.getId());
        ps.setString(3,organization.getFullName());
        ps.setDouble(4,organization.getCoordinates().getX());
        ps.setFloat(5,organization.getCoordinates().getY());
        ps.setDate(6, Date.valueOf(organization.getCreationDate().toLocalDate()));
        ps.setDouble(7,organization.getAnnualTurnover());
        ps.setInt(8, organization.getEmployeesCount());
        ps.setString(9,organization.getType().toString());
//        ps.setInt(10, city.getPopulationDensity());
//        ps.setString(11,city.getClimate().toString());
//        ps.setString(12, city.getGovernment().toString());
//        ps.setString(13, city.getGovernor().getName());
//        ps.setFloat(14, city.getGovernor().getHeight());

    }

    public static String getProductInsertionSql() {
        return String.format(
                "insert into collection (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
                "username",
                "id",
                "full_name",
                "coordinates_x",
                "coordinates_y",
                "creation_date",
                "annual_turnover",
                "employees_count",
                "organization_type",
                "",
                "",
                "",
                "",
                ""
        );
    }
}
