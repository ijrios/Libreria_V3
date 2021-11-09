/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Clases.Cliente;
import Clases.Administrador;
import Interfaz.Inicio;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import static javax.script.ScriptEngine.FILENAME;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author jario
 */
public class GestionDatos {

    private static final String FILENAME = "data.txt";
    private static final String FILENAME2 = "administrador.txt";
    private static final String FILENAME3 = "cliente.txt";
    private static final String FILENAME4 = "reseñas.txt";
    private static final String FILENAME5 = "libro.txt";
    private static ArrayList<Libro> book = new ArrayList<Libro>();
    private static ArrayList<Cliente> cliente = new ArrayList<Cliente>();
    private static ArrayList<Administrador> admi = new ArrayList<Administrador>();
    private static ArrayList<Sesión> sesionlist = new ArrayList<Sesión>();
    private static ArrayList<Reseña> critica = new ArrayList<Reseña>();
    private static ArrayList<Usuario> users = new ArrayList<Usuario>();
    private static ArrayList<Libro> b = new ArrayList<Libro>();
    private static  Map <Long, Integer> map = new HashMap <Long, Integer>();
    private static int numLibros = 0;
    private static int usuario = 0;
    private static int numadmin = 0;
    private static int numcliente = 0;
    private static int numsesion = 0;

    private static long validaLong(String number) {
        float result = 0; //Valor default.
        try {
            if (number != null) {
                result = Long.parseLong(number);
            }
        } catch (NumberFormatException nfe) {
            //*No es numerico!
        }
        return (long) result;
    }

    // Metodo para evitar excepcion java-lang-numberformatexception 
    private static int ConvertIntoNumeric(String xVal) {
        try {
            return Integer.parseInt(xVal);
        } catch (Exception ex) {
            return 0;
        }
    }
    
    // CALCULAR EL TOP 10
    public static String[][] SumarReseña() {
        
        int sum = 0;
        int rating = 0;
        long idbook = 0;
        String[][] reseña = new String[book.size()][3];
        String[] reseñas = null;
        
        for (int i = 0; i < book.size(); i++) {
            boolean ratingFound = false;
            sum = 0;
            for (int j = 0; j < critica.size(); j++) {
                if (book.get(i).getIdlibro() == critica.get(j).getIdlibro()) {
               
                    idbook = book.get(i).getIdlibro();
                    rating = critica.get(j).getCalificación();
                    sum += rating;
                    ratingFound = true;
                }
            }
            if (ratingFound) { // Or:  if (sum>0) 
                 
                 map.put(idbook, sum);
                 Object[] a = map.entrySet().toArray();
                Arrays.sort(a, new Comparator() {
                    public int compare(Object o1, Object o2) {
                        return ((Map.Entry<Long, Integer>) o2).getValue()
                                .compareTo(((Map.Entry<Long, Integer>) o1).getValue());
                    }
                });
                for (Object e : a) {
                   
                    reseña[i][0] = Long.toString(((Map.Entry<Long, Integer>) e).getKey());
                    reseña[i][1] = String.valueOf(book.get(i).getTitulo());
                    reseña[i][2] = Long.toString(((Map.Entry<Long, Integer>) e).getValue());
                    
                    System.out.println(((Map.Entry<Long, Integer>) e).getKey() + " : "
                            + ((Map.Entry<Long, Integer>) e).getValue());
                    
                } 
            }
        }
      
        return reseña;
         
    }
    
 
    public static boolean cargarlibros() {
        File f = new File(FILENAME);
        FileReader reader;
        try {
            reader = new FileReader(f);
            BufferedReader buffr = new BufferedReader(reader);
            String registro;
            while ((registro = buffr.readLine()) != null) {
                String[] datos = registro.replaceFirst("^\\{", "").split("}\\{|}$");
                //recontruir el objeto
                for (String dato : datos) {

                    String[] elems = dato.replaceFirst("^'", "").split("'?[:,] '|'$");

                    String titulo;
                    String descripcion;
                    ImageIcon imagen;
                    String formato;
                    String autor;
                    String editorial;
                    String isbn;
                    long id;
                    String categoria;
                    long precio;
                    String idioma;
                    int paginas;
                    String fecha;
                    String edicion;
                    String encuadernacion;

                    int longitud = elems.length;
//                    System.out.println(dato);
//                    System.out.println(longitud);

                    switch (longitud) {

                        case 14:
////                            System.out.println(elems[8]);
                            titulo = elems[1];
                            descripcion = elems[3];
                            imagen = new ImageIcon(elems[5]);
                            formato = elems[7];
                            autor = elems[9];
                            editorial = elems[11];
                            isbn = elems[13];
                            id = Long.parseLong(elems[13]);
                            categoria = " ";
                            precio = (long) 39900;
                            idioma = " ";
                            paginas = 0;
                            fecha = " ";
                            edicion = " ";
                            encuadernacion = " ";
                            GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);

                            break;

                        case 16:
////                            System.out.println(elems[8]);
                            titulo = elems[1];
                            descripcion = " ";
                            imagen = new ImageIcon(elems[3]);
                            formato = elems[5];
                            autor = elems[7];
                            editorial = elems[9];
                            isbn = elems[13];
                            id = Long.parseLong(elems[15]);
                            categoria = " ";
                            precio = (long) 39900;
                            idioma = " ";
                            paginas = 0;
                            fecha = " ";
                            edicion = " ";
                            encuadernacion = " ";
                            GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);

                            break;

                        case 18:
////                            System.out.println(elems[14]);
//                             
                            if (elems[10].contains("Editorial")) {
                                if (elems[14].contains("Isbn")) {
                                    titulo = elems[1];
                                    descripcion = elems[3];
                                    imagen = new ImageIcon(elems[5]);
                                    formato = elems[7];
                                    autor = elems[9];
                                    editorial = elems[11];
                                    isbn = elems[15];
                                    id = Long.parseLong(elems[17]);
                                    categoria = " ";
                                    precio = (long) 29900;
                                    idioma = elems[13];
                                    paginas = 0;
                                    fecha = " ";
                                    edicion = " ";
                                    encuadernacion = " ";
                                    GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);

                                }else{
                                    titulo = elems[1];
                                    descripcion = elems[3];
                                    imagen = new ImageIcon(elems[5]);
                                    formato = elems[7];
                                    autor = elems[9];
                                    editorial = elems[11];
                                    isbn = elems[17];
                                    id = Long.parseLong(elems[17]);
                                    categoria = " ";
                                    precio = (long) 29900;
                                    idioma = elems[13];
                                    paginas = 0;
                                    fecha = " ";
                                    edicion = " ";
                                    encuadernacion = elems[15];
                                    GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);

                                }
                                
                            }else{
                               
                                    titulo = elems[1];
                                    descripcion = elems[3];
                                    imagen = new ImageIcon(elems[5]);
                                    formato = elems[7];
                                    autor = elems[9];
                                    editorial = " ";
                                    isbn = elems[15] ;
                                    id = Long.parseLong(elems[17]);
                                    categoria = " ";
                                    precio = (long) 29900;
                                    idioma = elems[13];
                                    paginas = 0;
                                    fecha = elems[11];
                                    edicion = " ";
                                    encuadernacion = "";
                                    GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);

                                
                                
                            }
                            
                            break;

                        case 19:
//                            System.out.println(elems[17]);
//                            
                            titulo = elems[1];
                            descripcion = palabraEliminar(elems[2], "descripcion': \"'");
                            imagen = new ImageIcon(elems[4]);
                            formato = elems[6];
                            autor = elems[8];
                            editorial = elems[10];
                            isbn = elems[16];
                            id = Long.parseLong(elems[18]);
                            categoria = " ";
                            precio = (long) 29900;
                            idioma = elems[14];
                            paginas = 0;
                            fecha = elems[12];
                            edicion = " ";
                            encuadernacion = " ";
                            GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);

                            break;

                        case 20:
                            
                            if (elems[11].contains("Undefined")) {
//                                System.out.println(elems[18]);
                                titulo = elems[1];
                                descripcion = palabraEliminar(elems[2], "descripcion': \"");;
                                imagen = new ImageIcon(elems[4]);
                                formato = elems[6];
                                autor = palabraEliminar(elems[8], "Autor': ");
                                editorial = elems[9];
                                isbn = elems[17];
                                id = Long.parseLong(elems[19]);
                                categoria = elems[11];
                                precio = (long) 29900;
                                idioma = elems[15];
                                paginas = 0;
                                fecha = elems[13];
                                edicion = " ";
                                encuadernacion = " ";
                                GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);

                            }else{
                                titulo = elems[1];
                                descripcion = elems[3];
                                imagen = new ImageIcon(elems[5]);
                                formato = elems[7];
                                autor = elems[9];
                                editorial = elems[11];
                                isbn = elems[17];
                                id = Long.parseLong(elems[19]);
                                categoria = " ";
                                precio = (long) 29900;
                                idioma = elems[15];
                                paginas = 0;
                                fecha = elems[13];
                                edicion = " ";
                                encuadernacion = " ";
                                GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);

                            }
                            
                            break;

                        case 22:
//                            System.out.println(elems[12]);
                            if (elems[14].contains("Idioma")) {
                                if (elems[16].contains("Encuaderna")) {
                                    if (elems[12].contains("Cate")) {
                                        titulo = elems[1];
                                        descripcion = elems[3];
                                        imagen = new ImageIcon(elems[5]);
                                        formato = elems[7];
                                        autor = elems[9];
                                        editorial = elems[11];
                                        isbn = elems[19];
                                        id = Long.parseLong(elems[21]);
                                        categoria = elems[13];
                                        precio = (long) 39900;
                                        idioma = elems[15];
                                        paginas = 0;
                                        fecha = " ";
                                        edicion = " ";
                                        encuadernacion = elems[17];
                                        GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);

                                    }else{
                                        titulo = elems[1];
                                        descripcion = elems[3];
                                        imagen = new ImageIcon(elems[5]);
                                        formato = elems[7];
                                        autor = elems[9];
                                        editorial = elems[11];
                                        isbn = elems[19];
                                        id = Long.parseLong(elems[21]);
                                        categoria = "";
                                        precio = (long) 39900;
                                        idioma = elems[15];
                                        paginas = 0;
                                        fecha = elems[13];
                                        edicion = " ";
                                        encuadernacion = elems[17];
                                        GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);

                                    }
                                  
                                }else{
                                    if (!elems[12].contains("Cate")){
                                        titulo = elems[1];
                                        descripcion = elems[3];
                                        imagen = new ImageIcon(elems[5]);
                                        formato = elems[7];
                                        autor = elems[9];
                                        editorial = elems[11];
                                        isbn = elems[19];
                                        id = Long.parseLong(elems[21]);
                                        categoria = "" ;
                                        precio = (long) 39900;
                                        idioma = elems[15];
                                        paginas = ConvertIntoNumeric(elems[17]);
                                        fecha = elems[13];
                                        edicion = " ";
                                        encuadernacion = "";
                                        GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);

                                    }else{
                                        titulo = elems[1];
                                        descripcion = elems[3];
                                        imagen = new ImageIcon(elems[5]);
                                        formato = elems[7];
                                        autor = elems[9];
                                        editorial = elems[11];
                                        isbn = elems[19];
                                        id = Long.parseLong(elems[21]);
                                        categoria = elems[13];
                                        precio = (long) 39900;
                                        idioma = elems[15];
                                        paginas = ConvertIntoNumeric(elems[17]);
                                        fecha = "";
                                        edicion = " ";
                                        encuadernacion = "";
                                        GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);

                                    }
                                    
                                }
                            
                            } else{
                                        titulo = elems[1];
                                        descripcion = elems[3];
                                        imagen = new ImageIcon(elems[5]);
                                        formato = elems[7];
                                        autor = elems[9];
                                        editorial = elems[11];
                                        isbn = elems[19];
                                        id = Long.parseLong(elems[21]);
                                        categoria = elems[13];
                                        precio = (long) 39900;
                                        idioma = elems[17];
                                        paginas = 0;
                                        fecha = elems[15];
                                        edicion = "";
                                        encuadernacion = "";
                                        GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);

                            }
                            
                            break;

                        case 24:
//                            System.out.println(elems[22]);
                            if (elems[18].contains("Isbn")) {
                                if (elems[16].contains("Encua")) {
                                    titulo = elems[1];
                                    descripcion = elems[3];
                                    imagen = new ImageIcon(elems[5]);
                                    formato = elems[7];
                                    autor = elems[9];
                                    editorial = elems[11];
                                    isbn = elems[19];
                                    id = Long.parseLong(elems[21]);
                                    categoria = elems[13];
                                    precio = (long) 39900;
                                    idioma = elems[15];
                                    paginas = ConvertIntoNumeric(elems[17]);
                                    fecha = "";
                                    edicion = elems[23];
                                    encuadernacion = elems[17];
                                    GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);

                                }else{
                                    titulo = elems[1];
                                    descripcion = elems[3];
                                    imagen = new ImageIcon(elems[5]);
                                    formato = elems[7];
                                    autor = elems[9];
                                    editorial = elems[11];
                                    isbn = elems[19];
                                    id = Long.parseLong(elems[21]);
                                    categoria = elems[13];
                                    precio = (long) 39900;
                                    idioma = elems[17];
                                    paginas = 0;
                                    fecha = elems[15];
                                    edicion = elems[23];
                                    encuadernacion = elems[17];
                                    GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);

                                }
                                   
                            
                            }else if (elems[18].contains("Encua")){
                                 if (elems[20].contains("Isbn13")) {
                                     if (elems[12].contains("Catego")) {
                                     titulo = elems[1];
                                     descripcion = elems[3];
                                     imagen = new ImageIcon(elems[5]);
                                     formato = elems[7];
                                     autor = elems[9];
                                     editorial = elems[11];
                                     isbn = elems[21];
                                     id = Long.parseLong(elems[21]);
                                     categoria = elems[13];
                                     precio = (long) 39900;
                                     idioma = elems[15];
                                     paginas = ConvertIntoNumeric(elems[17]);
                                     fecha = "";
                                     edicion =elems[23];
                                     encuadernacion = elems[19];
                                     GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);

                                     }else{
                                     titulo = elems[1];
                                     descripcion = elems[3];
                                     imagen = new ImageIcon(elems[5]);
                                     formato = elems[7];
                                     autor = elems[9];
                                     editorial = elems[11];
                                     isbn = elems[21];
                                     id = Long.parseLong(elems[21]);
                                     categoria = " ";
                                     precio = (long) 39900;
                                     idioma = elems[15];
                                     paginas = ConvertIntoNumeric(elems[17]);
                                     fecha = elems[13];
                                     edicion =elems[23];
                                     encuadernacion = elems[19];
                                     GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);

                                     }
                                     
                                } else if (elems[22].contains("Isbn13")) {
                                     titulo = elems[1];
                                     descripcion = elems[3];
                                     imagen = new ImageIcon(elems[5]);
                                     formato = elems[7];
                                     autor = elems[9];
                                     editorial = elems[11];
                                     isbn = elems[21];
                                     id = Long.parseLong(elems[23]);
                                     categoria = " ";
                                     precio = (long) 39900;
                                     idioma = elems[15];
                                     paginas = ConvertIntoNumeric(elems[17]);
                                     fecha = elems[13];
                                     edicion = " ";
                                     encuadernacion = elems[19];
                                     GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);

                                 }
                           
                            
                            } else if (elems[16].contains("Idioma")){
                            titulo = elems[1];
                            descripcion = elems[3];
                            imagen = new ImageIcon(elems[5]);
                            formato = elems[7];
                            autor = elems[9];
                            editorial = elems[11];
                            isbn = elems[21];
                            id = Long.parseLong(elems[23]);
                            categoria = elems[12];
                            precio = (long) 39900;
                            idioma = elems[17];
                            paginas = ConvertIntoNumeric(elems[19]);
                            fecha = elems[15];
                            edicion = " ";
                            encuadernacion =elems[21];
                            GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);
 
                            }
                            
                            break;

                        case 26:
                            
//                            System.out.println(elems[18]);
//                            
                            if (elems[14].contains("Idioma")) {
                            titulo = elems[1];
                            descripcion = elems[3];
                            imagen = new ImageIcon(elems[5]);
                            formato = elems[7];
                            autor = elems[9];
                            editorial = elems[11];
                            isbn = elems[21];
                            id = Long.parseLong(elems[23]);
                            categoria = elems[13];
                            precio = (long) 39900;
                            idioma = elems[15];
                            paginas = ConvertIntoNumeric(elems[17]);
                            fecha = " ";
                            edicion = elems[25];
                            encuadernacion = elems[19];
                            GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);

                            } else if (elems[24].contains("Isbn")) {
                                 
                                    titulo = elems[1];
                                    descripcion = elems[3];
                                    imagen = new ImageIcon(elems[5]);
                                    formato = elems[7];
                                    autor = elems[9];
                                    editorial = elems[11];
                                    isbn = elems[23];
                                    id = Long.parseLong(elems[25]);
                                    categoria = elems[13];
                                    precio = (long) 39900;
                                    idioma = elems[17];
                                    paginas = ConvertIntoNumeric(elems[19]);
                                    fecha = elems[15];
                                    edicion = "";
                                    encuadernacion = elems[21];
                                    GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);

                            } else {
                                    if (elems[20].contains("Isbn")) {
                                        titulo = elems[1];
                                        descripcion = elems[3];
                                        imagen = new ImageIcon(elems[5]);
                                        formato = elems[7];
                                        autor = elems[9];
                                        editorial = elems[11];
                                        isbn = elems[21];
                                        id = Long.parseLong(elems[23]);
                                        categoria = elems[13];
                                        precio = (long) 39900;
                                        idioma = elems[17];
                                        paginas = ConvertIntoNumeric(elems[19]);
                                        fecha = elems[15];
                                        edicion = elems[25];
                                        encuadernacion = " ";
                                        GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);

                                }else{
                                        titulo = elems[1];
                                        descripcion = elems[3];
                                        imagen = new ImageIcon(elems[5]);
                                        formato = elems[7];
                                        autor = elems[9];
                                        editorial = elems[11];
                                        isbn = elems[23];
                                        id = Long.parseLong(elems[23]);
                                        categoria = elems[13];
                                        precio = (long) 39900;
                                        idioma = elems[17];
                                        paginas = ConvertIntoNumeric(elems[19]);
                                        fecha = elems[15];
                                        edicion = elems[25];
                                        encuadernacion = elems[21];
                                        GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);

                                    }
                                   
                            }
                           
                            break;

                        case 28:
//                            System.out.println(elems[26]);
                            if (elems[22].contains("Isbn")) {
                            titulo = elems[1];
                            descripcion = elems[3];
                            imagen = new ImageIcon(elems[5]);
                            formato = elems[7];
                            autor = elems[9];
                            editorial = elems[11];
                            isbn = elems[23];
                            id = Long.parseLong(elems[25]);
                            categoria = elems[13];
                            precio = (long) 49900;
                            idioma = elems[17];
                            paginas = ConvertIntoNumeric(elems[19]);
                            fecha = elems[15];
                            edicion = elems[27];
                            encuadernacion = elems[21];
                            GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);
                            
                            
                            }else{
                            titulo = elems[1];
                            descripcion = elems[3];
                            imagen = new ImageIcon(elems[5]);
                            formato = elems[7];
                            autor = elems[9];
                            editorial = elems[11];
                            isbn = elems[25];
                            id = Long.parseLong(elems[25]);
                            categoria = elems[13];
                            precio = (long) 49900;
                            idioma = elems[19];
                            paginas = ConvertIntoNumeric(elems[21]);
                            fecha = elems[17];
                            edicion = elems[27];
                            encuadernacion = elems[23];
                            GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);

                            }
                               
                            
                            break;

                        case 30:
//                            
//                            System.out.println(elems[14]);
                            if (elems[18].contains("Idioma")) {
                            titulo = elems[1];
                            descripcion = elems[3];
                            imagen = new ImageIcon(elems[5]);
                            formato = elems[7];
                            autor = elems[9];
                            editorial = elems[11];
                            isbn = elems[25];
                            id = Long.parseLong(elems[27]);
                            categoria = elems[13];
                            precio = (long) 49900;
                            idioma = elems[19];
                            paginas = ConvertIntoNumeric(elems[21]);
                            fecha = elems[17];
                            edicion = elems[29];
                            encuadernacion = elems[23];
                            GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);

                            }else{
                                
                            titulo = elems[1];
                            descripcion = elems[3];
                            imagen = new ImageIcon(elems[5]);
                            formato = elems[7];
                            autor = elems[9];
                            editorial = elems[11];
                            isbn = elems[25];
                            id = Long.parseLong(elems[27]);
                            categoria = elems[13];
                            precio = (long) 49900;
                            idioma = elems[17];
                            paginas = ConvertIntoNumeric(elems[19]);
                            fecha = elems[15];
                            edicion = elems[29];
                            encuadernacion = elems[21];
                            GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);

                            }
                            
                            break;

                        case 34:
//                            System.out.println(elems[28]);
                            
                            if (elems[18].contains("Idioma")) {
                            titulo = elems[1];
                            descripcion = elems[3];
                            imagen = new ImageIcon(elems[5]);
                            formato = elems[7];
                            autor = elems[9];
                            editorial = elems[11];
                            isbn = elems[29];
                            id = Long.parseLong(elems[31]);
                            categoria = elems[13];
                            precio = (long) 59900;
                            idioma = elems[19];
                            paginas = ConvertIntoNumeric(elems[21]);
                            fecha = elems[17];
                            edicion = elems[31];
                            encuadernacion = elems[23];
                            GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);

                            }else{
                                
                            titulo = elems[1];
                            descripcion = elems[3];
                            imagen = new ImageIcon(elems[5]);
                            formato = elems[7];
                            autor = elems[9];
                            editorial = elems[11];
                            isbn = elems[29];
                            id = Long.parseLong(elems[31]);
                            categoria = elems[13];
                            precio = (long) 59900;
                            idioma = elems[21];
                            paginas = ConvertIntoNumeric(elems[23]);
                            fecha = elems[19];
                            edicion = elems[31];
                            encuadernacion = elems[25];
                            GestionDatos.insertarLibro(id, titulo, editorial, formato, autor, categoria, precio, idioma, descripcion, paginas, fecha, edicion, isbn, encuadernacion, imagen);

                                
                            }
                            
                            break;

                    }
                }
            }
            buffr.close();
            reader.close();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
    
     public static Libro getlibro(int index){
        return book.get(index);
    }
    public static String palabraEliminar(String oracion, String palabra) {
        if (oracion.contains(palabra)) {
            return oracion.replaceAll(palabra, "");
        }
        return oracion;
    }
    
    public static boolean cargaradministrador() {
        File f = new File(FILENAME2);
        FileReader reader;
        try {
            reader = new FileReader(f);
            BufferedReader buffr = new BufferedReader(reader);
            String registro;
            while ((registro = buffr.readLine()) != null) {
                String[] datos = registro.split(";");
                //recontruir el objeto
                String nombre = datos[0];
                int id = ConvertIntoNumeric(datos[1]);
                String correo = datos[2];
                String pass = datos[3];
                GestionDatos.generico(nombre, id, correo, pass);
            }
            buffr.close();
            reader.close();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public static boolean cargarclientes() {
        File f = new File(FILENAME3);
        FileReader reader;
        try {
            reader = new FileReader(f);
            BufferedReader buffr = new BufferedReader(reader);
            String registro;
            while ((registro = buffr.readLine()) != null) {
                String[] datos = registro.split(";");
                //recontruir el objeto
                String nombre = datos[0];
                int id = ConvertIntoNumeric(datos[1]);
                String correo = datos[2];
                long celular = validaLong(datos[3]);
                String pass = datos[4];
                String dir = datos[5];
                GestionDatos.insertarCLiente(nombre, id, correo, celular, pass, dir);
            }
            buffr.close();
            reader.close();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public static boolean cargarreseñas() {
        File f = new File(FILENAME4);
        FileReader reader;
        try {
            reader = new FileReader(f);
            BufferedReader buffr = new BufferedReader(reader);
            String registro;
            while ((registro = buffr.readLine()) != null) {
                String[] datos = registro.split(";");
                //recontruir el objeto
                int id = ConvertIntoNumeric(datos[0]);
                int id2 = ConvertIntoNumeric(datos[1]);
                long id3 = Long.valueOf(datos[2]);
                String des = datos[3];
                int cali = ConvertIntoNumeric(datos[4]);
                GestionDatos.reseña(id, id2, id3, des, cali);
            }
            buffr.close();
            reader.close();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public static boolean guardar() {

        FileWriter writer = null;
        try {
            File f = new File(FILENAME2);
            writer = new FileWriter(f);
            BufferedWriter buffw = new BufferedWriter(writer);
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < admi.size(); i++) {
                Administrador admin = admi.get(i);
                if (admin != null) {
                    sb.append(admi.get(i).getNombre());
                    sb.append(";");
                    sb.append(admi.get(i).getId());
                    sb.append(";");
                    sb.append(admi.get(i).getCorreo());
                    sb.append(";");
                    sb.append(admi.get(i).getPass());
                    buffw.write(sb.toString());
                    buffw.newLine();
                    sb.setLength(0);
                }
            }
            buffw.close();
            writer.close();
        } catch (IOException ex) {
            return false;
        }
        return true;

    }

    public static boolean guardar2() {

        FileWriter writer = null;
        try {
            File f = new File(FILENAME3);
            writer = new FileWriter(f);
            BufferedWriter buffw = new BufferedWriter(writer);
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < cliente.size(); i++) {
                Cliente client = cliente.get(i);
                if (client != null) {
                    sb.append(cliente.get(i).getNombre());
                    sb.append(";");
                    sb.append(cliente.get(i).getId());
                    sb.append(";");
                    sb.append(cliente.get(i).getCorreo());
                    sb.append(";");
                    sb.append(cliente.get(i).getCelular());
                    sb.append(";");
                    sb.append(cliente.get(i).getPass());
                    sb.append(";");
                    sb.append(cliente.get(i).getDireccion());
                    buffw.write(sb.toString());
                    buffw.newLine();
                    sb.setLength(0);
                }
            }
            buffw.close();
            writer.close();
        } catch (IOException ex) {
            return false;
        }
        return true;

    }

    public static boolean guardar3() {

        FileWriter writer = null;
        try {
            File f = new File(FILENAME4);
            writer = new FileWriter(f);
            BufferedWriter buffw = new BufferedWriter(writer);
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < critica.size(); i++) {
                Reseña reseña = critica.get(i);
                if (reseña != null) {
                    sb.append(critica.get(i).getIdreseña());
                    sb.append(";");
                    sb.append(critica.get(i).getIdusuario());
                    sb.append(";");
                    sb.append(critica.get(i).getIdlibro());
                    sb.append(";");
                    sb.append(critica.get(i).getDescripcion());
                    sb.append(";");
                    sb.append(critica.get(i).getCalificación());
                    buffw.write(sb.toString());
                    buffw.newLine();
                    sb.setLength(0);
                }
            }
            buffw.close();
            writer.close();
        } catch (IOException ex) {
            return false;
        }
        return true;

    }
    public static boolean guardar4() {

        FileWriter writer = null;
        try {
            File f = new File(FILENAME5);
            writer = new FileWriter(f);
            BufferedWriter buffw = new BufferedWriter(writer);
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < book.size(); i++) {
                Libro lib = book.get(i);
                if (lib != null) {
                    sb.append("{");
                    sb.append("'titulo:'");
                    sb.append("'").append(book.get(i).getTitulo()).append("'");
                    sb.append(",");
                    sb.append("'descripcion:'");
                    sb.append("'").append(book.get(i).getSinapsis()).append("'");
                    sb.append(",");
                    sb.append("'img:'");
                    sb.append("'").append(book.get(i).getImagen()).append("'");
                    sb.append(",");
                    sb.append("'formato:'");
                    sb.append("'").append(book.get(i).getFormato()).append("'");
                    sb.append(",");
                    sb.append("'autor:'");
                    sb.append("'").append(book.get(i).getAutor()).append("'");
                    sb.append(",");
                    sb.append("'editorial:'");
                    sb.append("'").append(book.get(i).getEditorial()).append("'");
                    sb.append(",");
                    sb.append("'categoría:'");
                    sb.append("'").append(book.get(i).getCategoria()).append("'");
                    sb.append(",");
                    sb.append("'precio:'");
                    sb.append("'").append(book.get(i).getPrecio()).append("'");
                    sb.append(",");
                    sb.append("'año:'");
                    sb.append("'").append(book.get(i).getFecha()).append("'");
                    sb.append(",");
                    sb.append("'idioma:'");
                    sb.append("'").append(book.get(i).getIdioma()).append("'");
                    sb.append(",");
                    sb.append("'N° paginas:'");
                    sb.append("'").append(book.get(i).getPaginas()).append("'");
                    sb.append(",");
                    sb.append("'encuadernacion:'");
                    sb.append("'").append(book.get(i).getEncuadernacion()).append("'");
                    sb.append(",");
                    sb.append("'isbn:'");
                    sb.append("'").append(book.get(i).getIsbn()).append("'");
                    sb.append(",");
                    sb.append("'isbn13:'");
                    sb.append("'").append(book.get(i).getIdlibro()).append("'");
                    sb.append(",");
                    sb.append("'edicion:'");
                    sb.append("'").append(book.get(i).getEdicion()).append("'");
                    sb.append("}");
                    buffw.write(sb.toString());
                    sb.setLength(0);
                    
                }
            }
            buffw.close();
            writer.close();
        } catch (IOException ex) {
            return false;
        }
        return true;

    }
    
//                String[] datos = registro.replaceFirst("^\\{", "").split("}\\{|}$");
//                //recontruir el objeto
//                for (String dato : datos) {
//
//                    String[] elems = dato.replaceFirst("^'", "").split("'?[:,] '|'$");

    /*
    Crea una matriz a partir de los datos almacenados
     */
    public static String[][] libreria() {
        String[][] libro = new String[book.size()][15];
        for (int i = 0; i < book.size(); i++) {
            libro[i][0] = String.valueOf(book.get(i).getIdlibro());
            libro[i][1] = book.get(i).getTitulo();
            libro[i][2] = book.get(i).getEditorial();
            libro[i][3] = book.get(i).getFormato();
            libro[i][4] = book.get(i).getAutor();
            libro[i][5] = book.get(i).getCategoria();
            libro[i][6] = Long.toString(book.get(i).getPrecio());
            libro[i][7] = book.get(i).getIdioma();
            libro[i][8] = book.get(i).getSinapsis();
            libro[i][9] = String.valueOf(book.get(i).getPaginas());
            libro[i][10] = book.get(i).getFecha();
            libro[i][11] = book.get(i).getEdicion();
            libro[i][12] = book.get(i).getIsbn();
            libro[i][13] = book.get(i).getEncuadernacion();
            libro[i][14] = String.valueOf(book.get(i).getImagen());
        }
        return libro;
    }
    
 

    public static void modificarLibro(int index, int id, String titulo, String editorial, String formato, String autor, String categoria, long precio, String idioma, String reseña, int paginas, String fecha, String edicion, String isbn, String encuadernacion) {

        book.get(index).setIdlibro(id);
        book.get(index).setTitulo(titulo);
        book.get(index).setEditorial(editorial);
        book.get(index).setFormato(formato);
        book.get(index).setAutor(autor);
        book.get(index).setCategoria(categoria);
        book.get(index).setPrecio(precio);
        book.get(index).setIdioma(idioma);
        book.get(index).setSinapsis(reseña);
        book.get(index).setPaginas(paginas);
        book.get(index).setFecha(fecha);
        book.get(index).setEdicion(edicion);
        book.get(index).setIsbn(isbn);
        book.get(index).setEncuadernacion(encuadernacion);

//        
    }

    public static void modificarSesion(int index, String fechafin) {

        sesionlist.get(index).setFechafin(fechafin);
        
    }

    public static String[][] UsuarioaAdmin() {
        String[][] admin = new String[admi.size()][4];
        for (int i = 0; i < admi.size(); i++) {
            admin[i][0] = admi.get(i).getNombre();
            admin[i][1] = String.valueOf(admi.get(i).getId());
            admin[i][2] = admi.get(i).getCorreo();
            admin[i][3] = admi.get(i).getPass();
        }
        return admin;
    }

    public static String[][] general() {
        String[][] admin = new String[users.size()][4];
        for (int i = 0; i < users.size(); i++) {
            admin[i][0] = users.get(i).getNombre();
            admin[i][1] = String.valueOf(users.get(i).getId());
            admin[i][2] = users.get(i).getCorreo();
            admin[i][3] = users.get(i).getPass();
        }
        return admin;
    }

    public static String[][] reseñas() {
        String[][] reseña = new String[critica.size()][5];
        for (int i = 0; i < critica.size(); i++) {
            reseña[i][0] = String.valueOf(critica.get(i).getIdreseña());
            reseña[i][1] = String.valueOf(critica.get(i).getIdusuario());
            reseña[i][2] = String.valueOf(critica.get(i).getIdlibro());
            reseña[i][3] = critica.get(i).getDescripcion();
            reseña[i][4] = String.valueOf(critica.get(i).getCalificación());
        }
        return reseña;
    }

    public static ArrayList<Usuario> getList() {
        return users;
    }

    public static ArrayList<Libro> getListbook() {
        return book;
    }

    public static ArrayList<Reseña> getListreseña() {
        return critica;
    }
    public static String[][] UsuarioCliente() {
        String[][] client = new String[cliente.size()][6];
        for (int i = 0; i < cliente.size(); i++) {
            client[i][0] = cliente.get(i).getNombre();
            client[i][1] = String.valueOf(cliente.get(i).getId());
            client[i][2] = cliente.get(i).getCorreo();
            client[i][3] = String.valueOf(cliente.get(i).getCelular());
            client[i][4] = cliente.get(i).getPass();
            client[i][5] = cliente.get(i).getDireccion();
        }
        return client;
    }

    public static Administrador generico(String nombre, int id, String correo, String pass) {

        Administrador admin = new Administrador(nombre, id, correo, pass);
        users.add(admin);
        admi.add((Administrador) admin);
        numadmin++;

        return admin;
    }

    public static Cliente insertarCLiente(String nombre, int id, String correo, long celular, String pass, String dir) {

        Cliente clientx = new Cliente(nombre, id, correo, celular, pass, dir);
        users.add(clientx);
        cliente.add((Cliente) clientx);
        numcliente++;

        return clientx;
    }

    public static Libro insertarLibro(long id, String titulo, String editorial, String formato, String autor, String categoria, long precio, String idioma, String reseña, int paginas, String fecha, String edicion, String isbn, String encuadernacion, ImageIcon imagen) {

        Libro books = new Libro(id, titulo, editorial, formato, autor, categoria, precio, idioma, reseña, paginas, fecha, edicion, isbn, encuadernacion, imagen);
        book.add(books);
        numLibros++;

        return books;
    }

    public static Sesión inisiarSesion(int idsesion, int idusuario, String fechainicio, String fechafin) {

        Sesión sesion = new Sesión(idsesion, idusuario, fechainicio, fechafin);
        sesionlist.add(sesion);
        numLibros++;

        return sesion;
    }

    public static Reseña reseña(int idreseña, int idusuario, long idlibro, String descri, int puntaje) {

        Reseña reseña = new Reseña(idreseña, idusuario, idlibro, descri, puntaje);
        critica.add(reseña);
        numLibros++;

        return reseña;
    }

    public static Cliente BuscarCliente(int id) {
        Cliente client = null;

        for (int j = 0; j < cliente.size(); j++) {

            if (id != cliente.get(j).getId()) {
                JOptionPane.showMessageDialog(null, "Usuario no encontrado");
            } else {
                JOptionPane.showMessageDialog(null, "Usuario encontrado, obteniendo");
                client = cliente.get(j);
            }
        }
        return client;
    }

    public static boolean BuscarCorreo(String correo) {

        boolean bandera = false;
        for (int j = 0; j < admi.size(); j++) {

            bandera = !correo.contains(admi.get(j).getCorreo());
        }
        return bandera;

    }

    public static boolean BuscarCorreo2(String correo) {

        boolean bandera = false;
        for (int j = 0; j < cliente.size(); j++) {

            bandera = !correo.contains(cliente.get(j).getCorreo());
        }
        return bandera;

    }

    public static int Comparar(String correo) {

        int bandera = 0;
        for (int j = 0; j < users.size(); j++) {
            if (users.get(j) instanceof Administrador) {
                bandera = 1;
            } else if (users.get(j) instanceof Cliente) {
                bandera = 2;
            } else {
                bandera = 3;
            }
        }
        return bandera;

    }

    public static Administrador BuscarAdmin(int id) {
        Administrador client = null;
        for (int j = 0; j < admi.size(); j++) {

            if (id != admi.get(j).getId()) {
                JOptionPane.showMessageDialog(null, "Usuario no encontrado");

            } else {
                JOptionPane.showMessageDialog(null, "Usuario encontrado, obteniendo");
                client = admi.get(j);
            }
        }
        return client;
    }

    public static void EliminarLibro(int id) {
        for (int j = 0; j < book.size(); j++) {
            if (id != book.get(j).getIdlibro()) {
                JOptionPane.showMessageDialog(null, "ID no encontrado");

            } else {
                JOptionPane.showMessageDialog(null, "ID encontrado, eliminando...");
                book.remove(j);
            }
        }

    }

    public static Libro BuscarLibro(int id) {
        Libro libro = null;
        for (int j = 0; j < book.size(); j++) {

            if (id != book.get(j).getIdlibro()) {
                JOptionPane.showMessageDialog(null, "Libro no encontrado");

            } else {
                JOptionPane.showMessageDialog(null, "Libro encontrado, obteniendo");
                libro = book.get(j);
            }
        }
        return libro;
    }

    public static Sesión Buscarsesion(int id) {
        Sesión sesion = null;
        for (int j = 0; j < sesionlist.size(); j++) {

            if (id != sesionlist.get(j).getIdusuario()) {

            } else {
                JOptionPane.showMessageDialog(null, "Hasta pronto");
                sesion = sesionlist.get(j);
            }
        }
        return sesion;
    }

    public static int BuscarIndice(int id) {
        Libro libro = null;
        int indice = 0;

        for (int j = 0; j < book.size(); j++) {

            if (id != book.get(j).getIdlibro()) {
                JOptionPane.showMessageDialog(null, "Indice no encontrado");

            } else {
                libro = book.get(j);
                indice = book.indexOf(libro);
            }
        }
        return indice;
    }

    public static int BuscarIndice2(int id) {
        Sesión sesion = null;
        int indice = 0;

        for (int j = 0; j < sesionlist.size(); j++) {

            if (id != sesionlist.get(j).getIdusuario()) {
                JOptionPane.showMessageDialog(null, "Indice no encontrado");

            } else {
                sesion = sesionlist.get(j);
                indice = sesionlist.indexOf(sesion);
            }
        }
        return indice;
    }
//   
    public static int getNumLibros() {
        return numLibros;
    }

}
