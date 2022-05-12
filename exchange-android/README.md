# Android Platform

## How to Run
- Android Studio with jdk 15 or later to build the project
- Simulator or physical Android device running Android OS with API level 31 or later
- Backend server needs to be running for connection with the frontend. Please
Refer to the README of the backend for instructions.

## Technologies Used
- Androidx is used to build the GUI components
- [Androidplot](http://androidplot.com/) is used for the exchange rate multi-line graph
- Retrofit as the HTTP-Client

# Project Structure

Models and Fragments/Activities        |  Layouts
:-------------------------:|:-------------------------:
![](http://ekha.link.s3-website.eu-west-3.amazonaws.com/Models.PNG)   | ![](http://ekha.link.s3-website.eu-west-3.amazonaws.com/Layouts.PNG)


## Fragments / Activites

### `MainActivity`
Initial activity of the application which is called when the app loads. This activity creates the TabManager for the different fragments as well as the activities for both login and register. This activity also includes the logic to add a transaction when using the FAB.

### `TabsPageAdapter`
Adapter that handles the logic when clicking the tabs in the MainActivity of the application.

### `LoginActivity + RegistrationActivity`
Activities that contain the logic for signing in as well as signing up. This includes saving the user token on successfull authentication and creating a user and saving it in the database upon successful registration.

### `ExchangeFragment`
Fragment that contains the current 3 day rolling average of both rates. This fragment also contains a calculator where users can calculate the corresponding output for each rate.

### `TransactionsFragment`
Fragment that contains the previous transactions of the logged in user.

### `InsightsFragment`
Fragment that contains a couple of statistics into the development of the rates including 1-12-24 hour changes as well as 7 day changes. This fragment also contain a line chart with two lines showing the devlopment of rates over time.

### `PostsFragment`
Fragment that contains the trade postings of ever user (except the logged in user). On this platform users can click to accept said trades and the transaction will be added to the database. Users can also add transactions indicating the amount that they want to sell/buy for each currency. (Users need to be logged in to view this tab)

### `PostHelper`
A helper class that handles all the backend for fetching and updating the PostsFragment tab.

# App Architecture
This is a visual representation of the architecture the app is based on 


![MVC Architecture](http://ekha.link.s3-website.eu-west-3.amazonaws.com/Archi.PNG)


