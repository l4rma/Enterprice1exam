#pg5100 exam

## How to run
* Install
    * $ mvn package -DskipTests
* Run test and generate Jacoco report
    * $ mvn clean verify
* Run application locally
    * Right click and "run" LocalApplicationRunner (located in the frontend module)

* update: After testing this, it wouldn't build jacoco report after I exported the project as a zip. 
To "fix" it, I had to go to the mvn tab on the right side of the screen then click on report>Lifecycle>package then 
movies>Lifecycle>package. This created the target folder in the report module, but the "site" folder with the report 
ended up in frontend and backend target folders. This was not a problem in the project before zipping it.


## How to use
* Landing page: Contains a table of movies, with info, in the database. Each row has a button that will take you
to the "movie details page". 
* Header: Header contains a link back to the landing page. Always there.
* Footer: Contains a message, telling if you're logged in of not. When not, 
there is two buttons that will take you to either "login" og "signup". When logged in, 
there is a log out button there instead. If clicked you will be logged out and redirected to the landing page.
* Sign up: Field for username and password. Sign up button creates a user and redirects you to the landing page, logged in.
* Login: Field for username and password. Login button redirects you to the landing page, logged in.
* Movie details: Shows title of the movie, release date and a list of reviews. If logged in, there is a field to write a review, 
and a drop down menu to chose how many stars to rate it with. Create button creates the review and refreshes the list.
The sort button will sort the list by what ever chosen under (date/stars).

## Functionality
When clicking "movie details" a navigation bean will return an url with the movieId as a parameter. 
This is then used, as a variable in the xhtml, to generate information and reviews for the correct movie. 
When creating a review, the submit function takes the movieId from the parameter and the username from the userinfoController,
plus the information from the input field and the drop down list, and creates a review. It also catches exceptions and displays 
an error message on the page. Also done by using url parameters. ReviewId is a composite primary key of username and movieID 
to fulfill constraint "A user can only write one review per movie." Average stars in the table on the 
landing page are made backend by using an Object (MovieWithAvgStars) that takes a Movie object 
and a double which is avg stars made in MovieService. I also thought about doing it in the frontend by getting one list of movies 
and one list with avg star doubles and putting them together in the table, but I felt it was better to do it backend like I did.
I created one PageObject per jsf page, since that's how I understood the assignment. But I felt it was kinda unesessary 
since they all do almost the same, except for MovieDetailsPo where I made some extra methods to navigate the page.  




## Parts/Tasks
* [x] R1: Backend
    * [x] User: having info like name, surname, hashed-password, email, etc.
    * [x] Movie: having info like title, director, year of release, etc.
    * [x] Review: having info like the target movie, the actual review text, the user author, when the 
      review was created, etc.
    * Services should:
    * [x] create a user
    * [x] create/delete a movie
    * [x] add review to a movie, with a 1 to 5 star
    * [x] compute average of stars per movie
    * [x] retrieve all movies, sorted by average stars
    * [x] retrieve all reviews for a movie, sorted by either stars or creation time of the review

* [x] R2: Testing of backend
* [x] R3: Frontend
    * [x] Homepage: display all movies (info summaries). If the user is logged in, then display a welcome 
          message. Each movie entry must show the average stars it has, and link to a “movie details” 
          page.
    * [x] Movie details page: show all reviews and their stars. Give possibility to sort reviews by time and 
          by stars. If the user is logged in, enable possibility to write a new review. A user can only write 
          one review per movie.
    * [x] User login/signup page, based on Spring Security and storing of user info on the SQL database. It 
          should be possible to logout from any of the pages (e.g., via a button). When a login/signup fails, 
          you MUST show an error message. 
* [x] R4: Selenium Tests
    * [x] testDefaultMovies: go to home page, and verify that at least 2 movies are displayed (there 
          should be at least 2 movies by default initialized in the database).
    * [x] testWriteReview: go to movie details page for a movie, and verify you cannot write a review for 
          it. Log in. Go back to the same movie details page, and add a new review. Verify the review is 
          visible. Log out. Verify the review is still visible. 
    * [x] testStars: check current stars for a movie on home page. Write new review for it, and give a star 
          which must lead to a different average. Go back to home page. Verify that the average stars for 
          that movie has changed.
    * [x] testSorting: go to a movie details page, and write 3 reviews with 3 different authors, giving 
          different stars. Sort the display by time and by stars, and verify it is correctly sorted.
* [ ] R5: Extra
* I did not have time to do anything extra :(


## Copied/adapted code
A lot of code has been copied from the repo "https://github.com/arcuri82/testing_security_development_enterprise_systems".
All files with copied code, one copied line or more, has been marked with a comment saying so. 
All the code has been read and understood, before copying.
 
### Comment
When I ran the application on my computer I used postgres DB. When reading the exam it seemed to me that I should use
h2 as default when delivering the exam since I am not sure If examinator has postgres locally or not. The yml settings I used for
postgres was: 

    jsf:                
      projectStage: Production
    
    spring:
      datasource:
        driver-class-name: "org.postgresql.Driver"
        url: "jdbc:postgresql://localhost:5432/postgres"
        username: postgres
        (password: password)
      jpa:
        database-platform: org.hibernate.dialect.PostgreSQLDialect
        show-sql: true
        hibernate:
          ddl-auto: validate


So its possible to swap it out if you want persistent data.

