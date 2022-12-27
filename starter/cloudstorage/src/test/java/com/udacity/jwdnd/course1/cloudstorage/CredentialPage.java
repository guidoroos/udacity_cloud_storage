package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CredentialPage {
    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsNavTab;

    @FindBy(id = "addCredentialButton")
    private WebElement addCredentialButton;

    @FindBy(id = "credentialContainer")
    private WebElement credentialContainer;

    @FindBy(id = "credentialUrl")
    private WebElement credentialUrl;

    @FindBy(id = "credentialUsername")
    private WebElement credentialUsername;
    
    @FindBy(id = "credentialPassword")
    private WebElement credentialPassword;
    
    @FindBy(id = "credentialModal")
    private WebElement credentialModal;
    
    @FindBy(id = "credentialModalUrl")
    private WebElement credentialModalUrl;

    @FindBy(id = "credentialModalUsername")
    private WebElement credentialModalUsername;

    @FindBy(id = "credentialModalPassword")
    private WebElement credentialModalPassword;

    @FindBy(id = "credentialSubmitButton")
    private WebElement credentialSubmit;


    public CredentialPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void goToCredentialPage() {
        credentialsNavTab.click();
    }


    public void clickAddCredentialButton() {
        addCredentialButton.click();
    }

    public void typeCredentialInput(String url, String username, String password) {
        credentialModalUrl.click();
        credentialModalUrl.sendKeys(url);

        credentialModalUsername.click();
        credentialModalUsername.sendKeys(username);

        credentialModalPassword.click();
        credentialModalPassword.sendKeys(password);
    }

    public void clearCredentialInput() {
        credentialModalUrl.click();
        credentialModalUrl.clear();

        credentialModalUsername.click();
        credentialModalUsername.clear();

        credentialModalPassword.click();
        credentialModalPassword.clear();
    }

    public void submitCredential() {
        credentialSubmit.click();
    }

    public String getCredentialUrl(int position) {
        List<WebElement> credentialUrls = credentialContainer.findElements(By.id("credentialUrl"));
        WebElement credentialUrl = credentialUrls.get(position);
        return credentialUrl.getText();
    }

    public String getCredentialUsername(int position) {
        List<WebElement> credentialUserNames = credentialContainer.findElements(By.id("credentialUsername"));
        WebElement credentialUsername = credentialUserNames.get(position);
        return credentialUsername.getText();
    }

    public String getCredentialPassword(int position) {
        List<WebElement> credentialPasswords = credentialContainer.findElements(By.id("credentialPassword"));
        WebElement credentialPassword = credentialPasswords.get(position);
        return credentialUsername.getText();
    }


    public void clickEditCredentialButton(int position) {
        List<WebElement> credentials = credentialContainer.findElements(By.id("editCredentialButton"));
        WebElement editButton = credentials.get(position);
        editButton.click();
    }

    public String getCredentialModalUrl() {
        return credentialModalUrl.getAttribute("value");
    }

    public String getCredentialModalUsername() {
        return credentialModalUsername.getAttribute("value");
    }

    public String getCredentialModalPassword() {
        return credentialModalPassword.getAttribute("value");
    }

    public void clickDeleteCredentialButton(int position) {
        List<WebElement> credentials = credentialContainer.findElements(By.id("deleteCredentialButton"));
        WebElement deleteButton = credentials.get(position);
        deleteButton.click();
    }
}
