CREATE DATABASE  IF NOT EXISTS `sap` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `sap`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
<<<<<<< HEAD
-- Host: 127.0.0.1:3306
-- Generation Time: Jul 06, 2019 at 11:21 PM
-- Server version: 5.7.24
-- PHP Version: 7.2.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

=======
-- Host: 127.0.0.1    Database: sap
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.30-MariaDB
>>>>>>> 51eee38cd00788acbce0292c385f72ed15004a16

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
-- Table structure for table `afastamento`
--

DROP TABLE IF EXISTS `afastamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `afastamento` (
  `Tipo` enum('Pessoal','Saude') NOT NULL,
  `Data_inicio` date NOT NULL,
  `Data_Termino` date DEFAULT NULL,
  `Motivo` varchar(50) NOT NULL,
  `FK_CPF_professor` int(11) NOT NULL,
  PRIMARY KEY (`FK_CPF_professor`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `afastamento`
--

LOCK TABLES `afastamento` WRITE;
/*!40000 ALTER TABLE `afastamento` DISABLE KEYS */;
/*!40000 ALTER TABLE `afastamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curso`
--

DROP TABLE IF EXISTS `curso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curso` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(30) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `nome` (`nome`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curso`
--

LOCK TABLES `curso` WRITE;
/*!40000 ALTER TABLE `curso` DISABLE KEYS */;
/*!40000 ALTER TABLE `curso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `disciplina`
--

DROP TABLE IF EXISTS `disciplina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `disciplina` (
  `ID` int(5) NOT NULL AUTO_INCREMENT,
<<<<<<< HEAD
  `Codigo` varchar(10) NOT NULL,
  `Nome` varchar(100) NOT NULL,
  `Area` enum('ARC','FC','ENSISO','EXTERNA') DEFAULT NULL,
  `Tipo` enum('OBRIGATORIA','OPTATIVA','ELETIVA') DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Nome` (`Nome`)
) ENGINE=MyISAM AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;
=======
  `Tipo` enum('Obrigatoria','Externa','Optativa') NOT NULL,
  `Codigo` varchar(10) NOT NULL,
  `Nome` varchar(100) NOT NULL,
  `Area` enum('ARC','FC','ENSISO','EXTERNA') DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Nome` (`Nome`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
>>>>>>> 51eee38cd00788acbce0292c385f72ed15004a16

--
-- Dumping data for table `disciplina`
--

<<<<<<< HEAD
INSERT INTO `disciplina` (`ID`, `Codigo`, `Nome`, `Area`, `Tipo`) VALUES
(1, '06418', 'ÁLGEBRA VETORIAL E LINEAR PARA COMPUTAÇÃO', 'EXTERNA', 'OBRIGATORIA'),
(2, '06507', 'CÁLCULO NI', 'EXTERNA', 'OBRIGATORIA'),
(3, '14044', 'INTRODUÇÃO À CIÊNCIA DA COMPUTAÇÃO', 'ENSISO', 'OBRIGATORIA'),
(4, '14117', 'INTRODUÇÃO À PROGRAMAÇÃO I', 'ENSISO', 'OBRIGATORIA'),
(5, '14203', 'MATEMÁTICA DISCRETA I', 'FC', 'OBRIGATORIA'),
(6, '06214', 'ALGORITMOS E ESTRUTURAS DE DADOS', 'ENSISO', 'OBRIGATORIA'),
(7, '06508', 'CÁLCULO NII', 'EXTERNA', 'OBRIGATORIA'),
(8, '14118', 'INTRODUÇÃO À PROGRAMAÇÃO II', 'ENSISO', 'OBRIGATORIA'),
(9, '14204', 'MATEMÁTICA DISCRETA II', 'FC', 'OBRIGATORIA'),
(10, '14112', 'METODOLOGIA CIENTÍFICA APLICADA À COMPUTAÇÃO', 'ENSISO', 'OBRIGATORIA'),
(11, '14063', 'CIRCUITOS DIGITAIS', 'ARC', 'OBRIGATORIA'),
(12, '06243', 'ESTATÍSTICA EXPLORATÓRIA I', 'EXTERNA', 'OBRIGATORIA'),
(13, '06325', 'FÍSICA APLICADA À COMPUTAÇÃO', 'EXTERNA', 'OBRIGATORIA'),
(14, '14087', 'PROJETO E ANÁLISE DE ALGORITMOS', 'ENSISO', 'OBRIGATORIA'),
(15, '06223', 'TEORIA DA COMPUTAÇÃO', 'FC', 'OBRIGATORIA'),
(16, '14064', 'ARQUITETURA E ORGANIZAÇÃO DE COMPUTADORES', 'ARC', 'OBRIGATORIA'),
(17, '14088', 'BANCO DE DADOS S', 'ENSISO', 'OBRIGATORIA'),
(18, '06226', 'ENGENHARIA DE SOFTWARE', 'ENSISO', 'OBRIGATORIA'),
(19, '06252', 'PARADIGMAS DE PROGRAMAÇÃO', 'FC', 'OBRIGATORIA'),
(20, '14058', 'REDES DE COMPUTADORES', 'ARC', 'OBRIGATORIA'),
(21, '14090', 'COMPILADORES', 'ARC', 'OBRIGATORIA'),
(22, '14074', 'INTELIGÊNCIA ARTIFICIAL', 'FC', 'OBRIGATORIA'),
(23, '06251', 'PROJETO DE DESENVOLVIMENTO DE SOFTWARE', 'ENSISO', 'OBRIGATORIA'),
(24, '14059', 'SISTEMAS DISTRIBUÍDOS', 'ARC', 'OBRIGATORIA'),
(25, '14065', 'SISTEMAS OPERACIONAIS', 'ARC', 'OBRIGATORIA');

-- --------------------------------------------------------
=======
LOCK TABLES `disciplina` WRITE;
/*!40000 ALTER TABLE `disciplina` DISABLE KEYS */;
/*!40000 ALTER TABLE `disciplina` ENABLE KEYS */;
UNLOCK TABLES;
>>>>>>> 51eee38cd00788acbce0292c385f72ed15004a16

--
-- Table structure for table `horario_disciplinas`
--

DROP TABLE IF EXISTS `horario_disciplinas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `horario_disciplinas` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `numero` int(11) NOT NULL,
  `FK_ID_disciplinas` int(11) NOT NULL,
  `FK_ID_slots` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `horario_disciplinas_fk_ibfk_1` (`FK_ID_disciplinas`),
  KEY `horario_disciplinas_fk_ibfk_2` (`FK_ID_slots`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
<<<<<<< HEAD
=======
-- Dumping data for table `horario_disciplinas`
--

LOCK TABLES `horario_disciplinas` WRITE;
/*!40000 ALTER TABLE `horario_disciplinas` DISABLE KEYS */;
/*!40000 ALTER TABLE `horario_disciplinas` ENABLE KEYS */;
UNLOCK TABLES;

--
>>>>>>> 51eee38cd00788acbce0292c385f72ed15004a16
-- Table structure for table `ministra`
--

DROP TABLE IF EXISTS `ministra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ministra` (
  `FK_CPF_professor` int(11) NOT NULL,
  `FK_ID_disciplina` int(5) NOT NULL,
  PRIMARY KEY (`FK_CPF_professor`,`FK_ID_disciplina`),
  KEY `FK_ID_disciplina` (`FK_ID_disciplina`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ministra`
--

LOCK TABLES `ministra` WRITE;
/*!40000 ALTER TABLE `ministra` DISABLE KEYS */;
/*!40000 ALTER TABLE `ministra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `periodo`
--

DROP TABLE IF EXISTS `periodo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `periodo` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FK_ID_curso` int(11) NOT NULL,
  `FK_ID_slot` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `horario_disciplinas_idfk_1` (`FK_ID_curso`),
  KEY `horario_disciplinas_idfk_2` (`FK_ID_slot`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `periodo`
--

LOCK TABLES `periodo` WRITE;
/*!40000 ALTER TABLE `periodo` DISABLE KEYS */;
/*!40000 ALTER TABLE `periodo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prerequisito`
--

DROP TABLE IF EXISTS `prerequisito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prerequisito` (
  `FK_ID_disciplina` int(5) NOT NULL,
  `FK_ID_disciplina_pre` int(5) NOT NULL,
  PRIMARY KEY (`FK_ID_disciplina`,`FK_ID_disciplina_pre`),
  KEY `FK_ID_disciplina_pre` (`FK_ID_disciplina_pre`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prerequisito`
--

LOCK TABLES `prerequisito` WRITE;
/*!40000 ALTER TABLE `prerequisito` DISABLE KEYS */;
/*!40000 ALTER TABLE `prerequisito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professor`
--

DROP TABLE IF EXISTS `professor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `professor` (
  `CPF` int(11) NOT NULL,
  `Nome` varchar(40) NOT NULL,
  `Area` enum('FC','Arc','Ensiso') NOT NULL,
  `FK_Disciplina_Preferencia_1` int(5) DEFAULT NULL,
  `FK_Disciplina_Preferencia_2` int(5) DEFAULT NULL,
  `login` varchar(30) NOT NULL,
  `cargo` enum('PROFESSOR','COORDENADOR','SUPERVISOR') DEFAULT NULL,
  `senha` varchar(150) NOT NULL,
  PRIMARY KEY (`CPF`),
  UNIQUE KEY `login` (`login`),
  KEY `FK_Disciplina_Preferencia_1` (`FK_Disciplina_Preferencia_1`),
  KEY `FK_Disciplina_Preferencia_2` (`FK_Disciplina_Preferencia_2`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professor`
--

LOCK TABLES `professor` WRITE;
/*!40000 ALTER TABLE `professor` DISABLE KEYS */;
INSERT INTO `professor` VALUES (1232,'NOME','Ensiso',NULL,NULL,'prof','PROFESSOR','$2a$10$NktDIbpJMyAv2yNzuipqzu4kSGy.8jYtYVsRV140MAyR8VSrvlRR.'),(1234,'rodemarck','Ensiso',NULL,NULL,'rode','PROFESSOR','$2a$10$xu8K04NpPutppxYwdgLrcuFAF3ptgyCsEKSRMP6WF7F.Wy0UnRo4i');
/*!40000 ALTER TABLE `professor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slots`
--

DROP TABLE IF EXISTS `slots`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `slots` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slots`
--

LOCK TABLES `slots` WRITE;
/*!40000 ALTER TABLE `slots` DISABLE KEYS */;
/*!40000 ALTER TABLE `slots` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-07-02 17:56:13
