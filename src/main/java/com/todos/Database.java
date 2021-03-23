package com.todos;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class Database {

    static Connection connection;

    public void init() {
        if (connection == null) {
            try {
                connection = DriverManager
                        .getConnection("jdbc:h2:mem:myDB", "todos", "secret");
            } catch (Exception e) {
                System.out.println("database connection failed!");
            }
        }
    }

    public void put(UUID id, Todo todo) {
        init();

        String insertTodoSql = "INSERT INTO todos(id, title, completed, todoorder) " +
                "VALUES(?, ?, ?, ? )";
        try {
            PreparedStatement pstmt = connection.prepareStatement(insertTodoSql);
            pstmt.setString(1, id.toString());
            pstmt.setString(2, todo.getTitle());
            pstmt.setLong(4, todo.getOrder());
            pstmt.setBoolean(3, todo.isCompleted());
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Collection<Todo> all() {
        init();

        String selectSql = "SELECT * FROM todos";
        List<Todo> todos = new ArrayList<>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(selectSql);
            while (resultSet.next()) {
                Todo todo = new Todo(
                        resultSet.getString("title"),
                        resultSet.getBoolean("completed"),
                        resultSet.getInt("todoorder")
                );
                todo.setId(UUID.fromString(resultSet.getString("id")));
                todos.add(todo);
            }
            return todos;
        } catch (SQLException throwables) {
            return todos;
        }
    }

    public void clear() {
        String selectSql = "DELETE FROM todos";
        try {
            connection.createStatement().execute(selectSql);
        } catch (SQLException throwables) {
        }
    }

    public void remove(UUID id) {
        String deleteSql = "DELETE FROM todos where id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(deleteSql);
            pstmt.setString(1, id.toString());
            pstmt.execute();
        } catch (SQLException throwables) {
        }
    }

    public Todo get(UUID todoId) {
        Todo found = null;
        for (Todo todo: all()) {
            if (todo.getId().equals(todoId)) {
                found = todo;
            }
        }
        return found;
    }
}

