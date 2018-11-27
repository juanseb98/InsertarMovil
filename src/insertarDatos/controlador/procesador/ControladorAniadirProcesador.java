/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insertarDatos.controlador.procesador;

import insertarDatos.modelo.ConeccionBD;
import insertarDatos.vista.IngresarProcesador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author sastian
 */
public class ControladorAniadirProcesador implements ActionListener {

    private String MODELO, TIPO;
    private int ID;
    private double FRECUANCIA;

    private ConeccionBD bd;
    private IngresarProcesador panel;
    private static final String CONSULTA_ID = "select MAX(ID_PROCESADOR) from PROCESADOR;";

    public ControladorAniadirProcesador(IngresarProcesador panel) {
        bd = new ConeccionBD();
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            if (insertar()) {
                JOptionPane.showMessageDialog(panel, "Se ha añadido Procesador correctamente", "Añadido", JOptionPane.INFORMATION_MESSAGE);
                actualizarID();
                panel.limpiar();
                panel.cerrarVentana();
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(panel, "No se ha podido insertar Procesador", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void actualizarID() throws SQLException {

        ResultSet resultado = bd.realizarConsulta(CONSULTA_ID);
        if (resultado.next()) {
            panel.setTxtId(resultado.getInt(1) + 1);
        }

    }

    private boolean insertar() throws SQLException {
        boolean correcto = false;
        String prepareInst = "INSERT INTO PROCESADOR(ID_PROCESADOR,MODELO,TIPO,FRECUENCIA) VALUES( ?,  ?,  ?,  ?);";
        if (obtenerDatos()) {
            PreparedStatement prepareStament = bd.getPrepareStament(prepareInst);

            prepareStament.setInt(1, ID);
            prepareStament.setString(2, MODELO);
            prepareStament.setString(3, TIPO);
            prepareStament.setDouble(4, FRECUANCIA);

            prepareStament.executeUpdate();
            prepareStament.close();
            correcto = true;
        }
        return correcto;
    }

    private boolean obtenerDatos() {
        boolean datosCorrecto = false;
        try {
            ID = Integer.parseInt(panel.getTxtId());
            MODELO = panel.getTxtModelo();
            TIPO = panel.getTxtTipo();
            FRECUANCIA = Double.parseDouble(panel.getTxtFrecuencia());
            datosCorrecto = true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panel, "La frecuencia es un numero", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(panel, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return datosCorrecto;
    }

}
