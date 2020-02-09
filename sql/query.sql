use sap;
CREATE table slot(
    id INT PRIMARY KEY AUTO_INCREMENT,
    tipo TINYINT NOT NULL UNIQUE,
    nome VARCHAR(40) NOT NULL UNIQUE
);
CREATE TABLE sub_slot(
    id INT PRIMARY KEY AUTO_INCREMENT,
    slot_id INT NOT NULL,
    dia TINYINT NOT NULL,
    hora TINYINT NOT NULL,
    numero TINYINT NOT NULL,
    CONSTRAINT fk_slotid FOREIGN KEY (slot_id) REFERENCES slot(id)
);
CREATE TABLE espaco(
    id INT PRIMARY KEY AUTO_INCREMENT,
    slot_id INT NOT NULL,
    numero TINYINT NOT NULL,
    disciplina_id INT NOT NULL,
    CONSTRAINT fk_disciplinafk FOREIGN KEY (disciplina_id) REFERENCES disciplina(id),
    CONSTRAINT fk_slotid FOREIGN KEY (slot_id) REFERENCES slot(id)

);

DELIMITER $$
CREATE PROCEDURE troca_disciplinas(idPeriodo int, n1 int, n2 int)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
            ROLLBACK;
    END;
    START TRANSACTION;
    	SET @id_n1 = (SELECT id FROM espaco WHERE periodo_id=idPeriodo AND numero=n1);
    	SET @id_n2 = (SELECT id FROM espaco WHERE periodo_id=idPeriodo AND numero=n2);
    
    	UPDATE espaco SET numero=n1 WHERE id=@id_n2;
        UPDATE espaco SET numero=n2 WHERE id=@id_n1;
    	
    COMMIT;
END $$



SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Custom error';