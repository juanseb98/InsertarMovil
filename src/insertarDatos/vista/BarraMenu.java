/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insertarDatos.vista;

import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author sastian
 */
public class BarraMenu extends JMenuBar {

    private JMenu mArchivo, mAyuda;
    private JMenuItem iSalir, iInsertar;

    public BarraMenu() {

        initVariables();
        add(mArchivo);
        iSalir.setMnemonic('S');
        mArchivo.add(iSalir);
        iInsertar.setMnemonic('I');
        mArchivo.add(iInsertar);

        add(mAyuda);

    }

    public void controlador(ActionListener crt) {
        iSalir.addActionListener(crt);
        iSalir.setActionCommand("cancelar");
        iInsertar.addActionListener(crt);
        iInsertar.setActionCommand("insertar");

    }

    private void initVariables() {
        mArchivo = new JMenu("Archivo");
        mAyuda = new JMenu("Ayuda");
        iInsertar = new JMenuItem("Insertar");
        iSalir = new JMenuItem("Salir");
    }

}
