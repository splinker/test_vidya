package com.test.procuderes.testdemoprocedures.DAO;

import com.test.procuderes.testdemoprocedures.commons.DBConnection;
import com.test.procuderes.testdemoprocedures.model.Employee;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

@Repository
@Component
public class EmployeeDAO {

    private final String DB_NAME = "employee";
    private final String PROC_INSERT_EMPLOYEE = "insert_employee";
    private final String GET_EMPLOYEE_BY_USERNAME = "get_employee_by_username";
    private final String CHECK_USERNAME_EXISTS = "check_username_exists";
    private final String DELETE_USERNAME_FROM_TABLE= "delete_user_from_employee_table";
    private final String UPDATE_EMPLOYEE_DATA = "update_employee_data";

    public Employee insertToEmployee(Employee employee) throws Exception {
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = DBConnection.getConnection();

            if(!insertIntoEmployeeTable(con, stmt, employee)){
                throw new SQLException("sql exception occured");
            }

            employee = getEmployeeByUsername(con, stmt, employee.getUsername());

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Exception occured");
        }
        return employee;
    }

    public String deleteEmployeeWithUsername(String username) throws Exception {
        Connection con = null;
        CallableStatement stmt = null;
        String output = "delete failure";
        try{
            con = DBConnection.getConnection();
            if(!userFoundInDB(con, stmt, username)){
                throw new SQLException("User not found in db");
            }

            if(!deleteEmployeeFromDB(con, stmt, username)){
                throw new SQLException("Exception while executing the query");
            }

            output = "delete success";

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Exception occured");
        }
        return output;
    }

    public Employee updateEmployeeData(String username, Employee employee) throws Exception {
        Connection con = null;
        CallableStatement stmt = null;
        try{
            con = DBConnection.getConnection();

            if(!userFoundInDB(con, stmt, username)){
                throw new SQLException("User not found in db");
            }

            if(!updateEmployeeInDB(con, stmt, username, employee)){
                throw new SQLException("Unable to update the employee in DB");
            }

            employee = getEmployeeByUsername(con, stmt, username);

        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Exception occured");
        }

        return employee;
    }

    public Employee getEmployeeFromDB(String username) throws Exception {
        Connection con = null;
        CallableStatement stmt = null;
        Employee employee = new Employee();
        try{
            con = DBConnection.getConnection();

            if(!userFoundInDB(con, stmt, username)){
                throw new SQLException("User not found in db");
            }

            employee = getEmployeeByUsername(con, stmt, username);

        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Exception occured");
        }
        return employee;
    }

    private boolean userFoundInDB(Connection con, CallableStatement stmt, String username) throws SQLException {
        stmt = con.prepareCall("{call "+ CHECK_USERNAME_EXISTS +"(?,?)}");
        stmt.setString(1, username);

        //register for values from DB
        stmt.registerOutParameter(2, java.sql.Types.VARCHAR);
        stmt.execute();

        String result = stmt.getString(2);

        if(!result.equalsIgnoreCase("TRUE")){
            return false;
        }
        return true;
    }

    private boolean updateEmployeeInDB(Connection con, CallableStatement stmt, String username, Employee employee) throws SQLException {
        stmt = con.prepareCall("{call "+ UPDATE_EMPLOYEE_DATA +"(?,?,?,?,?,?,?)}");

        stmt.setString(1,username);
        stmt.setInt(2, employee.getId());
        stmt.setString(3,employee.getName());
        stmt.setString(4, employee.getRole());
        stmt.setString(5, employee.getCity());
        stmt.setString(6, employee.getCountry());

        //register for values from DB
        stmt.registerOutParameter(7, java.sql.Types.VARCHAR);

        stmt.execute();

        String result = stmt.getString(7);

        if(!result.equalsIgnoreCase("success")){
            return false;
        }
        return true;
    }

    private Employee getEmployeeByUsername(Connection con, CallableStatement stmt, String username) throws SQLException {
        stmt = con.prepareCall("{call "+ GET_EMPLOYEE_BY_USERNAME +"(?,?,?,?,?,?,?,?)}");
        stmt.setString(1, username);

        //register for values from DB
        stmt.registerOutParameter(2, Types.INTEGER);
        stmt.registerOutParameter(3, java.sql.Types.VARCHAR);
        stmt.registerOutParameter(4, java.sql.Types.VARCHAR);
        stmt.registerOutParameter(5, java.sql.Types.VARCHAR);
        stmt.registerOutParameter(6, java.sql.Types.VARCHAR);
        stmt.registerOutParameter(7, java.sql.Types.VARCHAR);
        stmt.registerOutParameter(8, java.sql.Types.VARCHAR);
        stmt.execute();

        String result = stmt.getString(8);

        if(!result.equalsIgnoreCase("success")){
            throw new SQLException("sql exception occured");
        }

        Employee outEmployee = new Employee();
        outEmployee.setId(stmt.getInt(2));
        outEmployee.setUsername(stmt.getString(3));
        outEmployee.setName(stmt.getString(4));
        outEmployee.setRole(stmt.getString(5));
        outEmployee.setCity(stmt.getString(6));
        outEmployee.setCountry(stmt.getString(7));

        return outEmployee;
    }

    private boolean insertIntoEmployeeTable(Connection con, CallableStatement stmt, Employee employee) throws SQLException {
        stmt = con.prepareCall("{call "+ PROC_INSERT_EMPLOYEE +"(?,?,?,?,?,?,?)}");
        stmt.setInt(1, employee.getId());
        stmt.setString(2, employee.getUsername());
        stmt.setString(3,employee.getName());
        stmt.setString(4, employee.getRole());
        stmt.setString(5, employee.getCity());
        stmt.setString(6, employee.getCountry());

        //register for out param
        stmt.registerOutParameter(7, java.sql.Types.VARCHAR);

        stmt.executeUpdate();

        //read the OUT parameter now
        String result = stmt.getString(7);

        if(!result.equalsIgnoreCase("success")){
            return false;
        }

        return true;
    }

    private boolean deleteEmployeeFromDB(Connection con, CallableStatement stmt, String username) throws SQLException {
        stmt = con.prepareCall("{call "+ DELETE_USERNAME_FROM_TABLE +"(?,?)}");
        stmt.setString(1, username);

        //register for values from DB
        stmt.registerOutParameter(2, java.sql.Types.VARCHAR);
        stmt.execute();

        String result = stmt.getString(2);

        if(!result.equalsIgnoreCase("TRUE")){
            return false;
        }

        return true;
    }
}
