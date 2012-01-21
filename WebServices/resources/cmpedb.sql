drop database if exists lab2storage;
create database lab2storage;

DROP TABLE IF EXISTS lab2storage.information;
CREATE TABLE  lab2storage.information (
  id int(10) NOT NULL AUTO_INCREMENT,
  author varchar(255) NOT NULL,
  itime DATETIME DEFAULT NULL,
  lat varchar(255),
  lon varchar(255),
  tags varchar(255),
  type varchar(255),
  ipath varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO lab2storage.information (
  author,itime,lat, lon, tags, type, ipath)
  VALUES
  	("Peter", "2008-11-2-2-34-49", "55.0", "98.3", "cab", "jpeg","c:\\image\\cab.jpg"),
  	("Manson", "2009-5-2-4-45-12", "52.0", "48.3", "circuit", "jpeg","c:\\image\\circuit.jpg"),
  	("Mat", "2009-6-8-8-45-22", "103.0", "934.3", "City_Hall", "jpeg","c:\\image\\City_Hall.jpg"),
  	("Mohan", "2003-1-3-5-45-32", "73.0", "933.3", "city-of-angeles", "jpeg","c:\\image\\city-of-angeles.jpg"),
  	("Kevin", "2005-5-30-11-23-52", "743.0", "263.3", "new_york_city", "jpeg","c:\\image\\new_york_city.jpg"),
	("Dennis", "2007-7-16-9-45-32", "63.0", "823.3", "new-york-city", "gif","c:\\image\\city-of-angeles.gif"),
	("Ross", "2004-12-25-12-32-23", "733.0", "233.3", "Skyline", "jpeg","c:\\image\\Skyline.jpg"),
	("Ashok", "2002-11-6-10-45-32", "543.0", "623.3", "Sunrise", "jpeg","c:\\image\\Sunrise.jpg"),
	("Ashok", "2001-5-12-3-42-16", "923.0", "833.3", "sunset", "jpeg","c:\\image\\sunset.jpg"),
  	("Ram", "2009-3-16-12-53-34", "823.0", "963.3", "tree", "jpeg","c:\\image\\tree.jpg");

drop database if exists lab2document;
create database lab2document;

DROP TABLE IF EXISTS lab2document.document;
CREATE TABLE  lab2document.document (
  id int(10) NOT NULL AUTO_INCREMENT,
  author varchar(255) NOT NULL,
  itime DATETIME DEFAULT NULL,
  lat varchar(255),
  lon varchar(255),
  tags varchar(255),
  type varchar(255),
  ipath varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO lab2document.document (
  author,itime,lat, lon, tags, type, ipath)
  VALUES
  	("Peter", "2008-11-2-2-34-49", "55.0", "98.3", "bearing", "pdf","c:\\document\\bearing.pdf"),
  	("Manson", "2009-5-2-4-45-12", "52.0", "48.3", "chicken", "pdf","c:\\document\\chicken.pdf"),
  	("Mat", "2009-6-8-8-45-22", "103.0", "934.3", "mat", "pdf","c:\\document\\mat.pdf"),
  	("Mohan", "2003-1-3-5-45-32", "73.0", "933.3", "himanchu", "pdf","c:\\document\\himanchu.pdf" ),
  	("Kevin", "2005-5-30-11-23-52", "743.0", "263.3", "football", "pdf","c:\\document\\football.pdf"),
    ("Dennis", "2007-7-16-9-45-32", "63.0", "823.3", "shalin", "pdf","c:\\document\\shalin.pdf"),
    ("Ross", "2004-12-25-12-32-23", "733.0", "233.3", "chang", "pdf","c:\\document\\chang.pdf"),
    ("Ashok", "2002-11-6-10-45-32", "543.0", "623.3", "neighbor", "pdf","c:\\document\\neighbor.pdf"),
    ("Ashok", "2001-5-12-3-42-16", "923.0", "833.3", "knot", "docx","c:\\document\\knot.docx"),
    ("Ram", "2009-3-16-12-53-34", "823.0", "963.3", "plan", "docx","c:\\document\\plan.docx");

drop database if exists lab2site;
create database lab2site;

DROP TABLE IF EXISTS lab2site.site;
CREATE TABLE  lab2site.site (
  id int(10) NOT NULL AUTO_INCREMENT,
  author varchar(255) NOT NULL,
  itime DATETIME DEFAULT NULL,
  lat varchar(255),
  lon varchar(255),
  tags varchar(255),
  type varchar(255),
  ipath varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO lab2site.site (
  author,itime,lat, lon, tags, type, ipath)
  VALUES
  ("Peter", "2008-11-2-2-34-49", "55.0", "98.3", "Peter", "htm","c:\\site\\Peter.htm"),
  ("Manson", "2009-5-2-4-45-12", "52.0", "48.3", "Manson", "htm","c:\\site\\Manson.htm"),
  ("Mat", "2009-6-8-8-45-22", "103.0", "934.3", "Mat", "htm","c:\\site\\Mat.htm"),
  ("Mohan", "2003-1-3-5-45-32", "73.0", "933.3", "Mohan", "htm","c:\\site\\Mohan.htm"),
  ("Kevin", "2005-5-30-11-23-52", "743.0", "263.3", "Kevin", "htm","c:\\site\\Kevin.htm"),
  ("Dennis", "2007-7-16-9-45-32", "63.0", "823.3", "Dennis", "htm","c:\\site\\Dennis.htm"),
  ("Ross", "2004-12-25-12-32-23", "733.0", "233.3", "Ross", "htm","c:\\site\\Ross.htm"),
  ("Ashok", "2002-11-6-10-45-32", "543.0", "623.3", "Ashok", "htm","c:\\site\\Ashok.htm"),
  ("Ashok", "2001-5-12-3-42-16", "923.0", "833.3", "Ashoka", "htm","c:\\site\\Ashoka.htm"),
  ("Ram", "2009-3-16-12-53-34", "823.0", "963.3", "Ram", "htm","c:\\site\\Ram.htm");

DROP TABLE IF EXISTS lab2video.video;
CREATE TABLE  lab2video.video (
  id int(10) NOT NULL AUTO_INCREMENT,
  author varchar(255) NOT NULL,
  itime DATETIME DEFAULT NULL,
  lat varchar(255),
  lon varchar(255),
  tags varchar(255),
  type varchar(255),
  ipath varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO lab2video.video (
  author,itime,lat, lon, tags, type, ipath)
  VALUES
  ("Peter", "2008-11-2-2-34-49", "55.0", "98.3", "bearing", "3GP","c:\\video\\bearing.3GP"),
  ("Manson", "2009-5-2-4-45-12", "52.0", "48.3", "cheetah", "3GP","c:\\video\\cheetah.3GP"),
  ("Manson", "2009-5-2-4-45-12", "52.0", "48.3", "cheetah", "3GP","c:\\video\\cheetah.3GP"),
  ("Mohan", "2003-1-3-5-45-32", "73.0", "933.3", "football", "3GP","c:\\video\\football.3GP"),
  ("Kevin", "2005-5-30-11-23-52", "743.0", "263.3", "hockey", "3GP","c:\\video\\hockey.3GP"),
  ("Dennis", "2007-7-16-9-45-32", "63.0", "823.3", "monster", "3GP","c:\\video\\monster.3GP"),
  ("Ross", "2004-12-25-12-32-23", "733.0", "233.3", "neighbor", "3GP","c:\\video\\neighbor.3GP"),
  ("Ashok", "2002-11-6-10-45-32", "543.0", "623.3", "seul", "3GP","c:\\video\\seul.3GP"),
  ("Ashok", "2001-5-12-3-42-16", "923.0", "833.3", "skeleton", "3GP","c:\\video\\skeleton.3GP"),
  ("Ram", "2009-3-16-12-53-34", "823.0", "963.3", "titanic", "MP4","c:\\video\\titanic.MP4");


DROP TABLE IF EXISTS lab2lookup.serverinfo;
CREATE TABLE  lab2lookup.serverinfo (
  id int(10) NOT NULL AUTO_INCREMENT,
  server varchar(255) NOT NULL,
  url varchar(255),
  status varchar(255),
  PRIMARY KEY (id)
);

INSERT INTO lab2lookup.serverinfo (
  server,url,status)
  VALUES
	("ImageServer", "http://localhost:8080/axis2/services/Search", "YES"),
	("DocumentServer", "http://localhost:8081/axis2/services/Document", "YES"),
    ("VideoServer", "http://localhost:8082/axis2/services/Video", "YES"),
    ("SiteServer", "http://localhost:8083/axis2/services/Site", "YES");

DROP TABLE IF EXISTS salvideodatabase.elements;
CREATE TABLE  salvideodatabase.elements (
  id int(10) NOT NULL AUTO_INCREMENT,
  author varchar(255) NOT NULL,
  itime DATETIME DEFAULT NULL,
  lat varchar(255),
  lon varchar(255),
  tags varchar(255),
  type varchar(255),
  ipath varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO salvideodatabase.elements (
  author,itime,lat, lon, tags, type, ipath)
  VALUES
  ("Peter", "2008-11-2-2-34-49", "55.0", "98.3", "hills", "wmv","c:\\salvideo\\Hills.wmv"),
  ("Ashok", "2009-5-2-4-45-12", "52.0", "48.3", "lilies", "wmv","c:\\salvideo\\Lilies.wmv"),
  ("Manson", "2009-5-2-4-45-12", "52.0", "48.3", "sunset", "wmv","c:\\salvideo\\Sunset.wmv"),
  ("Ram", "2009-3-16-12-53-34", "823.0", "963.3", "winter", "wmv","c:\\salvideo\\Winter.wmv");

 
     
