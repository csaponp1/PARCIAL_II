/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Modelo;
import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Sapón Pérez
 */
public class Estudiante extends Persona{
    Conexion1 cn;
    private String carnet;
    private int id;

    public Estudiante(){};
    public Estudiante(int id,String carnet, String nombres, String apellidos, String direccion, String telefono, String email, String fn, int genero) {
        super(nombres, apellidos, direccion, telefono, email, fn, genero);
        this.carnet = carnet;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }
    
    @Override
    public void agregar(){
        try{
            PreparedStatement parametro;
            cn = new Conexion1();
            cn.abrir_conexion();
            String query;
            query = "INSERT INTO estudiantes (carnet,nombres,apellidos,direccion,telefono,genero,email,fecha_nacimiento) VALUES(?,?,?,?,?,?,?,?);";
            parametro = (PreparedStatement)cn.conexionBD.prepareStatement(query);
            parametro.setString(1, getCarnet());
            parametro.setString(2, getNombres());
            parametro.setString(3, getApellidos());
            parametro.setString(4, getDireccion());
            parametro.setString(5, getTelefono());
            parametro.setInt(6, getGenero());
            parametro.setString(7, getEmail());
            parametro.setString(8, getFn());
            int ejecutar = parametro.executeUpdate();
            
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null,1+ "Registro Ingresado...!","Exito",JOptionPane.INFORMATION_MESSAGE);
            
        }
            
        catch(SQLException ex){System.out.println("xxxx Error xxxx"+ ex.getMessage());}
        
        cn.cerrar_conexion();
    }

    public DefaultTableModel leer(){
        DefaultTableModel tabla = new DefaultTableModel();
        try{
            cn = new Conexion1();
            cn.abrir_conexion();
            String query= "SELECT id_estudiante,carnet,nombres,apellidos,direccion,telefono,genero,email,fecha_nacimiento FROM estudiantes;";
            ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
            String encabezado[] = {"id","carnet","nombres","apellidos","direccion","telefono","genero","email","nacimiento"};
            tabla.setColumnIdentifiers(encabezado);
            String datos[] = new String[9];
            
            while(consulta.next()){
                datos[0]=consulta.getString("id_estudiante");
                datos[1]=consulta.getString("carnet");
                datos[2]=consulta.getString("nombres");
                datos[3]=consulta.getString("apellidos");
                datos[4]=consulta.getString("direccion");
                datos[5]=consulta.getString("telefono");
                datos[6]=consulta.getString("genero");
                datos[7]=consulta.getString("email");
                datos[8]=consulta.getString("fecha_nacimiento");
                tabla.addRow(datos);
            }
            cn.cerrar_conexion();

        }catch(SQLException ex){
            System.out.println("xxxx Error zxxxxx"+ ex.getMessage());
        }
        return tabla;
    }
    
    @Override
    public void actualizar(){
          try{
            
            PreparedStatement parametro;
            cn = new Conexion1();
            cn.abrir_conexion();
            String query;
            query = "update estudiantes set carnet=?,nombres=?,apellidos=?,direccion=?,telefono=?,genero=?,email=?,fecha_nacimiento=? where id_estudiante=?;";
            
            parametro = (PreparedStatement)cn.conexionBD.prepareStatement(query);
            parametro.setString(1, this.getCarnet());
            parametro.setString(2, this.getNombres());
            parametro.setString(3, this.getApellidos());
            parametro.setString(4, this.getTelefono());
            parametro.setInt(5, this.getGenero());
            parametro.setString(6, this.getEmail());
            parametro.setString(7, this.getFn());
            parametro.setInt(8, this.getId());
            int ejecutar = parametro.executeUpdate();
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null, Integer.toString(ejecutar)+" REGISTRO(S) ACTUALIZADO(S)","mensaje",JOptionPane.INFORMATION_MESSAGE);
                
        }
        
        catch(HeadlessException | SQLException ex){
            System.out.println("error"+ex.getMessage());
        }
    }
    
}
