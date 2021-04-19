/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.ujmd.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
/**
 *
 * @author Myke
 */
public class Log {
        private String contenido;

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido += contenido + "\n";
    }

    public Log() {
        this.contenido = "";
    }

    private String fecha, dia, mes, anio;
    private Calendar c;
    private int hora = 0, minutos = 0, segundos = 0;
    private Calendar calendario;
    private String dato;

    public void escribirLog(String user) {
        this.c = new GregorianCalendar();
        this.calendario = Calendar.getInstance();
        dia = Integer.toString(c.get(Calendar.DATE));
        mes = Integer.toString(c.get(Calendar.MONTH) + 1);
        anio = Integer.toString(c.get(Calendar.YEAR));
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND);
        fecha = dia + "-" + mes + "-" + anio;
        dato = user + "_" + fecha + "_" + hora + ":" + minutos + ":" + segundos + "_";
        try {
            File file = new File("log/LG" + "_" + fecha + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(dato + contenido);
            bw.newLine();
            bw.close();
            this.contenido = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
