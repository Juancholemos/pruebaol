
CREATE OR REPLACE PACKAGE reto04 IS
  FUNCTION reporte_comerciantes RETURN SYS_REFCURSOR;
END reto04;
/

CREATE OR REPLACE PACKAGE BODY reto04 IS

	FUNCTION reporte_comerciantes RETURN SYS_REFCURSOR IS
		c_comerciantes SYS_REFCURSOR;
		
	BEGIN
		-- Cursor referenciado para obtener la informacion de los comerciantes activos
		OPEN c_comerciantes FOR
		
			SELECT
				c.RAZON_SOCIAL,
				c.MUNICIPIO,
				c.TELEFONO,
				c.EMAIL,
				c.FECHA_REGISTRO,
				c.ESTADO,
				COUNT(e.ID_ESTAB)  AS CANTIDAD_ESTABLECIMIENTOS,
				NVL(SUM(e.INGRESOS), 0)      AS TOTAL_INGRESOS,
				NVL(SUM(e.NUM_EMPLEADOS), 0) AS CANTIDAD_EMPLEADOS
		    FROM COMERCIANTE c
			LEFT JOIN ESTABLECIMIENTO e ON c.ID_COMERCIANTE = e.ID_COMERCIANTE
		    WHERE c.ESTADO = 'Activo'
		    GROUP BY c.RAZON_SOCIAL, c.MUNICIPIO, c.TELEFONO, c.EMAIL, c.FECHA_REGISTRO, c.ESTADO
		    ORDER BY COUNT(e.ID_ESTAB) DESC;
			
		RETURN c_comerciantes;
			
	END;
END reto04;
/

--Ejecución del paquete
SET SERVEROUTPUT ON
DECLARE
  v_cursor     SYS_REFCURSOR;
  v_razon      COMERCIANTE.RAZON_SOCIAL%TYPE;
  v_municipio  COMERCIANTE.MUNICIPIO%TYPE;
  v_telefono   COMERCIANTE.TELEFONO%TYPE;
  v_email      COMERCIANTE.EMAIL%TYPE;
  v_fecha      COMERCIANTE.FECHA_REGISTRO%TYPE;
  v_estado     COMERCIANTE.ESTADO%TYPE;
  v_cant_estab NUMBER;
  v_total_ing  NUMBER;
  v_num_emp    NUMBER;
BEGIN
  v_cursor := reto04.reporte_comerciantes;

  LOOP
    FETCH v_cursor INTO v_razon, v_municipio, v_telefono, v_email, v_fecha, v_estado,
                       v_cant_estab, v_total_ing, v_num_emp;
    EXIT WHEN v_cursor%NOTFOUND;

    DBMS_OUTPUT.PUT_LINE('---');
    DBMS_OUTPUT.PUT_LINE('Razón Social: ' || v_razon);
    DBMS_OUTPUT.PUT_LINE('Municipio: ' || v_municipio);
    DBMS_OUTPUT.PUT_LINE('Teléfono: ' || v_telefono);
    DBMS_OUTPUT.PUT_LINE('Email: ' || v_email);
    DBMS_OUTPUT.PUT_LINE('Fecha Registro: ' || TO_CHAR(v_fecha, 'YYYY-MM-DD'));
    DBMS_OUTPUT.PUT_LINE('Estado: ' || v_estado);
    DBMS_OUTPUT.PUT_LINE('Cantidad Establecimientos: ' || v_cant_estab);
    DBMS_OUTPUT.PUT_LINE('Total Ingresos: ' || v_total_ing);
    DBMS_OUTPUT.PUT_LINE('Número de Empleados: ' || v_num_emp);
  END LOOP;

  CLOSE v_cursor;
END;
/
