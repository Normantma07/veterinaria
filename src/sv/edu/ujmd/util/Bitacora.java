/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.ujmd.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import vista.Login;
import sv.edu.ujmd.util.Log;

/**
 *
 * @author Myke
 */
public class Bitacora {
    
    private Calendar calendario;
    private Calendar c;
    private String dia, mes, anio, archivo, contenido, directorio, fecha;
    private int hora = 0, minutos = 0, segundos = 0;
    private Log lo;

    public void crearBitacora(usuarios u, Log l) { // cambie a usuarios u porque ahi tenemos el metodo getUsuario
        
        //se toman los datos del calendario y horario para meterlos en variables

        this.calendario = Calendar.getInstance();
        this.c = new GregorianCalendar();
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND);
        dia = Integer.toString(c.get(Calendar.DATE));
        mes = Integer.toString(c.get(Calendar.MONTH) + 1);
        anio = Integer.toString(c.get(Calendar.YEAR));

        //System.out.println(hora + ":" + minutos + ":" + segundos);
        //System.out.println(dia + "_" + mes +  "_" + anio); 
        archivo = "bita_" + dia + "-" + mes + "-" + anio + ".txt";
        contenido = "entra " + u.getUsuario() + " " + hora + ":" + minutos + ":" + segundos + " " //metodo getUsuario
                + dia + "-" + mes + "-" + anio;
        directorio = "bitacora" + dia + "-" + mes + "-" + anio;
        fecha = dia + "-" + mes + "-" + anio;
        try {
            //se crea el directorio y se verifica si existe o no
            File dir = new File(directorio);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
            l.setContenido(e.getMessage());
            l.escribirLog(u.getUsuario()); // getUsuario 

        }

        //se crea el archivo txt dentro del directorio y se valida si existe
        try {
            File file = new File(directorio + "/" + archivo);
            if (!file.exists()) {
                file.createNewFile();
            }
            //se creal el archivo y se escribe dentro de el lo que contiene 
            //variable contenido
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(contenido);
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
            l.setContenido(e.getMessage());
            l.escribirLog(u.getUsuario());
        }
    }

    public void cerrarSesion(usuarios u, Log l) {
        this.calendario = Calendar.getInstance();
        this.c = new GregorianCalendar();
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND);
        dia = Integer.toString(c.get(Calendar.DATE));
        mes = Integer.toString(c.get(Calendar.MONTH) + 1);
        anio = Integer.toString(c.get(Calendar.YEAR));

        //System.out.println(hora + ":" + minutos + ":" + segundos);
        //System.out.println(dia + "_" + mes +  "_" + anio); 
        archivo = "bita_" + dia + "-" + mes + "-" + anio + ".txt";
        contenido = "sale " + u.getUsuario() + " " + hora + ":" + minutos + ":" + segundos + " "
                + dia + "-" + mes + "-" + anio;
        directorio = "bitacora" + dia + "-" + mes + "-" + anio;

        try {
            File file = new File(directorio + "/" + archivo);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(contenido);
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
            l.setContenido(e.getMessage());
            l.escribirLog(u.getUsuario());
        }
    }
    
    public void Comprimir(usuarios u, Log l) {
        //direccion de lo que va comprimir
        String comprimir = directorio + "/";
        File zip = new File(comprimir);
        if (zip.exists()) {
            File[] ficheros = zip.listFiles();
            // ciclo para recorrer todos los archivos a comprimir
            for (int i = 0; i < ficheros.length; i++) {
                //System.out.println("Nombre del fichero: " + ficheros[i].getName());
                String extension = "";
                for (int j = 0; j < ficheros[i].getName().length(); j++) {
                    //obtiene la extensión del archivo
                    if (ficheros[i].getName().charAt(j) == '.') {
                        extension = ficheros[i].getName().substring(j, (int) ficheros[i].getName().length());
                        //System.out.println(extension);
                    }
                }
                try {
                    // crea un buffer temporal para ir poniendo los archivos a comprimir
                    ZipOutputStream zous = new ZipOutputStream(new FileOutputStream("hist/" + archivo.replaceAll(".txt", ".zip")));

                    //nombre con el que se va guardar el archivo dentro del zip
                    ZipEntry entrada = new ZipEntry(ficheros[i].getName());
                    zous.putNextEntry(entrada);

                    //System.out.println("Nombre del Archivo: " + entrada.getName());
                    //obtiene el archivo para irlo comprimiendo
                    FileInputStream fis = new FileInputStream(comprimir + entrada.getName());
                    int leer;
                    byte[] buffer = new byte[1024];
                    while (0 < (leer = fis.read(buffer))) {
                        zous.write(buffer, 0, leer);
                    }
                    fis.close();
                    zous.closeEntry();
                    zous.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    e.getCause();
                    l.setContenido(e.getMessage());
                    l.escribirLog(lo.getContenido());
                } catch (IOException e) {
                    e.printStackTrace();
                    e.getCause();
                    l.setContenido(e.getMessage());
                    l.escribirLog(lo.getContenido());
                }
            }
        }
    }
    
}    
