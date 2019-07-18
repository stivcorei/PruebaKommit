import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * Clase Main
 * @author Jhonnattan Prieto
 *
 */
public class Main {
	
	//Lista de rutas posibles con su respectiva distancia
	static ArrayList<String[]> rutasPosibles = new ArrayList<String[]>(); 
	
	//Lista auxiliar de ciudades para ir armando las rutas
	static ArrayList<String> ciudades = new ArrayList<String>();
	
	//Distancia acumulada de cada ruta
	static int distancia = 0;
	
	/**
	 * Método main
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Ruta del archivo que contiene los datos de las rutas
		String rutaArchivo = "C:/Users/Jhonnattan/Desktop/input.txt";
		ArrayList<String[]> rutas = new ArrayList<String[]>();
		leerArchivo(rutaArchivo, rutas);
		
		String origen = rutas.get(rutas.size()-1)[0];
		String destino = rutas.get(rutas.size()-1)[1];
		
		ciudades.add(rutas.get(rutas.size()-1)[0]);
				
		calcularRuta(rutas, origen, destino);
		
		generarRutaCorta(origen, destino);
		
//		imprimir();
	}
	
	/**
	 * Método leerArchivo
	 * 
	 * Este método sirve para leer datos desde un archivo de texto plano
	 * 
	 * @param rutaArchivo - Ruta del archivo
	 * @param rutas - Lista donde se guardaran las rutas contenidas en el archivo plano
	 */
	public static void leerArchivo(String rutaArchivo, ArrayList<String[]> rutas) {
		File archivo = new File (rutaArchivo);
		FileReader fr;
		try {
			fr = new FileReader (archivo);
			BufferedReader br = new BufferedReader(fr);
			String linea;
	         while((linea=br.readLine())!=null) {
	        	 String[] aux = new String[3];
	        	 aux = linea.split(",");
	        	 rutas.add(aux);
	         }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Método calcularRuta
	 * 
	 * Este método sirve para calcular las rutas entre dos ciudades
	 * 
	 * @param rutas - Lista de rutas entre ciudades con su respectivo peso o distancia
	 * @param origen - Ciudad de origen
	 * @param destino - Ciudad de destino
	 * @return
	 */
	public static boolean calcularRuta(ArrayList<String[]> rutas, String origen, String destino) {
				
		for (int i = 0; i < rutas.size()-1; i++) {
			
			if(rutas.get(i)[0].equals(origen) && rutas.get(i)[1].equals(destino)) {
				distancia+=Integer.parseInt(rutas.get(i)[2]);
				ciudades.add(rutas.get(i)[1]);
				
				String[] res = new String[2];
				res[0] = extraerRuta();
				res[1] = ""+distancia;
				rutasPosibles.add(res);
				
				ciudades.remove(ciudades.size()-1);
				distancia-=Integer.parseInt(rutas.get(i)[2]);
				
				return true;				
			}else if(rutas.get(i)[0].equals(origen)) {
				distancia+=Integer.parseInt(rutas.get(i)[2]);
				ciudades.add(rutas.get(i)[1]);
				if(!calcularRuta(rutas, rutas.get(i)[1], destino)) {
					ciudades.remove(ciudades.size()-1);
					distancia-=Integer.parseInt(rutas.get(i)[2]);
				}else if(i!=rutas.size()-2) {					
					ciudades.remove(ciudades.size()-1);
					distancia-=Integer.parseInt(rutas.get(i)[2]);
				}else {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Método extraerRuta
	 * 
	 * Este método concatena ciudades para crear una cadena la cual es una ruta
	 * 
	 * @return - Retorna la ruta concatenada
	 */
	public static String extraerRuta() {
		String ruta = "";
		
		for(int i=0; i<ciudades.size(); i++) {
			ruta += ciudades.get(i)+" - ";
		}
		
		return ruta;
	}
	
	/**
	 * Método imprimir
	 * 
	 * Este método imprime por consola todas las rutas posibles entre las ciudades que se están analizando
	 * 
	 */
	public static void imprimir() {
		for (int i = 0; i < rutasPosibles.size(); i++) {
			System.out.println(rutasPosibles.get(i)[0]+"------"+rutasPosibles.get(i)[1]);
		}
	}
	
	/**
	 * Método generarRutaCorta
	 * 
	 * Este método analiza las rutas encontradas y muestra en pantalla la ruta más corta entre las ciudades analizadas
	 * 
	 * @param origen - Ciudad de origen
	 * @param destino - Ciudad de destino
	 */
	public static void generarRutaCorta(String origen, String destino) {
		int distanciaMenor = Integer.parseInt(rutasPosibles.get(0)[1]);
		int indice = 0;
		
		for(int i=1; i<rutasPosibles.size();i++) {
			
			int aux = Integer.parseInt(rutasPosibles.get(i)[1]);
			
			if(distanciaMenor>aux) {
				distanciaMenor = aux;
				indice = i;
			}
		}
		
		JOptionPane.showMessageDialog(null, "La ruta más corta entre "+origen+" y "+destino+" es\n"+rutasPosibles.get(indice)[0]+" con "+rutasPosibles.get(indice)[1]+" de distancia");
	}
}
