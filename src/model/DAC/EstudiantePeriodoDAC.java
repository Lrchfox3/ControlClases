/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAC;


import com.gmail.lrchfox3.basedatos.BD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.DTO.EstudiantePeriodo;

/**
 *
 * @author lchinchilla
 */
public class EstudiantePeriodoDAC extends BD {
     private EstudiantePeriodo dtoBase = new EstudiantePeriodo();

     
         public List<Integer> getEstudiantePeriodos(int _codigoEstudiante) throws SQLException, Exception {
        List<Integer> lst = new ArrayList();
        String sql = "SELECT " + dtoBase.getNombreCampos() + " FROM " + dtoBase.getTabla() + " WHERE " + dtoBase.getNombreCampo(1) + " = " + _codigoEstudiante;
        Statement st = crearSentencia();
        ResultSet rs = st.executeQuery(sql);
        //rs.first();
        while (rs.next()) {
            lst.add(rs.getInt(dtoBase.getNombreCampo(2)));
        }
        if (st != null) {
            st.close();
        }
        if (rs != null) {
            rs.close();
        }
        return lst;
    }
         
    public boolean editar(int accion, EstudiantePeriodo dto) throws SQLException, Exception {
        String sql = "";
        PreparedStatement pstr = null;
        if (accion == BD.ACCION_AGREGAR) {
            sql = "INSERT INTO " + dtoBase.getTabla() + "(" + dtoBase.getNombreCampos(new int[]{1, 2}) + ") VALUES(" + dtoBase.getSignosParametros() + ")";
            pstr = prepararSentencia(sql);
            pstr.setInt(1, dto.getCodigoEstudiante());
            pstr.setInt(2, dto.getCodigoPeriodo());
            pstr.executeUpdate();
            commit();
        } else if (accion == BD.ACCION_EDITAR) {
            sql = "DELETE FROM " + dtoBase.getTabla() + " WHERE " + dtoBase.getNombreCampo(1) + " = ?";
            pstr = prepararSentencia(sql);
            pstr.setInt(1, dto.getCodigoEstudiante());
            pstr.executeUpdate();
            commit();
        }

        if (pstr != null) {
            pstr.close();
        }
        return true;
    }

    public boolean editar(int accion, int codigoEstudiante) throws SQLException, Exception {
        EstudiantePeriodo dto = new EstudiantePeriodo();
        dto.setCodigoEstudiante(codigoEstudiante);
        return editar(accion, dto);
    }
}
