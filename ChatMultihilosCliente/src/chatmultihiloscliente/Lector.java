/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatmultihiloscliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Raul
 */
public class Lector extends Thread {

    private BufferedReader reader;
    private Socket socket;
    private boolean continuar;
    private Cliente cliente;
    private Escritor escritor;

    public Lector(Socket socket, Escritor escritor, Cliente cliente) throws IOException {
        this.socket = socket;
        this.cliente = cliente;
        this.escritor = escritor;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        continuar = true;
        String mensaje;
        while (continuar) {
            try {
                mensaje = reader.readLine();
                if (mensaje.contains("Esperamos volver a verte")) {
                    System.out.println(mensaje);
                    mensaje = reader.readLine();
                    System.out.println(mensaje);
                    continuar = false;
                    socket.close();
                }else if(mensaje.startsWith("/ping <--")){
                    escritor.pong("/pong --> "+mensaje.split(" ")[1] + " " + mensaje.split(" ")[2]);
                }else{
                    System.out.println(mensaje);
                }
            } catch (IOException ex) {
                System.out.println("Error leyendo del servidor: " + ex.getMessage());
                continuar = false;
            }
        }
    }
}
