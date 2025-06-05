package com.aplicacion.pruebaol.repository;

import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class ComercianteReporteRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Object[]> obtenerReporteComerciantes() {
        List<SqlParameter> declaredParameters = new ArrayList<>();
        declaredParameters.add(new SqlOutParameter(
                "p_cursor",
                OracleTypes.CURSOR,
                new RowMapper<Object[]>() {
                    @Override
                    public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new Object[]{
                                rs.getString("RAZON_SOCIAL"),
                                rs.getString("MUNICIPIO"),
                                rs.getString("TELEFONO"),
                                rs.getString("EMAIL"),
                                rs.getDate("FECHA_REGISTRO"),
                                rs.getString("ESTADO"),
                                rs.getInt("CANTIDAD_ESTABLECIMIENTOS"),
                                rs.getBigDecimal("TOTAL_INGRESOS"),
                                rs.getInt("CANTIDAD_EMPLEADOS")
                        };
                    }
                }
        ));

        Map<String, Object> results = jdbcTemplate.call(
                new CallableStatementCreator() {
                    @Override
                    public CallableStatement createCallableStatement(Connection con) throws SQLException {
                        String sql = "{ ? = call reto04.reporte_comerciantes() }";
                        CallableStatement cs = con.prepareCall(sql);
                        cs.registerOutParameter(1, OracleTypes.CURSOR);
                        return cs;
                    }
                },
                declaredParameters
        );

        List<Object[]> reporte = (List<Object[]>) results.get("p_cursor");
        return reporte != null ? reporte : Collections.emptyList();
    }
}
