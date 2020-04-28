-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: shop
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `producer` varchar(20) NOT NULL,
  `name` varchar(30) NOT NULL,
  `ports_num` int NOT NULL,
  `price` float NOT NULL,
  `device_desc` varchar(5000) DEFAULT NULL,
  `device_type` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Cisco','J9773A',24,2966.78,'Aruba 2530 Series Gigabit Ethernet devices are enterprise class, fully managed, Layer 2 edge switches delivering cost effective, reliable, and secure connectivity for business networks. Designed for entry level to midsize enterprise networks, these Gigabit Ethernet switches deliver full Layer 2 capabilities with enhanced access security, traffic prioritization, IPv6 host support, and optional PoE+. The 2530 24G PoE+ model has 24 x 10/100/1000 PoE enabled ports and four Gigabit SFP ports.',2),(2,'HPE','J9773A',24,2966.78,'Aruba 2530 Series Gigabit Ethernet devices are enterprise class, fully managed, Layer 2 edge switches delivering cost effective, reliable, and secure connectivity for business networks. Designed for entry level to midsize enterprise networks, these Gigabit Ethernet switches deliver full Layer 2 capabilities with enhanced access security, traffic prioritization, IPv6 host support, and optional PoE+. The 2530 24G PoE+ model has 24 x 10/100/1000 PoE enabled ports and four Gigabit SFP ports.',2),(3,'Cisco','EW5HUB',5,290.78,'Quick and easy networking for your PCs! Ready to run with the latest network hardware and software, the Linksys EW5HUB Ethernet 5-Port Workgroup Hub transforms your fleet of PCs into a high performance workgroup. Imagine sharing files, printers, Internet access, multimedia and more at speeds of up to 10 Mbps ! The five (5) 10BaseT ports makes installation as easy as plugging a twisted-pair cable into your hub and your computer. Built-in error detection automatically protects your data\'s integrity. Seven (7) easy-to-read LEDs instantly reveal your network\'s link status and line status, making troubleshooting easier than ever. Plus you\'ll have no problem expanding in the future - you can quickly and effortlessly connect the Linksys EW5HUB Ethernet 5-Port Workgroup Hub to other hubs and switches with its built-in uplink port. Ready to run right out of the box with Windows 95/98/2000/NT, Linux and more. The Linksys EW5HUB Ethernet 5-Port Workgroup Hub is the easiest and most efficient way to connect your PCs together. So grab yours today!',3),(6,'Moc','Energia',9,11,'Kawa',1);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-28  5:30:57
