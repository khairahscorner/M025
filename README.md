## M025 Assignment

# CSYM025 Project Assigment
JavaFX Application for a Rental property management system


## What's Needed
- Java
- JavaFX
- SceneBuilder
- JUnit 5
- import jar files to build path (javafx, itextpdf, javaxmail, activation)


## System Features
- <img width="414" alt="Screenshot 2024-03-06 at 1 53 01 pm" src="https://github.com/khairahscorner/M025/assets/38109438/236d1ae6-877a-4250-a159-665d76965c59">

- <img width="414" alt="Screenshot 2024-03-06 at 1 53 10 pm" src="https://github.com/khairahscorner/M025/assets/38109438/cca8e5cb-8a19-477f-a4fd-021686d810fb">


## Repo Structure
It contains the following files:
1. Java files - main classes for the program
2. FXML files
3. Controller class files - files for configuring the FXML files
4. .properties file - Java properties file
5. .dat files - contains serialised class objects
6. .pdf files - files generated from two different invoices
7. images folder - sample images


```
/
|
├─ src/
│  │
│  ├─ assignment/          				# Package for main files
│  │  ├─ AdminList.java
│  │  ├─ AppStart.java					# Entry point of application/ class with main method
│  │  ├─ AppStartController.java		# Controller class for the entry point
│  │  │....
│  │  ├─ DataFormatter.java				# Interface
│  │  │....
│  │  ├─ Invoice.java					# Interface
│  │  │....
│  │  ├─ AppStart.fxml					# Starting UI for the app
│  │  │....
│  │  └─ styles.css						# CSS styling for the UIs
│  │
│  │
│  ├─ tests/                			# package for unit tests
│  │  ├─  data/
│  │  │  ├─  collaborators/
│  │  │  ├─  money/
│  │  │  ├─  subscriptions/
│  │  │  └─  users/          
│  │  │
│  │  └─  utilities/
│  │
│  └─ module-info.java
│
├─ images/                  			# random images folder
├─ activation-1.1.jar                			
├─ admins.dat                  			# .dat file containing serialised AdminList object
├─ allFiles.properties                  # Java properties file with file names
├─ customerList.csv                  	# file gotten from exporting customers as csv in the app
├─ itextpdf-5.5.13.jar
├─ javax.mail.jar
├─ ...
├─ README.md              # This file
├─ rentalInvoice.pdf			        # pdf file generated while renting a property
└─ tenancyEndInvoice.pdf
```

---

