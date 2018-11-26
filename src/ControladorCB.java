/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 *
 * @author sastian
 */
public class ControladorCB implements ItemListener {

    private IngresarDatos ventana;

    public ControladorCB(IngresarDatos aThis) {
        ventana = aThis;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            ventana.setMarca(e.getItem().toString());
        }
    }

}
