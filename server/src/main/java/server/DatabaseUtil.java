package server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.operations.Or;
import lib.organization.*;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DatabaseUtil {

    private final ObjectMapper objectMapper;

    public DatabaseUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void initPS(PreparedStatement ps, Organization organization) throws SQLException, JsonProcessingException {
        ps.setInt(1, organization.getId());
        String str = objectMapper.writeValueAsString(organization);
        System.out.println("__ str: " + str);
        ps.setString(2, str);
    }

    public String getOrganizationInsert() {
        return String.format(
                "insert into collection (%s, %s) values(?, ?);",
                "id",
                "data"
        );
    }

    public Organization readOrganization(ResultSet rs) throws SQLException, JsonProcessingException {
        int id = rs.getInt(1);
        String str = rs.getString(2);
        return objectMapper.readValue(str, Organization.class);
    }
}
