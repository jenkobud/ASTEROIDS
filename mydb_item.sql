-- MySQL dump 10.13  Distrib 5.6.24, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: mydb
-- ------------------------------------------------------
-- Server version	5.5.24-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `X` float NOT NULL,
  `Y` float NOT NULL,
  `Accion` tinyint(1) NOT NULL,
  `Bonus` int(11) NOT NULL,
  `Platform_NroLvl` int(11) NOT NULL,
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (139.368,113.454,1,2,1,1),(320.253,343.083,1,2,1,2),(699.622,327.821,1,2,1,3),(218.643,251.078,1,2,1,4),(744.115,65.5042,1,2,1,5),(858.094,205.225,1,2,1,6),(14.0195,340.196,0,2,1,7),(185.132,81.139,1,2,1,8),(305.456,495.771,1,2,1,9),(139.368,113.454,1,2,2,10),(320.253,343.083,1,2,2,11),(699.622,327.821,1,2,2,12),(218.643,251.078,1,2,2,13),(744.115,65.5042,1,2,2,14),(858.094,205.225,1,2,2,15),(14.0195,340.196,0,2,2,16),(185.132,81.139,1,2,2,17),(305.456,495.771,1,2,2,18),(36.8791,395.429,0,2,3,19),(104.638,230.791,0,2,3,20),(573.309,520.285,1,2,3,21),(539.954,41.9073,1,2,3,22),(775.596,362.348,0,2,3,23),(155.88,153.073,1,2,3,24),(879.657,99.9026,0,2,3,25),(985.997,214.52,0,2,3,26),(166.092,63.372,0,2,3,27),(921.451,390.978,1,2,3,28),(689.459,159.79,0,2,3,29),(296.489,96.0507,1,2,3,30),(213.292,82.7186,1,2,3,31),(886.281,355.078,1,2,3,32),(589.752,196.143,1,2,3,33),(918.325,362.556,1,2,3,34),(599.288,401.572,0,2,3,35),(848.339,482.409,0,2,3,36),(78.915,216.979,0,2,3,37),(655.852,496.334,1,2,3,38),(686.356,252.923,0,2,3,39);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-10-01  9:25:36
