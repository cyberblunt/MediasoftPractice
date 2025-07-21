package org.example.service;

import org.example.entity.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        // Arrange
        List<User> expectedUsers = Arrays.asList(testUser);
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // Act
        List<User> actualUsers = userService.getAllUsers();

        // Assert
        assertEquals(expectedUsers, actualUsers);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_WhenUserExists_ShouldReturnUser() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // Act
        Optional<User> actualUser = userService.getUserById(1L);

        // Assert
        assertTrue(actualUser.isPresent());
        assertEquals(testUser, actualUser.get());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserById_WhenUserDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<User> actualUser = userService.getUserById(999L);

        // Assert
        assertFalse(actualUser.isPresent());
        verify(userRepository, times(1)).findById(999L);
    }

    @Test
    void createUser_ShouldReturnSavedUser() {
        // Arrange
        User userToSave = new User("New User", "new@example.com");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        User savedUser = userService.createUser(userToSave);

        // Assert
        assertEquals(testUser, savedUser);
        verify(userRepository, times(1)).save(userToSave);
    }

    @Test
    void updateUser_WhenUserExists_ShouldReturnUpdatedUser() {
        // Arrange
        User updateDetails = new User("Updated User", "updated@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        User updatedUser = userService.updateUser(1L, updateDetails);

        // Assert
        assertEquals(testUser, updatedUser);
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void updateUser_WhenUserDoesNotExist_ShouldThrowException() {
        // Arrange
        User updateDetails = new User("Updated User", "updated@example.com");
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.updateUser(999L, updateDetails));
        verify(userRepository, times(1)).findById(999L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteUser_ShouldCallRepositoryDelete() {
        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void findByEmail_ShouldReturnUser() {
        // Arrange
        when(userRepository.findByEmail("test@example.com")).thenReturn(testUser);

        // Act
        User foundUser = userService.findByEmail("test@example.com");

        // Assert
        assertEquals(testUser, foundUser);
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }
} 