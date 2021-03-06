package insertarDatos.controlador.procesador;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import insertarDatos.controlador.ControladorAniadir;
import insertarDatos.modelo.ConeccionBD;
import insertarDatos.vista.IngresarProcesador;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author sastian
 */
public class ControladorVentanainsertarProcesador extends WindowAdapter {

    private final IngresarProcesador ventana;
    private final ControladorAniadir controlador;
    private ConeccionBD bd;
    private static final String CONSULTA_ID = "select MAX(ID_PROCESADOR) from PROCESADOR;";

    public ControladorVentanainsertarProcesador(IngresarProcesador vent, ControladorAniadir ctr) {
        ventana = vent;
        controlador = ctr;
    }

    @Override
    public void windowOpened(WindowEvent e) {

        ResultSet resultado;
        try {
            bd = new ConeccionBD();
            //realizamos una cosulta a la base de datos para obtener el ultimo ID usado
            resultado = bd.realizarConsulta(CONSULTA_ID);
            while (resultado.next()) {
                //ponemos el id ya configurado en el cuadro de texto ID
                ventana.setTxtId(resultado.getInt(1) + 1);
            }
            resultado.close();

        } catch (SQLException ex) {
            //En caso de no poder realizar la consulta saltara un mensaje informandonos de ello
            JOptionPane.showMessageDialog(null, "No se pudo realizar la consulta correctamente", "Error Al realizar consulta", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
        try {
            controlador.iniciarProcesadores(bd);
        } catch (SQLException ex) {
            //Logger.getLogger(ControladorVentanainsertarProcesador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
