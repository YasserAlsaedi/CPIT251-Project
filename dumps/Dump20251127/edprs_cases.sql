-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: edprs
-- ------------------------------------------------------
-- Server version	9.5.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT. */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS. */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '942e6b7d-be52-11f0-9a4a-089798e33a5d:1-812';

--
-- Table structure for table `cases`
--

DROP TABLE IF EXISTS `cases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cases` (
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `relative_present` bit(1) NOT NULL,
  `assigned_unit_id` bigint DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(2000) DEFAULT NULL,
  `caller_phone` varchar(255) DEFAULT NULL,
  `case_number` varchar(255) NOT NULL,
  `llocation` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `mrn` varchar(255) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `urgency` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_d2x5t06l1d3krie16abr38r0y` (`case_number`),
  KEY `FK5hm050dboxrp84xw36q2tdetx` (`assigned_unit_id`),
  CONSTRAINT `FK5hm050dboxrp84xw36q2tdetx` FOREIGN KEY (`assigned_unit_id`) REFERENCES `units` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cases`
--

LOCK TABLES `cases` WRITE;
/*!40000 ALTER TABLE `cases` DISABLE KEYS */;
INSERT INTO `cases` VALUES (NULL,NULL,_binary '\0',1,'2025-11-25 15:33:26.195379',3,'fafa','efae','CASE-1764084806195',NULL,'fafa','fef','Completed','Emergency','Critical'),(NULL,NULL,_binary '\0',2,'2025-11-25 15:43:41.323754',7,'cscvs','scs','CASE-1764085421323',NULL,'vscc','scsc','Completed','Emergency','Critical'),(NULL,NULL,_binary '\0',1,'2025-11-25 15:47:02.370097',9,'scasc','ascas','CASE-1764085622370',NULL,'cascas','scas','Completed','Emergency','Critical'),(NULL,NULL,_binary '\0',2,'2025-11-26 03:39:40.035754',18,'grv','dvedv','CASE-1764128380035',NULL,'vr','dvdsv','Completed','Emergency','Critical'),(NULL,NULL,_binary '\0',1,'2025-11-26 03:45:05.579848',19,'vev','vvf','CASE-1764128705579',NULL,'ev','fvev','Completed','Emergency','Critical'),(NULL,NULL,_binary '\0',2,'2025-11-26 03:53:54.538999',21,'34f4','4r','CASE-1764129234538',NULL,'44','r34r','Completed','Emergency','Critical'),(NULL,NULL,_binary '\0',1,'2025-11-26 03:58:15.195300',23,'rev','erverv','CASE-1764129495195',NULL,'erverv','rvcerv','Completed','Emergency','Critical'),(21.6275,39.20619,_binary '\0',2,'2025-11-26 04:02:45.939009',24,'wd','dde','CASE-1764129765939',NULL,'GPS: 21.62750, 39.20619','ede','Completed','Emergency','Critical'),(21.56209,39.16231,_binary '\0',2,'2025-11-26 04:06:56.092194',25,'3d34d','wed34','CASE-1764130016092',NULL,'GPS: 21.56209, 39.16231','ede','Completed','Emergency','Critical'),(21.5465,39.16128,_binary '\0',4,'2025-11-26 04:14:32.058717',26,'edec','refr','CASE-1764130472058',NULL,'GPS: 21.54650, 39.16128','fcerc','Completed','Emergency','Critical'),(21.56087,39.16643,_binary '\0',1,'2025-11-26 05:20:56.007754',27,'gfdf','0987654','CASE-1764134456007',NULL,'GPS: 21.56087, 39.16643','ttuf','Completed','Emergency','Critical'),(21.5366,39.18223,_binary '\0',3,'2025-11-26 16:49:57.611332',28,'fhrtdnd','09876543','CASE-1764175797611',NULL,'GPS: 21.53660, 39.18223','2423','Completed','Emergency','Critical'),(21.56464,39.16162,_binary '\0',1,'2025-11-26 16:50:56.939250',29,'gnfgnf','4636362','CASE-1764175856939',NULL,'GPS: 21.56464, 39.16162','6235','Completed','Emergency','Critical'),(21.58419,39.19491,_binary '\0',1,'2025-11-26 16:55:03.531282',30,'tfjtfhdfbfg','064253234','CASE-1764176103531',NULL,'GPS: 21.58419, 39.19491','2435','Completed','Emergency','Critical'),(21.55384,39.15482,_binary '\0',4,'2025-11-26 22:25:05.771811',31,'gfhd','53677','CASE-1764195905771',NULL,'GPS: 21.55384, 39.15482','35352','Completed','Emergency','Critical'),(21.53653,39.1981,_binary '\0',3,'2025-11-26 22:26:41.811431',32,'36tdgxdg','754635','CASE-1764196001811',NULL,'GPS: 21.53653, 39.19810','2356','Completed','Emergency','Critical'),(NULL,NULL,_binary '\0',3,'2025-11-26 22:27:45.139161',34,'jtjtf','577','CASE-1764196065139',NULL,'fjtj','5757','Completed','Emergency','Critical'),(21.55353,39.18601,_binary '\0',4,'2025-11-27 03:50:51.754148',35,'efef','dcd','CASE-1764215451754',NULL,'GPS: 21.55353, 39.18601','dc','Completed','Emergency','Critical');
/*!40000 ALTER TABLE `cases` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-27  7:17:10
