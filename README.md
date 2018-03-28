# Projects
These are some of the projects we had at Tech Elevator.

The Vending machine capstone project was completed after 4 weeks of Java.
GIVEN: We were provided the Menu.java file, about 20 lines of the VendingMachine.CLI, and the .csv file with the products.
Instructor Feedback: It still needs significant refactoring before it is truly readable, and there is some repetion with the Interface (should have used a super class!). 

Two weeks later, we completed the capstone project National Park Reservation System. This project required integration with SQL postgres databases, and I was able to apply some of the suggestions from the feedback provided for the vending machine by breaking down the methods in the CLI into smaller methods that are easier to follow.
GIVEN: We were provided the database to work with, a basic Campground.CLI file with about 20 lines, and Menu.java (I only added one method to menu). Everything else was built from scratch.
Instructor Feedback: I used recursive methods to return to previous menus, not realizing that this causes there to be multiple sessions running at once that will eventually result in a crash. A better way to handle "return to previous menu" is to use while loops. 

The third capstone was my first full-stack application. It involved applying MVC principles in Java using the Spring framework (run on Tomcat server). The View was written in JSTL and EL language, the Model was integrating with PostgreSQL, and the Controller took care of the GET and POST methods. My pair and I applied server-side validation on the survey page, made integration tests for the database and selenium tests for the view pages.
GIVEN: We were provided the database and an abstract class that set up the database connection for the tests.
Instructor Feedback: We should have made it possible to call the advisory notices from the same weather class. In this example there is some redundancy in the controller where we pass two separate weather Lists. 