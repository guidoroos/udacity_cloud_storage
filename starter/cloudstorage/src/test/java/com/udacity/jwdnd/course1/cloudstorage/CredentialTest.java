package com.udacity.jwdnd.course1.cloudstorage;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static com.udacity.jwdnd.course1.cloudstorage.CloudStorageApplicationTests.loginMockUser;
import static com.udacity.jwdnd.course1.cloudstorage.CloudStorageApplicationTests.signupMockUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTest {
    @LocalServerPort
    private Integer port;

    private static WebDriver driver;

    private CredentialPage credentialPage;


    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @BeforeEach
    public void beforeEach() {
        credentialPage = new CredentialPage(driver);
    }

    @AfterEach
    public void afterEach() {
        WebElement logoutButton = driver.findElement(By.id("logoutButton"));
        logoutButton.click();
    }


    @AfterAll
    public static void afterAll() {
        driver.quit();
    }


    @Test
    public void testCreateCredential() throws InterruptedException {
        doSignUp("ali", "baba", "aliBaba", "test123");
        doLogin("aliBaba", "test123");

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        credentialPage.goToCredentialPage();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addCredentialButton")));

        credentialPage.clickAddCredentialButton();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialModal")));

        credentialPage.typeCredentialInput("www.fruits.com", "BillBanana", "bananasplit");
        credentialPage.submitCredential();
        credentialPage.goToCredentialPage();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addCredentialButton")));

        String url = credentialPage.getCredentialUrl(0);
        String username = credentialPage.getCredentialUsername(0);
        String password = credentialPage.getCredentialPassword(0);

        assertEquals(url, "www.fruits.com");
        assertEquals(username, "BillBanana");
        //encrypted password should be visible instead
        assertNotEquals(password, "bananasplit");
    }

    @Test
    public void testEditCredential() throws InterruptedException {
        doSignUp("peter", "pan", "peterPan", "test123");
        doLogin("peterPan", "test123");

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        credentialPage.goToCredentialPage();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addCredentialButton")));

        credentialPage.clickAddCredentialButton();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialModal")));

        credentialPage.typeCredentialInput("www.vegetables.com", "TomTomato", "Pomodoro");
        credentialPage.submitCredential();
        credentialPage.goToCredentialPage();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addCredentialButton")));

        String url = credentialPage.getCredentialUrl(0);
        String username = credentialPage.getCredentialUsername(0);

        assertEquals(url, "www.vegetables.com");
        assertEquals(username, "TomTomato");

        credentialPage.clickEditCredentialButton(0);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialModalUrl")));

        String credentialModalUrl = credentialPage.getCredentialModalUrl();
        String credentialModalUsername = credentialPage.getCredentialModalUsername();
        String credentialModalPassword = credentialPage.getCredentialModalPassword();

        assertEquals(credentialModalUrl, "www.vegetables.com");
        assertEquals(credentialModalUsername, "TomTomato");
        assertEquals(credentialModalPassword, "Pomodoro");

        credentialPage.clearCredentialInput();
        credentialPage.typeCredentialInput("www.cheese.com", "CharlyBrie", "ViveLaFrance");
        credentialPage.submitCredential();
        credentialPage.goToCredentialPage();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addCredentialButton")));

        url = credentialPage.getCredentialUrl(0);
        username = credentialPage.getCredentialUsername(0);

        assertEquals(url, "www.cheese.com");
        assertEquals(username, "CharlyBrie");
    }

    @Test
    public void testDeleteCredential() throws InterruptedException {
        doSignUp("sponge", "bob", "spongeBob", "crustyCrab123");
        doLogin("spongeBob", "crustyCrab123");

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        credentialPage.goToCredentialPage();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addCredentialButton")));

        credentialPage.clickAddCredentialButton();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialModal")));

        credentialPage.typeCredentialInput("www.vegetables.com", "TomTomato", "Pomodoro");
        credentialPage.submitCredential();
        credentialPage.goToCredentialPage();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addCredentialButton")));

        String url = credentialPage.getCredentialUrl(0);
        String username = credentialPage.getCredentialUsername(0);

        assertEquals(url, "www.vegetables.com");
        assertEquals(username, "TomTomato");

        credentialPage.goToCredentialPage();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addCredentialButton")));

        credentialPage.clickAddCredentialButton();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialModal")));
        credentialPage.typeCredentialInput("www.cheese.com", "CharlyBrie", "ViveLaFrance");
        credentialPage.submitCredential();
        credentialPage.goToCredentialPage();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addCredentialButton")));

        username = credentialPage.getCredentialUsername(1);
        url = credentialPage.getCredentialUrl(1);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addCredentialButton")));

        assertEquals(url, "www.cheese.com");
        assertEquals(username, "CharlyBrie");

        credentialPage.clickDeleteCredentialButton(0);
        credentialPage.goToCredentialPage();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addCredentialButton")));

        url = credentialPage.getCredentialUrl(0);
        username = credentialPage.getCredentialUsername(0);

        assertEquals(url, "www.cheese.com");
        assertEquals(username, "CharlyBrie");
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

