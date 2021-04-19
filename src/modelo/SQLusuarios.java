/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import sv.edu.ujmd.util.usuarios;
import com.mysql.cj.xdevapi.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Norman
 */
public class SQLusuarios extends conexion {
    
    public boolean registrar(usuarios usr){
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "INSERT INTO usuarios (usuario, contraseña, nombre, correo,idRoles) VALUES(?,?,?,?,?)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usr.getUsuario());
            ps.setString(2, usr.getContraseña());
            ps.setString(3, usr.getNombre());
            ps.setString(4, usr.getCorreo());
            ps.setInt(5,usr.getIdRoles());
            ps.execute();
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(SQLusuarios.class.getName()).log(Level.SEVERE, null, ex);
            
            return false;
        }
    }
    
    public boolean login(usuarios usr){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        
        String sql = "SELECT idusuarios, usuario, contraseña, nombre, idRoles FROM usuarios WHERE usuario = ?";
        
        try {
            ps = con.prepareStatement(sql); //preparamos consulta
            ps.setString(1, usr.getUsuario());// enviamos datos
            rs = ps.executeQuery();//EJECUTAMOS CONSULTA
            
            if(rs.next()){
                //COMPARAMOS CONTRASEÑA DE BDA Y LA INGRESADA
                if(usr.getContraseña().equals(rs.getString(3))) // el 3 significa en que columna va a evaluar del query de arriba
                {
                    usr.setIdusuarios(rs.getInt(1));
                    usr.setNombre(rs.getString(4));
                    usr.setIdRoles(rs.getInt(5));
                    return true;
                }else{
                    return false;
                }
                
                
            }
            return false;
            
            
        } catch (SQLException ex) {
            Logger.getLogger(SQLusuarios.class.getName()).log(Level.SEVERE, null, ex);
            
            return false;
        }
    }
    
     public int existeUsuario(String usuarios){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        
        String sql = "SELECT count(idusuarios) FROM usuarios WHERE usuario = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usuarios);
            rs = ps.executeQuery();
            
            if(rs.next()){
                return rs.getInt(1);
            }
            return 1;
            
            
        } catch (SQLException ex) {
            Logger.getLogger(SQLusuarios.class.getName()).log(Level.SEVERE, null, ex);
            
            return 1;
        }
    }
}
