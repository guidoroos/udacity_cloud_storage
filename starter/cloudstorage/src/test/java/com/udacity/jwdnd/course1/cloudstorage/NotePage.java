package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class NotePage {
    @FindBy(id = "nav-notes-tab")
    private WebElement notesNavTab;

    @FindBy(id = "addNoteButton")
    private WebElement addNoteButton;

    @FindBy(id = "noteContainer")
    private WebElement noteContainer;

    @FindBy(id = "noteModal")
    private WebElement noteModal;

    @FindBy(id = "noteModalTitle")
    private WebElement noteModalTitle;

    @FindBy(id = "noteModalDescription")
    private WebElement noteModalDescription;

    @FindBy(id = "noteModalSubmit")
    private WebElement noteSubmit;



    public NotePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void goToNotePage() {
        notesNavTab.click();
    }


    public void clickAddNoteButton() {
        addNoteButton.click();
    }

    public void typeNoteInput(String title, String description) {
        noteModalTitle.click();
        noteModalTitle.sendKeys(title);

        noteModalDescription.click();
        noteModalDescription.sendKeys(description);
    }

    public void clearNoteInput() {
        noteModalTitle.click();
        noteModalTitle.clear();

        noteModalDescription.click();
        noteModalDescription.clear();
    }

    public void submitNote() {
        noteSubmit.click();
    }

    public String getNoteTitle(int position) {
        List<WebElement> noteTitles = noteContainer.findElements(By.id("noteTitle"));
        WebElement noteTitle = noteTitles.get(position);
        return noteTitle.getText();
    }

    public String getNoteDescription(int position) {
        List<WebElement> noteDescriptions = noteContainer.findElements(By.id("noteDescription"));
        WebElement noteDescription = noteDescriptions.get(position);
        return noteDescription.getText();
    }


    public void clickEditNoteButton(int position) {
        List<WebElement> notes = noteContainer.findElements(By.id("editNoteButton"));
        WebElement editButton = notes.get(position);
        editButton.click();
    }

    public String getNoteModalTitle() {
        return noteModalTitle.getAttribute("value");
    }

    public String getNoteModalDescription() {
        return noteModalDescription.getAttribute("value");
    }

    public void clickDeleteNoteButton(int position) {
        List<WebElement> notes = noteContainer.findElements(By.id("deleteNoteButton"));
        WebElement deleteButton = notes.get(position);
        deleteButton.click();
    }

}
