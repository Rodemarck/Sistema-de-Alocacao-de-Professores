label:BEGIN
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
        LEAVE label;
    END IF;
    SET @id_n1 = (SELECT id FROM espaco WHERE periodo_id=idPeriodo AND numero=n1);
    SET @id_n2 = (SELECT id FROM espaco WHERE periodo_id=idPeriodo AND numero=n2);

    IF((@id_n1 IS NOT NULL) AND (@id_n2 IS NOT NULL))THEN
        UPDATE espaco SET numero=n1 WHERE id=@id_n2;
        UPDATE espaco SET numero=n2 WHERE id=@id_n1;
        SELECT * FROM espaco;
        COMMIT;
    ELSE
        SET err = (SELECT INSERT(INSERT("horarios de numeros[$] ou [$] não encontrado",28,1,n2),21,1,n1));
        SIGNAL SQLSTATE '45000';
    END IF;
END


