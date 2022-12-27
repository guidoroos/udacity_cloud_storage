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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteTest {
    @LocalServerPort
    private Integer port;

    private static WebDriver driver;

    private NotePage notePage;


    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @BeforeEach
    public void beforeEach() {
        notePage = new NotePage(driver);
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
    public void testCreateNote() throws InterruptedException {
        doSignUp("ali", "baba", "aliBaba", "test123");
        doLogin("aliBaba", "test123");

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        notePage.goToNotePage();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNoteButton")));

        notePage.clickAddNoteButton();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteModal")));

        notePage.typeNoteInput("Do laundry", "wash at 40 degrees");
        notePage.submitNote();
        notePage.goToNotePage();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNoteButton")));

        String title = notePage.getNoteTitle(0);
        String description = notePage.getNoteDescription(0);

        assertEquals(title,"Do laundry" );
        assertEquals(description,"wash at 40 degrees" );
    }

    @Test
    public void testEditNote() throws InterruptedException {
        doSignUp("peter", "pan", "peterPan", "test123");
        doLogin("peterPan", "test123");

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        notePage.goToNotePage();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNoteButton")));

        notePage.clickAddNoteButton();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteModal")));

        notePage.typeNoteInput("Do laundry", "wash at 40 degrees");
        notePage.submitNote();
        notePage.goToNotePage();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNoteButton")));

        String title = notePage.getNoteTitle(0);
        String description = notePage.getNoteDescription(0);

        assertEquals(title,"Do laundry" );
        assertEquals(description,"wash at 40 degrees" );

        notePage.clickEditNoteButton(0);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteModalTitle")));
        notePage.clearNoteInput();
        notePage.typeNoteInput("Do dishes", "rinse with cold water then wash with hot water and soap");
        notePage.submitNote();
        notePage.goToNotePage();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNoteButton")));

        title = notePage.getNoteTitle(0);
        description = notePage.getNoteDescription(0);

        assertEquals(title,"Do dishes");
        assertEquals(description,"rinse with cold water then wash with hot water and soap" );
    }

    @Test
    public void testDeleteNote() throws InterruptedException {
        doSignUp("sponge", "bob", "spongeBob", "crustyCrab123");
        doLogin("spongeBob", "crustyCrab123");

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        notePage.goToNotePage();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNoteButton")));

        notePage.clickAddNoteButton();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteModal")));

        notePage.typeNoteInput("Do laundry", "wash at 40 degrees");
        notePage.submitNote();
        notePage.goToNotePage();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNoteButton")));

        String title = notePage.getNoteTitle(0);
        String description = notePage.getNoteDescription(0);

        assertEquals(title,"Do laundry" );
        assertEquals(description,"wash at 40 degrees" );

        notePage.goToNotePage();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNoteButton")));

        notePage.clickAddNoteButton();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteModal")));
        notePage.typeNoteInput("Do dishes", "rinse with cold water then wash with hot water and soap");
        notePage.submitNote();
        notePage.goToNotePage();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNoteButton")));

        title = notePage.getNoteTitle(1);
        description = notePage.getNoteDescription(1);

        assertEquals(title,"Do dishes" );
        assertEquals(description,"rinse with cold water then wash with hot water and soap" );

        notePage.clickDeleteNoteButton(0);
        notePage.goToNotePage();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNoteButton")));

        title = notePage.getNoteTitle(0);
        description = notePage.getNoteDescription(0);

        assertEquals(title,"Do dishes");
        assertEquals(description,"rinse with cold water then wash with hot water and soap" );
    }

    private void doSignUp (String firstName, String lastName, String username, String password) {
        driver.get("http://localhost:" + this.port + "/signup");

        // Fill out credentials
        signupMockUser(driver, firstName, lastName, username, password);
    }

    private void doLogin(String username, String password)
    {
        // Log in to our dummy account.
        driver.get("http://localhost:" + this.port + "/login");
        loginMockUser(driver, username, password);
    }







}

