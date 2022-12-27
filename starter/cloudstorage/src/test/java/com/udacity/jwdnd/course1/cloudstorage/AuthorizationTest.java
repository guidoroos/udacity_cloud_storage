package com.udacity.jwdnd.course1.cloudstorage;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static com.udacity.jwdnd.course1.cloudstorage.CloudStorageApplicationTests.loginMockUser;
import static com.udacity.jwdnd.course1.cloudstorage.CloudStorageApplicationTests.signupMockUser;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorizationTest {
    private static WebDriver driver;
    @LocalServerPort
    private Integer port;


    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
    }


    @Test
    public void testUnauthorizedUserCanOnlyAccessSignupLogin() throws InterruptedException {
        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("http://localhost:" + this.port + "/signup", driver.getCurrentUrl());
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertNotEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());

    }

    @Test
    public void testLoggedOutUserCanOnlyAccessSignupLogin() throws InterruptedException {
        doSignUp("ali", "baba", "aliBaba", "test123");
        doLogin("aliBaba", "test123");

        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("http://localhost:" + this.port + "/signup", driver.getCurrentUrl());
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());

        WebElement logoutButton = driver.findElement(By.id("logoutButton"));
        logoutButton.click();

        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("http://localhost:" + this.port + "/signup", driver.getCurrentUrl());
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertNotEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());

    }


    private void doSignUp(String firstName, String lastName, String username, String password) {
        driver.get("http://localhost:" + this.port + "/signup");

        // Fill out credentials
        signupMockUser(driver, firstName, lastName, username, password);
    }

    private void doLogin(String username, String password) {
        // Log in to our dummy account.
        driver.get("http://localhost:" + this.port + "/login");
        loginMockUser(driver, username, password);
    }


}


