CREATE OR REPLACE PACKAGE reto03 IS
  PROCEDURE insertar_usuarios;
  PROCEDURE insertar_comerciantes;
  PROCEDURE insertar_establecimientos;
END reto03;
/

CREATE OR REPLACE PACKAGE BODY reto03 IS

	-- Insertar usuarios
	PROCEDURE insertar_usuarios IS
	BEGIN
		INSERT INTO USUARIO(NOMBRE, EMAIL, PASSWORD, ROL) 
		VALUES('Juan Perez', 'juan@email.com', '$2a$10$zbzACHvOnopl6Run1fwmxu.EzT6fuBPd7reNzk4.SmlaGgx1ujw8q', 'Administrador');--password = clave123
		
		INSERT INTO USUARIO(NOMBRE, EMAIL, PASSWORD, ROL) 
		VALUES('Jose Ospina','jose@email.com', '$2a$10$D5fX1bkJE6SPHMdzR5qPwO6qug3QCWXjnOtjh6X/edFPHXZzhdwPa', 'Auxiliar');--password = 4dm1ncl4v3
		
		COMMIT;
	END;
	
	
	-- Insertar comerciantes
    PROCEDURE insertar_comerciantes IS
		TYPE comerciante_rec IS RECORD (
			razon_social COMERCIANTE.RAZON_SOCIAL%TYPE,
			municipio    COMERCIANTE.MUNICIPIO%TYPE,
			telefono     COMERCIANTE.TELEFONO%TYPE,
			email        COMERCIANTE.EMAIL%TYPE    
		);
    
    TYPE comerciante_tab IS TABLE OF comerciante_rec;
		  
		comerciantes comerciante_tab := comerciante_tab(
			comerciante_rec('Roberto Klaus',   'Popayan',   '111222333', 'roberto_klaus@mail.com'),
			comerciante_rec('Andres Sosa',     'Cali',      '444555666', 'andres_sosa@mail.com'),
			comerciante_rec('Clara Vergara',   'Bogota',    '777888999', 'clara_vergara@mail.com'),
			comerciante_rec('Jorge Rodriguez', 'Medellin',  '159357456', 'jorge_rodriguez@mail.com'),
			comerciante_rec('Isabel Ramirez',  'Cartagena', '258789123', 'isabel_ramirez@mail.com')
		);
		
		v_id_usuario USUARIO.ID_USUARIO%TYPE;
		v_estado     COMERCIANTE.ESTADO%TYPE;
	BEGIN
		FOR i IN 1..5 LOOP
		
			-- Seleccionar aleatoriamente un usuario (o NULL)
			SELECT ID_USUARIO
			INTO v_id_usuario
			FROM (
			  SELECT ID_USUARIO
        FROM USUARIO
			  ORDER BY DBMS_RANDOM.VALUE
			)
			WHERE ROWNUM = 1;
			  
			-- Selecci√≥n aleatoria del estado 50% de probabilidad de usar 'Activo' o 'Inactivo'
			v_estado := CASE WHEN DBMS_RANDOM.VALUE < 0.5 THEN 'Activo'
							 ELSE 'Inactivo'
						END;

			INSERT INTO COMERCIANTE(ID_USUARIO, RAZON_SOCIAL, MUNICIPIO, TELEFONO, EMAIL, FECHA_REGISTRO, ESTADO)
			VALUES (v_id_usuario, comerciantes(i).razon_social, comerciantes(i).municipio, comerciantes(i).telefono, comerciantes(i).email, SYSDATE, v_estado);
		END LOOP;
		COMMIT;
	END;
	
	-- Insertar establecimientos
    PROCEDURE insertar_establecimientos IS
		nombres          SYS.ODCIVARCHAR2LIST := SYS.ODCIVARCHAR2LIST('Apple', 'IKEA', 'Louis Vuitton', 'Sephora', 'Adidas', 'Samsung', 'Toyota', 'Walmart', 'Starbucks', 'Zara');
		v_id_comerciante COMERCIANTE.ID_COMERCIANTE%TYPE;
		v_id_usuario     USUARIO.ID_USUARIO%TYPE;
	BEGIN
		FOR i IN 1..nombres.COUNT LOOP
		
			-- Seleccionar aleatoriamente un comerciante
			SELECT ID_COMERCIANTE
			INTO v_id_comerciante
			FROM (
			  SELECT ID_COMERCIANTE FROM COMERCIANTE
			  ORDER BY DBMS_RANDOM.VALUE
			)
			WHERE ROWNUM = 1;
			
			-- Seleccionar aleatoriamente un usuario o NULL
			SELECT ID_USUARIO
			INTO v_id_usuario
			FROM (
			  SELECT ID_USUARIO 
        FROM USUARIO
			  ORDER BY DBMS_RANDOM.VALUE
			)
			WHERE ROWNUM = 1;

			INSERT INTO ESTABLECIMIENTO(ID_COMERCIANTE, ID_USUARIO, NOMBRE_ESTAB, INGRESOS, NUM_EMPLEADOS)
			VALUES (v_id_comerciante, v_id_usuario, nombres(i), TRUNC(DBMS_RANDOM.VALUE(1000000, 50000000), 2), TRUNC(DBMS_RANDOM.VALUE(100, 1000)));
	    END LOOP;
	    COMMIT;
	END;

END reto03;
/

--EjecuciÛn del paquete
BEGIN
  reto03.insertar_usuarios;
  reto03.insertar_comerciantes;
  reto03.insertar_establecimientos;
END;
/
