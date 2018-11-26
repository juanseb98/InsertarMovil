/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author sastian
 */
public class ControladorAniadir implements ActionListener {

    private ConeccionBD bd;
    private IngresarDatos ventana;

    public ControladorAniadir(IngresarDatos ventana) {
        bd = new ConeccionBD();
        this.ventana = ventana;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String insert = "INSERT INTO MOVILES(ID,MARCA,NOMBRE,FOTO,TAMANNO,PESO,PULGADAS,RESOLUCION,ALMACENAMIENTO,RAM,PROCESADOR,HUELLA,ACELEROMETRO,GIROSCOPIO,BATERIA) "
                + "VALUES (" + ventana.getTxtId() + "," + obtenerIdMarca(ventana.getMarca()) + ",'" + ventana.getTxtNombre() + "','" + ventana.getTxtFoto() + "','"
                + ventana.getTxtTamanio() + "'," + ventana.getTxtPeso() + "," + ventana.getTxtPulgadas() + ",'" + ventana.getTxtREsolucion() + "',"
                + ventana.getTxtAlmacenamiento() + "," + ventana.getTxtRam() + "," + obtenerIdProcesador(ventana.getjComboBox2()) + "," + ventana.getCbHuella() + ","
                + ventana.getCbAcelerometro() + "," + ventana.getCbGiroscopio() + "," + ventana.getTxtBateria() + ");";
        try {
            bd.realizarInsert(insert);
            JOptionPane.showMessageDialog(ventana, "Se ha añadido movil correctamente", "Añadido", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(ventana, "No se ha podido insertar movil", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int obtenerIdMarca(String marca) {
        try {
            ResultSet resultado = bd.realizarConsulta("select ID_MARCA from MARCA where NOMBRE='" + marca + "';");
            return resultado.getInt("ID_MARCA");
        } catch (SQLException ex) {
            Logger.getLogger(ControladorAniadir.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    private int obtenerIdProcesador(String procesador) {
        try {
            int id = 0;
            ResultSet resultado = bd.realizarConsulta("select ID_PROCESADOR from PROCESADOR where MODELO = '" + procesador + "';");
            while (resultado.next()) {
                id = resultado.getInt("ID_PROCESADOR");
            }
            return id;
        } catch (SQLException ex) {
            Logger.getLogger(ControladorAniadir.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

}
