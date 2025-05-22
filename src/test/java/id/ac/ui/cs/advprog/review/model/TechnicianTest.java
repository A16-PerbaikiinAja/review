package id.ac.ui.cs.advprog.review.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TechnicianTest {

    private Technician technician;
    private UUID testId;
    private String testFullName;
    private String testEmail;
    private String testPassword;
    private String testPhoneNumber;
    private String testAddress;
    private String testProfilePhoto;
    private Integer testExperience;
    private Double testTotalEarnings;
    private Integer testTotalJobsCompleted;
    private LocalDateTime testCreatedAt;

    @BeforeEach
    void setUp() throws Exception {
        testId = UUID.randomUUID();
        testFullName = "Kieran White";
        testEmail = "kieran@example.com";
        testPassword = "White123";
        testPhoneNumber = "081234567890";
        testAddress = "Ardhalis";
        testProfilePhoto = "profile.jpg";
        testExperience = 5;
        testTotalEarnings = 1000000.0;
        testTotalJobsCompleted = 50;
        testCreatedAt = LocalDateTime.now();

        technician = createTechnicianWithReflection();
    }

    private Technician createTechnicianWithReflection() throws Exception {
        Constructor<Technician> constructor = Technician.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Technician tech = constructor.newInstance();

        setField(tech, "id", testId);
        setField(tech, "fullName", testFullName);
        setField(tech, "email", testEmail);
        setField(tech, "password", testPassword);
        setField(tech, "phoneNumber", testPhoneNumber);
        setField(tech, "address", testAddress);
        setField(tech, "profilePhoto", testProfilePhoto);
        setField(tech, "experience", testExperience);
        setField(tech, "totalEarnings", testTotalEarnings);
        setField(tech, "totalJobsCompleted", testTotalJobsCompleted);
        setField(tech, "createdAt", testCreatedAt);

        return tech;
    }

    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    void testGetId() {
        assertEquals(testId, technician.getId());
    }

    @Test
    void testGetFullName() {
        assertEquals(testFullName, technician.getFullName());
    }

    @Test
    void testGetEmail() {
        assertEquals(testEmail, technician.getEmail());
    }

    @Test
    void testGetPassword() {
        assertEquals(testPassword, technician.getPassword());
    }

    @Test
    void testGetPhoneNumber() {
        assertEquals(testPhoneNumber, technician.getPhoneNumber());
    }

    @Test
    void testGetAddress() {
        assertEquals(testAddress, technician.getAddress());
    }

    @Test
    void testGetProfilePhoto() {
        assertEquals(testProfilePhoto, technician.getProfilePhoto());
    }

    @Test
    void testGetExperience() {
        assertEquals(testExperience, technician.getExperience());
    }

    @Test
    void testGetTotalEarnings() {
        assertEquals(testTotalEarnings, technician.getTotalEarnings());
    }

    @Test
    void testGetTotalJobsCompleted() {
        assertEquals(testTotalJobsCompleted, technician.getTotalJobsCompleted());
    }

    @Test
    void testGetCreatedAt() {
        assertEquals(testCreatedAt, technician.getCreatedAt());
    }
}