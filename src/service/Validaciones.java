/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import exception.CadenaInvalidaException;
import exception.NumeroInvalidoException;
import java.util.Scanner;

/**
 *
 * @author Jeremías Paez
 */
public class Validaciones {
    
    private Scanner input = new Scanner(System.in);
    
    //Métodos de validación de entrada
    public int leerEntero(String mensaje) {
        try {
            System.out.print(mensaje);
            return Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            throw new NumeroInvalidoException("Debe ingresar un numero entero valido");
        }
    }
    
    public int leerEnteroNoNegativo(String mensaje) {
        int numero = leerEntero(mensaje);
        
        if (numero < 0){
            throw new NumeroInvalidoException("El número debe ser mayor o igual a 0");
        }
        
        return numero;
    }

    public int leerEnteroPositivo(String mensaje) {
        int numero = leerEntero(mensaje);
        
        if (numero <= 0){
            throw new NumeroInvalidoException("El número debe ser mayor 0");
        }
        
        return numero;
    }     
    
    public long leerLong(String mensaje) {
        try {
            System.out.print(mensaje);
            return Long.parseLong(input.nextLine());
        } catch (NumberFormatException e) {
            throw new NumeroInvalidoException("Debe ingresar un ID numerico valido");
        }
    }
    
    public long leerLongPositivo(String mensaje) {
        long numero = leerLong(mensaje);
        
        if (numero <= 0){
            throw new NumeroInvalidoException("El número debe ser mayor a 0");
        }
        
        return numero;
    }    

    public String leerCadena(String mensaje){
        System.out.println(mensaje);
        String cadena = input.nextLine();
        
        if (cadena == null || cadena.trim().isEmpty()){
            throw new CadenaInvalidaException("El texto ingresado está vacío, intente nuevamente");
        }
        
        return cadena.trim();
    }
    
    public String leerCadenaSinNumeros(String mensaje) {
        System.out.println(mensaje);
        String cadena = input.nextLine();

        if (cadena == null || cadena.trim().isEmpty()) {
            throw new CadenaInvalidaException("El texto ingresado está vacío, intente nuevamente");
        }

        if (cadena.matches(".*\\d.*")) {
            throw new CadenaInvalidaException("El texto ingresado no puede contener números, intente nuevamente");
        }

        return cadena.trim();
    }

    public String leerCadenaSinNumerosNiCaracteresEspeciales(String mensaje) {
        System.out.println(mensaje);
        String cadena = input.nextLine();

        if (cadena == null || cadena.trim().isEmpty()) {
            throw new CadenaInvalidaException("El texto ingresado está vacío, intente nuevamente");
        }

        cadena = cadena.trim();

        if (!cadena.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+")) {
            throw new CadenaInvalidaException("El texto ingresado no puede contener números ni caracteres especiales, intente nuevamente");
        }

        return cadena;
    }    
    
    public double leerDouble(String mensaje) {
        try {
            System.out.print(mensaje);
            return Double.parseDouble(input.nextLine());
        } catch (NumberFormatException e) {
            throw new NumeroInvalidoException("Debe ingresar un ID numerico valido");
        }
    }

    public double leerDoubleNoNegativo(String mensaje) {
        double numero = leerDouble(mensaje);
        
        if (numero < 0){
            throw new NumeroInvalidoException("El número debe ser mayor o igual a 0");
        }
        
        return numero;
    }    
    
}
