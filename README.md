
# Capstone Project

## Project Description

The Capstone Project focuses on creating a Test Automation Framework (TAF) for UI and API testing of the Spotify application. The aim is to develop and execute test scripts that validate the login functionality, playlist creation, modification, and other related features of Spotify.

## Objectives

1. Develop a Test Automation Framework (TAF).
2. Implement UI test scripts for Spotify's web application.
3. Implement API test scripts for Spotify's playlist functionalities.
4. Execute end-to-end tests combining UI and API interactions.

## Technologies Used

- **Programming Language:** Java
- **Build Tool:** Maven
- **Testing Framework:** JUnit/TestNG
- **Web Automation Tool:** Selenium WebDriver
- **API Testing Tool:** REST Assured

## Test Scripts

### UI Test Scripts

#### Login Tests

- [LoginUITest.java](src/test/java/tests/LoginUITest.java)

1. **Test Login Form with Empty Credentials**
    - Validates that the login form displays appropriate error messages when submitted with empty credentials.

2. **Test Login Form with Incorrect Credentials**
    - Checks the error message when incorrect login credentials are provided.

3. **Test Login Form with Correct Credentials**
    - Ensures that the user can successfully log in with valid credentials.

#### Playlist Tests

- [PlaylistUITest.java](src/test/java/tests/PlaylistUITest.java)

4. **Test Create Playlist**
    - Verifies the functionality of creating a new playlist.

5. **Test Edit Details of Playlist**
    - Confirms that the details of an existing playlist can be edited.

6. **Test Search and Add to Playlist**
    - Tests the ability to search for songs and add them to a playlist.

7. **Test Remove Song from Playlist**
    - Validates the functionality to remove a song from a playlist.

8. **Test Delete Playlist**
    - Ensures that a playlist can be deleted successfully.

### API Test Scripts

- [ApiTest.java](src/test/java/tests/ApiTest.java)

1. **Test Create Playlist**
    - Validates the API endpoint for creating a new playlist.

2. **Test Edit Details of the Playlist**
    - Ensures that the playlist details can be updated via the API.

3. **Test Add Items to Playlist**
    - Checks the API functionality to add songs to a playlist.

4. **Test Remove Song from the Playlist**
    - Validates the API endpoint for removing a song from a playlist.

### End-to-End Test Scripts

- [EndToEndTest.java](src/test/java/tests/EndToEndTest.java)

1. **Add Song to Playlist**
    - Combines UI and API tests to add a song to a playlist and validate the addition.

2. **Test Edit Details of the Playlist**
    - Executes a sequence of UI and API operations to edit and verify playlist details.