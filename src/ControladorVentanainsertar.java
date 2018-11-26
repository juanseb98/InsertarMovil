/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author sastian
 */
public class ControladorVentanainsertar extends WindowAdapter {

    private final IngresarDatos ventana;
    private static final String CONSULTA_MARCAS = "select NOMBRE from MARCA;";
    private static final String CONSULTA_PRO = "select MODELO from PROCESADOR;";

    public ControladorVentanainsertar(IngresarDatos vent) {
        ventana = vent;
    }

    @Override
    public void windowOpened(WindowEvent e) {

        ResultSet resultado;
        try {
            ConeccionBD bd = new ConeccionBD();
            //realizamos la consulta a la base de datos y guardamos los datos
            resultado = bd.realizarConsulta(CONSULTA_MARCAS);

            //Limpiamos los combobox de las marcas
            ventana.limpiarItems(ventana.getCbMarca());

            //aniadimos un item de identificativo
            while (resultado.next()) {
                //aniadimos el resultado de la consulta a los combobox de modelo
                ventana.addMarca(resultado.getString("NOMBRE"));
            }

            resultado = bd.realizarConsulta(CONSULTA_PRO);
            ventana.limpiarItems(ventana.getCbProcesador());
            while (resultado.next()) {
                //aniadimos el resultado de la consulta a los combobox de modelo
                ventana.addProcesador(resultado.getString("MODELO"));
            }

            resultado = bd.realizarConsulta("select MAX(ID) from MOVILES;");
            while (resultado.next()) {
                ventana.setTxtId(resultado.getInt(1) + 1);
            }
        } catch (SQLException ex) {
            //En caso de no poder realizar la consulta saltara un mensaje informandonos de ello
            JOptionPane.showMessageDialog(null, "No se pudo realizar la consulta correctamente", "Error Al realizar consulta", JOptionPane.ERROR_MESSAGE);
        }
    }
}
