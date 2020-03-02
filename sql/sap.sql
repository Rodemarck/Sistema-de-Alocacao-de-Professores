-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Tempo de geração: 02/03/2020 às 15:02
-- Versão do servidor: 10.4.10-MariaDB
-- Versão do PHP: 7.4.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `sap`
--
CREATE DATABASE IF NOT EXISTS `sap` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `sap`;

DELIMITER $$
--
-- Procedimentos
--
DROP PROCEDURE IF EXISTS `aplica_regras`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `aplica_regras` ()  BEGIN
    SET @query = CONCAT('SELECT * FROM disciplina WHERE ', (SELECT GROUP_CONCAT(filtro ORDER BY id ASC SEPARATOR ' AND ') FROM regras));
    PREPARE statement FROM @query;
    EXECUTE statement;
END$$

DROP PROCEDURE IF EXISTS `teste`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `teste` ()  BEGIN
	SELECT (SELECT Nome FROM professor) AS NUMU, (SELECT CPF FROM professor) AS IDE
    ;
END$$

DROP PROCEDURE IF EXISTS `troca_disciplinas`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `troca_disciplinas` (IN `idPeriodo` INT, IN `n1` INT, IN `n2` INT)  troca_disciplinas:BEGIN
    declare err VARCHAR (100);
    DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = err;
    END;
    START TRANSACTION;
    
    IF(idPeriodo not in (SELECT id from periodo))THEN
        SET err = (SELECT INSERT("periodo[$] não encontrado",9,1,idPeriodo));  
        SIGNAL SQLSTATE '45000';
        LEAVE troca_disciplinas;
    END IF;
    SET @id_n1 = (SELECT id FROM espaco WHERE periodo_id=idPeriodo AND numero=n1);
    SET @id_n2 = (SELECT id FROM espaco WHERE periodo_id=idPeriodo AND numero=n2);

    IF((@id_n1 IS NOT NULL) AND (@id_n2 IS NOT NULL))THEN
        UPDATE espaco SET numero=n1 WHERE id=@id_n2;
        UPDATE espaco SET numero=n2 WHERE id=@id_n1;
        COMMIT;
    ELSE
        SET err = (SELECT INSERT(INSERT("horarios de numeros[$] ou [$] não encontrado",28,1,n2),21,1,n1));
        SIGNAL SQLSTATE '45000';
    END IF;
END$$

--
-- Funções
--
DROP FUNCTION IF EXISTS `findByName_professor`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `findByName_professor` (`nome__` VARCHAR(250)) RETURNS TEXT CHARSET utf8 NO SQL
return (SELECT CONCAT('[',GROUP_CONCAT(json_object(
            'id',id,
            'nome',Nome,
            'login',login,
            'senha',senha,
            'cargo',cargo,
            'area',Area,
            'preferencias','[]'
        ) SEPARATOR ','),']') FROM professor WHERE login=nome__)$$

DROP FUNCTION IF EXISTS `get_alocacao`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `get_alocacao` (`professor__` INT, `periodo__` INT) RETURNS TEXT CHARSET utf8 NO SQL
IF (periodo__ IS NULL) THEN
	RETURN (SELECT CONCAT('[',
        GROUP_CONCAT(
            json_object(
                'id',id,
                'professor',get_professor(professor_id),
                'disciplina',get_disciplina(disciplina_id),
                'periodoId',periodo_id
            ) SEPARATOR ','
        ),']'
    ) FROM ministra WHERE professor_id=professor__ AND periodo_id=periodo__);
ELSE
	RETURN (SELECT CONCAT('[',
        GROUP_CONCAT(
            json_object(
                'id',id,
                'professor',get_professor(professor_id),
                'disciplina',get_disciplina(disciplina_id),
                'periodoId',periodo_id
            ) SEPARATOR ','
        ),']'
    ) FROM ministra WHERE professor_id=professor__);
END IF$$

DROP FUNCTION IF EXISTS `get_curso`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `get_curso` (`id__` INT) RETURNS TEXT CHARSET utf8 NO SQL
IF (id__ NOT IN (SELECT id from curso))THEN
	return null;
ELSE
	RETURN (SELECT json_object(
        'id',id,
        'nome',nome,
        'sigla',sigla
    )FROM curso
     where id=id__);
END IF$$

DROP FUNCTION IF EXISTS `get_disciplina`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `get_disciplina` (`idDisciplina` INT) RETURNS TEXT CHARSET utf8 NO SQL
get_disciplina:BEGIN    
    IF(idDisciplina NOT IN (SELECT id FROM disciplina)) THEN
    	return null;
    ELSE
    	RETURN (SELECT json_object(
            'id',id,
            'codigo',Codigo,
            'nome',nome,
            'area',area,
            'tipo',tipo,
            'prerequisito','[]'
        )
        FROM disciplina
        WHERE ID=idDisciplina);
    END IF;
END$$

DROP FUNCTION IF EXISTS `get_disciplinas`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `get_disciplinas` (`idDisciplina` INT) RETURNS TEXT CHARSET utf8 NO SQL
get_preerequisito_disciplinas:BEGIN    
    IF(idDisciplina NOT IN (SELECT id FROM disciplina)) THEN
    	return '{}';
    ELSE
    	RETURN (SELECT json_object(
            'id',id,
            'codigo',Codigo,
            'nome',nome,
            'area',area,
            'tipo',tipo,
            'prerequisito',get_prerequisito(id)
        )
        FROM disciplina
        WHERE ID=idDisciplina);
    END IF;
END$$

DROP FUNCTION IF EXISTS `get_espaco`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `get_espaco` (`id__` INT) RETURNS TEXT CHARSET utf8 NO SQL
IF(id__ NOT IN (SELECT id from espaco)) THEN
	return '{}';
ELSE
	RETURN(SELECT json_object(
        'id',id,
        'disciplina',get_disciplina(disciplina_id),
        'periodo_id',periodo_id,
        'numero',numero
    )FROM espaco
     WHERE id=id__);
END IF$$

DROP FUNCTION IF EXISTS `get_espacos`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `get_espacos` (`periodo__` INT) RETURNS TEXT CHARSET utf8 NO SQL
return (SELECT CONCAT('[',GROUP_CONCAT(get_espaco(id) SEPARATOR ','),']') from espaco WHERE periodo_id=periodo__ ORDER BY numero)$$

DROP FUNCTION IF EXISTS `get_ministra`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `get_ministra` (`id__` INT) RETURNS TEXT CHARSET utf8 NO SQL
RETURN (SELECT json_object(
    'id',id,
    'professor',get_professor(professor_id),
    'disciplina',get_discplina(disciplina_id),
    'periodo_id',periodo_id
) FROM ministra WHERE id=id__)$$

DROP FUNCTION IF EXISTS `get_periodo`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `get_periodo` (`id__` INT) RETURNS TEXT CHARSET utf8 NO SQL
IF (id__ NOT IN (SELECT id FROM periodo))THEN
	RETURN null;
ELSE
	RETURN (SELECT json_object(
        'id',id,
        'curso',get_curso(curso_id),
        'slot',get_slot(slots_id),
        'espacos',get_espacos(id),
        'nPeriodo',n_periodo,
        'anoLetivo',ano_letivo
    )FROM periodo
     WHERE id=id__);
END IF$$

DROP FUNCTION IF EXISTS `get_prerequisito`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `get_prerequisito` (`disciplina` INT) RETURNS LONGTEXT CHARSET utf8 NO SQL
return (SELECT CONCAT('[',GROUP_CONCAT(get_disciplina(FK_ID_disciplina_pre) SEPARATOR ','),']') from prerequisito WHERE FK_ID_disciplina = disciplina)$$

DROP FUNCTION IF EXISTS `get_professor`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `get_professor` (`id__` INT) RETURNS LONGTEXT CHARSET utf8 get_professor:BEGIN
    DECLARE err VARCHAR (100);
    DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = err;
    END;
    IF (id__ NOT IN (SELECT id FROM professor)) THEN
        SET err = (SELECT INSERT("professor com id[$]",18,1,id__));
        SIGNAL SQLSTATE '45000';
        LEAVE get_professor;
    ELSE
        return (SELECT json_object(
            'id',id,
            'nome',Nome,
            'login',login,
            'senha',senha,
            'cargo',cargo,
            'area',Area,
            'preferencias','[]'
        )
         FROM professor
         WHERE id=id__);
    END IF;  
END$$

DROP FUNCTION IF EXISTS `get_slot`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `get_slot` (`id__` INT) RETURNS TEXT CHARSET utf8 NO SQL
IF (id__ NOT IN (SELECT id FROM slots)) THEN
	RETURN '{}';
ELSE
	RETURN (SELECT json_object(
        'id',id,
        'turno',turno,
        'nome',nome,
        'subSlots',get_sub_slots(id)
    ) FROM slots
      WHERE id=id__);
END IF$$

DROP FUNCTION IF EXISTS `get_sub_slot`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `get_sub_slot` (`sub_slot_id` INT) RETURNS TEXT CHARSET utf8 NO SQL
IF (sub_slot_id NOT IN (SELECT id from sub_slot)) THEN
	return '{}';
ELSE
	return (SELECT json_object(
        'id',id,
        'slots_id',slots_id,
        'dia',dia,
        'horario',horario,
        'numero',numero
    ) FROM sub_slot
      WHERE id=sub_slot_id);
END IF$$

DROP FUNCTION IF EXISTS `get_sub_slots`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `get_sub_slots` (`id_slot` INT) RETURNS TEXT CHARSET utf8 NO SQL
return (SELECT CONCAT('[',GROUP_CONCAT(get_sub_slot(id) SEPARATOR ','),']') from sub_slot WHERE slots_id = id_slot)$$

DROP FUNCTION IF EXISTS `hello`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `hello` (`s` CHAR(20)) RETURNS CHAR(50) CHARSET latin1 IF (s IS NULL ) THEN
	RETURN 'nulao';
ELSE
	RETURN CONCAT('Hello, ',' ->',s,'!');
END IF$$

DROP FUNCTION IF EXISTS `list_cursos`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `list_cursos` () RETURNS TEXT CHARSET utf8 NO SQL
return (SELECT CONCAT('[',GROUP_CONCAT(json_object(
            'id',id,
            'nome',nome,
            'sigla',sigla
        ) SEPARATOR ','),']') from curso)$$

DROP FUNCTION IF EXISTS `list_ministra`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `list_ministra` (`periodo__` INT) RETURNS TEXT CHARSET utf8 NO SQL
RETURN (SELECT CONCAT(
    '[',
	GROUP_CONCAT(
        json_object(
            'id',id,
            'professor',get_professor(professor_id),
            'disciplina',get_disciplina(disciplina_id),
            'periodoId',periodo_id
        ) SEPARATOR ','
    )
    ,']'
) FROM ministra WHERE periodo_id=periodo__)$$

DROP FUNCTION IF EXISTS `list_periodos`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `list_periodos` (`id__` INT) RETURNS TEXT CHARSET utf8 NO SQL
return (SELECT CONCAT('[',GROUP_CONCAT(get_periodo(id) SEPARATOR ','),']') from periodo WHERE curso_id = id__)$$

DROP FUNCTION IF EXISTS `list_professores`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `list_professores` () RETURNS TEXT CHARSET utf8 NO SQL
return (SELECT CONCAT('[',GROUP_CONCAT(json_object(
            'id',id,
            'nome',Nome,
            'login',login,
            'senha',senha,
            'cargo',cargo,
            'area',Area,
            'preferencias','[]'
        ) SEPARATOR ','),']') from professor)$$

DROP FUNCTION IF EXISTS `sublist_periodo`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `sublist_periodo` (`curso__` INT, `ano__` INT) RETURNS TEXT CHARSET utf8 NO SQL
return (SELECT
        CONCAT('[',GROUP_CONCAT(json_object('id', id, 'nPeriodo', n_periodo) SEPARATOR ','),']')
    FROM
        periodo
    WHERE
        curso_id = curso__ AND ano_letivo = ano__)$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estrutura para tabela `afastamento`
--

DROP TABLE IF EXISTS `afastamento`;
CREATE TABLE IF NOT EXISTS `afastamento` (
  `Tipo` enum('Pessoal','Saude') NOT NULL,
  `Data_inicio` date NOT NULL,
  `Data_Termino` date DEFAULT NULL,
  `Motivo` varchar(50) NOT NULL,
  `FK_CPF_professor` int(11) NOT NULL,
  PRIMARY KEY (`FK_CPF_professor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tabela truncada antes do insert `afastamento`
--

TRUNCATE TABLE `afastamento`;
-- --------------------------------------------------------

--
-- Estrutura para tabela `curso`
--

DROP TABLE IF EXISTS `curso`;
CREATE TABLE IF NOT EXISTS `curso` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `sigla` varchar(10) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `nome` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Tabela truncada antes do insert `curso`
--

TRUNCATE TABLE `curso`;
--
-- Despejando dados para a tabela `curso`
--

INSERT INTO `curso` (`ID`, `nome`, `sigla`) VALUES
(1, 'Bacharel em Ciências da Computação', 'BCC'),
(2, 'licenciatura em computação', 'LC');

-- --------------------------------------------------------

--
-- Estrutura para tabela `disciplina`
--

DROP TABLE IF EXISTS `disciplina`;
CREATE TABLE IF NOT EXISTS `disciplina` (
  `ID` int(5) NOT NULL AUTO_INCREMENT,
  `Codigo` varchar(10) NOT NULL,
  `Nome` varchar(100) NOT NULL,
  `Area` enum('ARC','FC','ENSISO','EXTERNA') DEFAULT NULL,
  `Tipo` enum('OBRIGATORIA','OPTATIVA','ELETIVA') DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Nome` (`Nome`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

--
-- Tabela truncada antes do insert `disciplina`
--

TRUNCATE TABLE `disciplina`;
--
-- Despejando dados para a tabela `disciplina`
--

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

--
-- Estrutura para tabela `espaco`
--

DROP TABLE IF EXISTS `espaco`;
CREATE TABLE IF NOT EXISTS `espaco` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `disciplina_id` int(11) NOT NULL,
  `periodo_id` int(11) NOT NULL,
  `numero` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_discplinaid` (`disciplina_id`),
  KEY `fk_periodoid` (`periodo_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

--
-- Tabela truncada antes do insert `espaco`
--

TRUNCATE TABLE `espaco`;
--
-- Despejando dados para a tabela `espaco`
--

INSERT INTO `espaco` (`id`, `disciplina_id`, `periodo_id`, `numero`) VALUES
(1, 1, 1, 3),
(2, 2, 1, 5),
(3, 3, 1, 2),
(4, 4, 1, 1),
(5, 5, 1, 4),
(6, 6, 2, 1),
(7, 7, 2, 2),
(8, 8, 2, 3),
(9, 9, 2, 4),
(10, 10, 2, 5),
(11, 18, 5, 3),
(12, 21, 5, 2),
(13, 14, 5, 1),
(14, 25, 5, 4),
(15, 7, 5, 5);

-- --------------------------------------------------------

--
-- Estrutura para tabela `ministra`
--

DROP TABLE IF EXISTS `ministra`;
CREATE TABLE IF NOT EXISTS `ministra` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `professor_id` int(11) NOT NULL,
  `disciplina_id` int(11) NOT NULL,
  `periodo_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_professor_id` (`professor_id`),
  KEY `fk_disciplina_id` (`disciplina_id`),
  KEY `fk_periodo_id` (`periodo_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Tabela truncada antes do insert `ministra`
--

TRUNCATE TABLE `ministra`;
--
-- Despejando dados para a tabela `ministra`
--

INSERT INTO `ministra` (`id`, `professor_id`, `disciplina_id`, `periodo_id`) VALUES
(1, 2, 1, 1);

-- --------------------------------------------------------

--
-- Estrutura para tabela `periodo`
--

DROP TABLE IF EXISTS `periodo`;
CREATE TABLE IF NOT EXISTS `periodo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `curso_id` int(11) NOT NULL,
  `slots_id` int(11) NOT NULL,
  `n_periodo` tinyint(4) NOT NULL,
  `ano_letivo` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_curso_id` (`curso_id`),
  KEY `fk_slots_id` (`slots_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Tabela truncada antes do insert `periodo`
--

TRUNCATE TABLE `periodo`;
--
-- Despejando dados para a tabela `periodo`
--

INSERT INTO `periodo` (`id`, `curso_id`, `slots_id`, `n_periodo`, `ano_letivo`) VALUES
(1, 1, 1, 1, 20191),
(2, 1, 1, 2, 20191),
(3, 1, 1, 3, 20191),
(4, 1, 1, 4, 20191),
(5, 2, 2, 1, 20191);

-- --------------------------------------------------------

--
-- Estrutura para tabela `preferencia`
--

DROP TABLE IF EXISTS `preferencia`;
CREATE TABLE IF NOT EXISTS `preferencia` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `professor_id` int(11) NOT NULL,
  `disciplina_id` int(11) NOT NULL,
  `impotancia` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_professorid` (`professor_id`),
  KEY `fk_disciplinaid` (`disciplina_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tabela truncada antes do insert `preferencia`
--

TRUNCATE TABLE `preferencia`;
-- --------------------------------------------------------

--
-- Estrutura para tabela `prerequisito`
--

DROP TABLE IF EXISTS `prerequisito`;
CREATE TABLE IF NOT EXISTS `prerequisito` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `disciplina_id` int(11) NOT NULL,
  `disciplina_pre_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_disciplina_pre_id` (`disciplina_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Tabela truncada antes do insert `prerequisito`
--

TRUNCATE TABLE `prerequisito`;
--
-- Despejando dados para a tabela `prerequisito`
--

INSERT INTO `prerequisito` (`id`, `disciplina_id`, `disciplina_pre_id`) VALUES
(1, 8, 4),
(2, 9, 5),
(3, 15, 5),
(4, 15, 9);

-- --------------------------------------------------------

--
-- Estrutura para tabela `professor`
--

DROP TABLE IF EXISTS `professor`;
CREATE TABLE IF NOT EXISTS `professor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `CPF` varchar(20) NOT NULL,
  `Nome` varchar(40) NOT NULL,
  `login` varchar(30) NOT NULL,
  `senha` varchar(150) NOT NULL,
  `area` enum('ARC','ENSISO','FC','EXTERNA') DEFAULT 'ENSISO',
  `cargo` enum('PROFESSOR','COORDENADOR','SUPERVISOR') DEFAULT 'PROFESSOR',
  PRIMARY KEY (`id`),
  UNIQUE KEY `CPF` (`CPF`),
  UNIQUE KEY `Nome` (`Nome`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Tabela truncada antes do insert `professor`
--

TRUNCATE TABLE `professor`;
--
-- Despejando dados para a tabela `professor`
--

INSERT INTO `professor` (`id`, `CPF`, `Nome`, `login`, `senha`, `area`, `cargo`) VALUES
(1, '01', 'rodemarck', 'rodemarck', '$2a$10$aPNN1p/ia8FB0tmUeN3z7eRb.fbhoZup5j4mEN3WrNVff9knXSpFW', 'ARC', 'PROFESSOR'),
(2, '02', 'fernanda', 'fernanda', '$2a$10$h8xNQ7wG1dxqaX4Fge9hSezM4laWQ/t3Ru41GG1ekzezfuDo8WCYK', 'ENSISO', 'COORDENADOR');

-- --------------------------------------------------------

--
-- Estrutura para tabela `regras`
--

DROP TABLE IF EXISTS `regras`;
CREATE TABLE IF NOT EXISTS `regras` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `filtro` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `filtro` (`filtro`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Tabela truncada antes do insert `regras`
--

TRUNCATE TABLE `regras`;
--
-- Despejando dados para a tabela `regras`
--

INSERT INTO `regras` (`id`, `filtro`) VALUES
(2, 'id <> 0'),
(4, 'id <> 5'),
(3, 'id <> 7'),
(1, 'id MOD 3 <> 0');

-- --------------------------------------------------------

--
-- Estrutura para tabela `slots`
--

DROP TABLE IF EXISTS `slots`;
CREATE TABLE IF NOT EXISTS `slots` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `turno` tinyint(2) DEFAULT NULL,
  `nome` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nome` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Tabela truncada antes do insert `slots`
--

TRUNCATE TABLE `slots`;
--
-- Despejando dados para a tabela `slots`
--

INSERT INTO `slots` (`id`, `turno`, `nome`) VALUES
(1, 1, 'BCC-PADRAO-1'),
(2, 2, 'LC-PADRAO-1');

-- --------------------------------------------------------

--
-- Estrutura para tabela `sub_slot`
--

DROP TABLE IF EXISTS `sub_slot`;
CREATE TABLE IF NOT EXISTS `sub_slot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `slots_id` int(11) NOT NULL,
  `dia` tinyint(4) NOT NULL,
  `horario` tinyint(4) NOT NULL,
  `numero` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_slotsid` (`slots_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

--
-- Tabela truncada antes do insert `sub_slot`
--

TRUNCATE TABLE `sub_slot`;
--
-- Despejando dados para a tabela `sub_slot`
--

INSERT INTO `sub_slot` (`id`, `slots_id`, `dia`, `horario`, `numero`) VALUES
(1, 1, 0, 7, 1),
(2, 1, 0, 8, 1),
(3, 1, 0, 9, 2),
(4, 1, 0, 10, 2),
(5, 1, 1, 7, 3),
(6, 1, 1, 8, 3),
(7, 1, 1, 9, 4),
(8, 1, 1, 10, 4),
(9, 1, 2, 7, 2),
(10, 1, 2, 8, 2),
(11, 1, 2, 9, 5),
(12, 1, 2, 10, 5),
(13, 1, 3, 7, 4),
(14, 1, 3, 8, 4),
(15, 1, 3, 9, 1),
(16, 1, 3, 10, 1),
(17, 1, 4, 7, 5),
(18, 1, 4, 8, 5),
(19, 1, 4, 9, 3),
(20, 1, 4, 10, 3),
(21, 2, 0, 11, 1),
(22, 2, 0, 12, 1),
(23, 2, 0, 13, 2),
(24, 2, 0, 14, 2),
(25, 2, 1, 11, 3),
(26, 2, 1, 12, 3),
(27, 2, 1, 13, 4),
(28, 2, 1, 14, 4),
(29, 2, 2, 11, 2),
(30, 2, 2, 12, 2),
(31, 2, 2, 13, 5),
(32, 2, 2, 14, 5),
(33, 2, 3, 11, 4),
(34, 2, 3, 12, 4),
(35, 2, 3, 13, 1),
(36, 2, 3, 14, 1),
(37, 2, 4, 11, 5),
(38, 2, 4, 12, 5),
(39, 2, 4, 13, 3),
(40, 2, 4, 14, 3);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
