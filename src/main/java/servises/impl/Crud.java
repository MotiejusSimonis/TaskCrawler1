package servises.impl;

import models.DTO.DBQueryDTO;
import servises.ICrud;
import servises.IDataBase;
import servises.helpers.QueryBuilder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Crud implements ICrud {

    @Override
    public DBQueryDTO create(Object object) {
        IDataBase dataBase = new DataBase();
        Connection connection = dataBase.getConnection();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(QueryBuilder.createInsertQuery(object));

            int rowsCreated = preparedStatement.executeUpdate();
            preparedStatement.close();
            return new DBQueryDTO(true, "Rows created -> " + rowsCreated, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new DBQueryDTO(false, e.getMessage(), null);
        } finally {
            dataBase.closeConnection(connection);
        }
    }


    @Override
    public DBQueryDTO read(String query) {
        IDataBase dataBase = new DataBase();
        Connection connection = dataBase.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int columnCount = resultSet.getMetaData().getColumnCount();
            List<List<Object>> rows = new ArrayList<>();
            while(resultSet.next()) {
                List<Object> columns = new ArrayList<>();
                for (int i = 0; i < columnCount; i++) {
                    columns.add(resultSet.getObject(i));
                }
                rows.add(columns);
            }
            return new DBQueryDTO(true, "Rows selected from DB -> " + rows.size(), rows);
        } catch (Exception e) {
            e.printStackTrace();
            return new DBQueryDTO(false, e.getMessage(), null);
        } finally {
            dataBase.closeConnection(connection);
        }
    }


    @Override
    public DBQueryDTO update(String query) {
        IDataBase dataBase = new DataBase();
        Connection connection = dataBase.getConnection();
        try {
            Statement statement = connection.createStatement();
            int rowsUpdated = statement.executeUpdate(query);
            return new DBQueryDTO(true, "Rows updated from DB -> " + rowsUpdated, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new DBQueryDTO(false, e.getMessage(), null);
        } finally {
            dataBase.closeConnection(connection);
        }
    }


    @Override
    public DBQueryDTO delete(String query) {
        IDataBase dataBase = new DataBase();
        Connection connection = dataBase.getConnection();
        try {
            Statement statement = connection.createStatement();
            int rowsDeleted = statement.executeUpdate(query);
            return new DBQueryDTO(true, "Rows updated from DB -> " + rowsDeleted, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new DBQueryDTO(false, e.getMessage(), null);
        } finally {
            dataBase.closeConnection(connection);
        }
    }
}


