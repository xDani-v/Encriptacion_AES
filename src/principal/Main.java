/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Daniel Jazmany Villano Escobar
 */
public class Main {

    public static String llave =  "bandera";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.print("Ingrese su texto a encriptar: ");
        String pas =  sc.next();
        System.out.println("Entrada: "+pas);
        String encriptacion =Encriptar(pas);
        System.out.println("Encriptacion: "+encriptacion);
        System.out.println("Desencriptar: "+ Desencriptar(encriptacion));
    }

    public static SecretKeySpec crearClave(String password) {
        try {
            byte[] cadena = password.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            cadena = md.digest(cadena);
            cadena = Arrays.copyOf(cadena, 16);
            SecretKeySpec secretkeyspek = new SecretKeySpec(cadena, "AES");
            return secretkeyspek;
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String Encriptar(String txt) {
        try {
            SecretKeySpec sks = crearClave(llave);
            Cipher cip = Cipher.getInstance("AES");
            cip.init(Cipher.ENCRYPT_MODE, sks);
            byte[] cadena =txt.getBytes("UTF-8");
            byte[] encriptada = cip.doFinal(cadena);
            String cadena_encritpada = Base64.getEncoder().encodeToString(encriptada);
            return cadena_encritpada;
        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            return null;
        }
    }
    
     public static String Desencriptar(String encriptacion) {
        try {
            SecretKeySpec sks = crearClave(llave);
            Cipher cip = Cipher.getInstance("AES");
            cip.init(Cipher.DECRYPT_MODE, sks);
            byte[] cadena = Base64.getDecoder().decode(encriptacion);
            byte[] desencriptacion = cip.doFinal(cadena);
            String cadena_desencritpada = new String(desencriptacion);
            return cadena_desencritpada;
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            return null;
        }
    }
}
