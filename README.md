# gaming4life2017

Gaming for Life 2017 Clojure project was generated using Luminus framework version "2.9.11.38"
It uses H2 Embedded Database Engine version "1.4.193" for database access, Clojure programming language for backend and Bootstrap framework and Javascript for frontend development.

## Prerequisites

You will need [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

These are steps for running the application:

1) Navigate to your project directory and execute the following command in the command line:

    export DATABASE_URL="jdbc:h2:./gaming4life2017_dev.db"

2) Run migrations for the database: using lein run migrate

    lein run migrate

3) To start a web server for the application, run:

    lein run

## About the Project

The aim of this project was to create an online store store for selling video games and even more so to populate a positive aspect of playing video games.

Gaming has been one of the many hobbies of my generation that is not understood and even frowned upon by the adults.

Contrary to their belief it is a very healthy hobby that develops problem-solving skills, enhances creativity and even helps in stimulating the growth of new neurons, as scientist suggest. It is also well-known that gamers develop better hand-eye coordination and navigation in the environment, are better at decision-making and learn early on how much teamwork is important.

As I am not much of a writer, I will go in with the techical stuff.

This project consists of the following frontend pages:

1) Login and register page - it is a user starting point. The user has to be logged in to access almost all of the other pages. If the user has no accout he can register a new one.

2) Home page - a static page with some very nice articals about popular games.

3) About page - consists of a javascript google map with an imaginary location of the Gaming for Life headquarters and a "About us" text that might as well been in Chinese as nobody is really gonna read it.

4) Contact page - This page allows the user to contact the Gaming for Life workforce. It sends a message to the specified mail address and a message to the user by using Clojure postal.core.

5) Products page - lists all of the products from the database, sorting them by name. User can also filter only games, consoles of other gaming equipment and search for a specific pattern in the name. All the products can be added to the user cart.

6) User profile - shows user information. User can change profile picture and enter information about oneself.

7) Cart - Shows products that user added to his/her cart. User can proceed to checkout with his products, remove a product from the cart and clear the whole cart

Furthermore, the user can also change his password and logout.

In addition to these, user that is an admin can also:

1) Add a new product and
2) Delete an existing product

The application was written in Clojure programming language, using Luminus framework, Compojure and H2 Database Engine.
The following dependencies were added to the project:

    [com.h2database/h2 "1.4.193"]
    [compojure "1.5.2"]
    [conman "0.6.3"]
    [cprop "0.1.10"]
    [funcool/struct "1.0.0"]
    [luminus-immutant "0.2.3"]
    [luminus-migrations "0.3.0"]
    [luminus-nrepl "0.1.4"]
    [markdown-clj "0.9.98"]
    [metosin/ring-http-response "0.8.2"]
    [mount "0.1.11"]
    [org.clojure/clojure "1.8.0"]
    [org.clojure/tools.cli "0.3.5"]
    [org.clojure/tools.logging "0.3.1"]
    [org.webjars/bootstrap "4.0.0-alpha.5"]
    [org.webjars/jquery "3.1.1"]
    [org.webjars/webjars-locator-jboss-vfs "0.1.0"]
    [ring-middleware-format "0.7.2"]
    [ring-webjars "0.1.1"]
    [ring/ring-core "1.6.0-RC1"]
    [ring/ring-defaults "0.2.3"]
    [bouncer "1.0.0"]
    [com.draines/postal "2.0.2"]
    [buddy "1.3.0"]
    [selmer "1.10.6"]

The project was developed as part of the assignment for the course Software Engineering Tools and Methodology at the Faculty of Organization Sciences, University of Belgrade, Serbia. It was based on the project [Gaming for Life] (https://github.com/hachiko93/gaming-for-life) developed in PHP during my bachelor studies.
