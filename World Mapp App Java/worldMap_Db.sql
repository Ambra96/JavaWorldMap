CREATE DATABASE world_map;



USE world_map;



CREATE TABLE countries (

    id INT AUTO_INCREMENT PRIMARY KEY,

    name VARCHAR(100) NOT NULL,

    capital VARCHAR(100),

    population BIGINT,

    continent VARCHAR(50),

    coordinates VARCHAR(50)

);



INSERT INTO countries (name, capital, population, continent, coordinates)

VALUES

('Greece', 'Athens', 10720000, 'Europe', '100,200'),

('USA', 'Washington, D.C.', 331000000, 'North America', '300,100'),

('China', 'Beijing', 1444000000, 'Asia', '400,300'); 