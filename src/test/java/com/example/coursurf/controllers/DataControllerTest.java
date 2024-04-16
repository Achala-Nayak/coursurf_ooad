// package com.example.coursurf.controllers;

// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertNotNull;
// import static org.junit.Assert.assertTrue;
// import static org.mockito.Mockito.*;

// import org.junit.Before;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.mockito.Mock;
// import org.mockito.junit.MockitoJUnitRunner;
// import org.springframework.http.ResponseEntity;

// import com.example.coursurf.config.DbProperties;

// import java.sql.*;

// @RunWith(MockitoJUnitRunner.class)
// public class DataControllerTest {

//     @Mock
//     private DbProperties dbProperties;

//     @Mock
//     private Connection mockConnection;

//     @Mock
//     private PreparedStatement mockStatement;

//     @Mock
//     private ResultSet mockResultSet;

//     private DataController dataController;

//     @Before
//     public void setup() throws SQLException {
//         // Initialize DataController with mocked dependencies
//         dataController = new DataController();
//         dataController.setDbProperties(dbProperties);

//         // Mock DbProperties
//         when(dbProperties.getHost()).thenReturn("localhost");
//         when(dbProperties.getPort()).thenReturn("3306");
//         when(dbProperties.getDatabase()).thenReturn("test_db");
//         when(dbProperties.getUser()).thenReturn("test_user");
//         when(dbProperties.getPassword()).thenReturn("test_password");

//         // Mock DriverManager.getConnection() to return our mockConnection
//         when(DriverManager.getConnection(anyString(), anyString(), anyString())).thenReturn(mockConnection);
//     }

//     @Test
//     public void testFetchData() throws SQLException {
//         // Mock ResultSet and its behavior
//         when(mockResultSet.next()).thenReturn(true).thenReturn(false); // Simulate one row
//         when(mockResultSet.getObject(anyInt())).thenReturn("value1").thenReturn("value2"); // Example column values

//         // Mock PreparedStatement behavior
//         when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
//         when(mockStatement.executeQuery()).thenReturn(mockResultSet);

//         // Test fetchData API
//         ResponseEntity<?> response = (ResponseEntity<?>) dataController.fetchData("query", "provider", 3, 5);

//         // Assertions
//         assertNotNull(response);
//         // Add more assertions based on expected behavior
//     }

//     @Test
//     public void testGetTrendingData() throws SQLException {
//         // Mock ResultSet and its behavior
//         when(mockResultSet.next()).thenReturn(true).thenReturn(false); // Simulate one row
//         when(mockResultSet.getObject(anyInt())).thenReturn("value1").thenReturn("value2"); // Example column values

//         // Mock PreparedStatement behavior
//         when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
//         when(mockStatement.executeQuery()).thenReturn(mockResultSet);

//         // Test getTrendingData API
//         ResponseEntity<?> response = (ResponseEntity<?>) dataController.getTrendingData(5);

//         // Assertions
//         assertNotNull(response);
//         // Add more assertions based on expected behavior
//     }

//     @SuppressWarnings("deprecation")
//     @Test
//     public void testIncrementClick() throws SQLException {
//         // Mock PreparedStatement behavior for update
//         when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
//         when(mockStatement.executeUpdate()).thenReturn(1); // Simulate one row updated

//         // Test incrementClick API
//         ResponseEntity<String> response = dataController.incrementClick("Course Title");

//         // Assertions
//         assertNotNull(response);
//         assertEquals(200, response.getStatusCodeValue());
//         assertTrue(response.getBody().contains("Click count updated for title"));
//     }
// }
