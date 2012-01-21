drop database if exists salvideodatabase;
create database salvideodatabase;

DROP TABLE IF EXISTS salvideodatabase.elements;
CREATE TABLE  salvideodatabase.elements (
  id int(10) NOT NULL AUTO_INCREMENT,
  Name varchar(255),
  Author varchar(255),
  Description varchar(255),
  Timestamp timestamp,
  Latitude double,
  Longitude double,
  Tags varchar(255),
  Path varchar(255),
  PRIMARY KEY (id)
);

INSERT INTO salvideodatabase.elements (
  Name,Author,Description, Timestamp, Latitude, Longitude, Tags, Path)
  VALUES
  ('Hills.wmv','Ashok', 'Blue Hills is a nice short video', '2009-10-18 16:10:00', 30, -120, 'Blue Hills','c:\\SALvideo\\Hills.wmv'),
  ('Lilies.wmv','Peter', 'Water Lilies is a nice short video', '2009-10-18 16:11:00', 31, -121, 'Water Lilies','c:\\SALvideo\\Lilies.wmv'),
  ('Sunset.wmv','Kevin', 'Sunset is a nice short video', '2009-10-18 16:12:00', 32, -122, 'Sunset Sun','c:\\SALvideo\\Sunset.wmv'),
  ('Winter.wmv','Ram', 'Winter is a nice short video', '2009-10-18 16:13:00', 33, -123, 'Winter','c:\\SALvideo\\Winter.wmv');
