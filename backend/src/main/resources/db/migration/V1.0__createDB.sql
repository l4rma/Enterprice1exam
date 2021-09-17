CREATE SEQUENCE entity_id_sequence START 1 INCREMENT 1;

CREATE TABLE movie
  (
     id              INT8 NOT NULL,
     director        VARCHAR(128),
     genre           VARCHAR(128),
     title           VARCHAR(128),
     year_of_release INT8 NOT NULL CHECK (year_of_release>=1888 AND
     year_of_release<=
     3000),
     PRIMARY KEY (id)
  );

CREATE TABLE review
  (
     date_created  TIMESTAMP NOT NULL,
     review_text   VARCHAR(128),
     stars         INT8 NOT NULL CHECK (stars<=5 AND stars>=1),
     user_username VARCHAR(255) NOT NULL,
     movie_id      INT8 NOT NULL,
     PRIMARY KEY (movie_id, user_username)
  );

CREATE TABLE user_roles
  (
     user_username VARCHAR(255) NOT NULL,
     roles         VARCHAR(255)
  );

CREATE TABLE users
  (
     username VARCHAR(255) NOT NULL,
     enabled  BOOLEAN NOT NULL,
     PASSWORD VARCHAR(255),
     PRIMARY KEY (username)
  );

ALTER TABLE review
  ADD CONSTRAINT foreign_key_username FOREIGN KEY (user_username) REFERENCES
  users;

ALTER TABLE review
  ADD CONSTRAINT foreign_key_movie_id FOREIGN KEY (movie_id) REFERENCES movie;

ALTER TABLE user_roles
  ADD CONSTRAINT foreign_key_user_username FOREIGN KEY (user_username)
  REFERENCES users; 