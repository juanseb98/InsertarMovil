package insertatDatos.controlador;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import insertatDatos.vista.IngresarDatos;
import insertatDatos.modelo.ConeccionBD;
import insertatDatos.vista.IngresarProcesador;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author sastian
 */
public class ControladorAniadir implements ActionListener {

    private ConeccionBD bd;
    private IngresarDatos ventana;
    private String NOMBRE, FOTO, TAMANNO, RESOLUCION;
    private int ID, MARCA, PESO, ALMACENAMIENTO, RAM, PROCESADOR, HUELLA, ACELEROMETRO, GIROSCOPIO, BATERIA;
    private double PULGADAS;

    private static final String CONSULTA_ID = "select MAX(ID) from MOVILES;";

    public ControladorAniadir(IngresarDatos ventana) {
        bd = new ConeccionBD();
        this.ventana = ventana;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String boton = e.getActionCommand();

        switch (boton) {
            case "insertar":
                if (obtenerDatos()) {
                    insertar();
                    actualizarID();
                }

//                insertarIntert();
                break;
            case "procesador":
                JDialog ventanaProcesador = new JDialog(ventana, "Añadir Procesador");
                IngresarProcesador panel = new IngresarProcesador();
                ventanaProcesador.addWindowListener(new ControladorVentanainsertarProcesador(panel));
                ControladorAniadirProcesador ctr = new ControladorAniadirProcesador(panel);
                panel.controlador(ctr);
                ventanaProcesador.add(panel);

                ventanaProcesador.pack();
                ventanaProcesador.setModal(true);
                ventanaProcesador.setVisible(true);
                break;
        }

    }

    private void insertarIntert() throws HeadlessException {
        String insert = "INSERT INTO MOVILES(ID,MARCA,NOMBRE,FOTO,TAMANNO,PESO,PULGADAS,RESOLUCION,ALMACENAMIENTO,RAM,PROCESADOR,HUELLA,ACELEROMETRO,GIROSCOPIO,BATERIA) "
                + "VALUES (" + ventana.getTxtId() + "," + obtenerIdMarca(ventana.getMarca()) + ",'" + ventana.getTxtNombre() + "','" + ventana.getTxtFoto() + "','"
                + ventana.getTxtTamanio() + "'," + ventana.getTxtPeso() + "," + ventana.getTxtPulgadas() + ",'" + ventana.getTxtResolucion() + "',"
                + ventana.getTxtAlmacenamiento() + "," + ventana.getTxtRam() + "," + obtenerIdProcesador(ventana.getcbProcesador()) + "," + ventana.getCbHuella() + ","
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
            //Logger.getLogger(ControladorAniadir.class.getName()).log(Level.SEVERE, null, ex);
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
            //Logger.getLogger(ControladorAniadir.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    private void insertar() {
        try {
            String prepareInst = "INSERT INTO MOVILES(ID,MARCA,NOMBRE,FOTO,TAMANNO,PESO,PULGADAS,RESOLUCION,ALMACENAMIENTO,"
                    + "RAM,PROCESADOR,HUELLA,ACELEROMETRO,GIROSCOPIO,BATERIA) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement prepareStament = bd.getPrepareStament(prepareInst);

            prepareStament.setInt(1, ID);
            prepareStament.setInt(2, MARCA);
            prepareStament.setString(3, NOMBRE);
            prepareStament.setString(4, FOTO);
            prepareStament.setString(5, TAMANNO);
            prepareStament.setInt(6, PESO);
            prepareStament.setDouble(7, PULGADAS);
            prepareStament.setString(8, RESOLUCION);
            prepareStament.setInt(9, ALMACENAMIENTO);
            prepareStament.setInt(10, RAM);
            prepareStament.setInt(11, PROCESADOR);
            prepareStament.setInt(12, HUELLA);
            prepareStament.setInt(13, ACELEROMETRO);
            prepareStament.setInt(14, GIROSCOPIO);
            prepareStament.setInt(15, BATERIA);

            prepareStament.executeUpdate();
            prepareStament.close();
            JOptionPane.showMessageDialog(ventana, "Se ha añadido movil correctamente", "Añadido", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(ventana, "No se ha podido insertar movil", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    private boolean obtenerDatos() {
        boolean datosCorreecto = false;
        try {

            ID = Integer.parseInt(ventana.getTxtId());
            MARCA = obtenerIdMarca(ventana.getMarca());
            NOMBRE = ventana.getTxtNombre();
            FOTO = ventana.getTxtFoto();
            TAMANNO = ventana.getTxtTamanio();
            PESO = Integer.parseInt(ventana.getTxtPeso());
            PULGADAS = Double.parseDouble(ventana.getTxtPulgadas());
            RESOLUCION = ventana.getTxtResolucion();
            ALMACENAMIENTO = Integer.parseInt(ventana.getTxtAlmacenamiento());
            RAM = Integer.parseInt(ventana.getTxtRam());
            PROCESADOR = obtenerIdProcesador(ventana.getcbProcesador());
            HUELLA = ventana.getCbHuella();
            ACELEROMETRO = ventana.getCbAcelerometro();
            GIROSCOPIO = ventana.getCbGiroscopio();
            BATERIA = Integer.parseInt(ventana.getTxtBateria());
            datosCorreecto = true;

        } catch (NullPointerException | NumberFormatException e) {
            JOptionPane.showMessageDialog(ventana, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return datosCorreecto;
    }

    private void actualizarID() {
        try {
            ResultSet resultado = bd.realizarConsulta(CONSULTA_ID);
            if (resultado.next()) {
                ventana.setTxtId(resultado.getInt(1) + 1);
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ControladorAniadir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
