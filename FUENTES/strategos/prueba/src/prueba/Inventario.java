package prueba;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Inventario {
	
	public static void main (String args[]) {
		
		String nombre = "";
		int serial=0;
		Double peso=0.0;
		List telefonos = new ArrayList();
		
		System.out.println("hola bienvenido al sitema de inventario de telefonos");
		
		System.out.println("ingrese los datos del telefono:");
		
		Scanner leer= new Scanner(System.in);
		
		System.out.println("nombre: ");
		nombre=leer.nextLine();
		System.out.println("serial");
		serial=leer.nextInt();
		System.out.println("peso:");
		peso=leer.nextDouble();
		
		Telefono telefono = new Telefono();
		
		telefono.setNombre(nombre);
		telefono.setSerial(serial);
		telefono.setPeso(peso);
		
		telefonos.add(telefono);
		
		
		
		
		
		//Telefono tel = new Telefono(nombre, serial, peso);
		
		System.out.println("Telefono 1: ");
		
		System.out.println(telefono.toString());
		
	}
	
}
