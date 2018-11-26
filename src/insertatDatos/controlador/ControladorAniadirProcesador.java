/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insertatDatos.controlador;

import insertatDatos.modelo.ConeccionBD;
import insertatDatos.vista.IngresarProcesador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author sastian
 */
public class ControladorAniadirProcesador implements ActionListener {

    private ConeccionBD bd;
    private IngresarProcesador ventana;

    public ControladorAniadirProcesador(IngresarProcesador ventana) {
        bd = new ConeccionBD();
        this.ventana = ventana;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String insert = "INSERT INTO PROCESADOR(ID_PROCESADOR,MODELO,TIPO,FRECUENCIA) "
                + "VALUES(" + ventana.getTxtId() + ",'" + ventana.getTxtModelo() + "','" + ventana.getTxtTipo() + "'," + Double.parseDouble(ventana.getTxtFrecuencia()) + ");";
        try {
            bd.realizarInsert(insert);
            JOptionPane.showMessageDialog(ventana, "Se ha añadido movil correctamente", "Añadido", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(ventana, "No se ha podido insertar movil", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
