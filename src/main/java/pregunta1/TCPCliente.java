package pregunta1;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mik
 */
public class TCPCliente {
    private String serverIP;
    private int puerto;
    private Socket socket;
    private ObjectInputStream flujoEntrada;
    private ObjectOutputStream flujoSalida;

    public TCPCliente(String serverIP, int puerto) {
        this.serverIP = serverIP;
        this.puerto = puerto;
    }

    public void inicia() throws IOException {
        System.out.println("[Cliente] Estableciendo conexión...");
        this.socket = new Socket(this.serverIP, this.puerto);
        this.flujoSalida = new ObjectOutputStream(socket.getOutputStream());
        this.flujoEntrada = new ObjectInputStream(socket.getInputStream());
        System.out.println("[Cliente] Conexión establecida.");
    }

    public void termina() throws IOException {
        System.out.println("[Cliente] Cerrando conexion..");
        this.flujoEntrada.close();
        this.flujoSalida.close();
        this.socket.close();
        System.out.println("[Cliente] Conexiones cerradas..");
    }

    public List<Persona> recibe() throws IOException, ClassNotFoundException {
        List<Persona> personas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Persona persona = (Persona) this.flujoEntrada.readObject();
            personas.add(persona);
            System.out.println("[Cliente] Persona " + (i+1) + " recibida: " + persona);
        }
        return personas;
    }

    public void envia(List<Persona> personas) throws IOException {
        for (Persona persona : personas) {
            this.flujoSalida.writeObject(persona);
            System.out.println("[Cliente] Enviando persona: " + persona);
        }
    }

    public static void main(String[] args) {
        int puerto_servidor = 49171;
        TCPCliente cliente = new TCPCliente("localhost", puerto_servidor);
        try {
            cliente.inicia();

            List<Persona> personasParaEnviar = new ArrayList<>();
            for (int i = 1; i <= 100; i++) {
                personasParaEnviar.add(new Persona("Eynar" + i, String.valueOf(i)));
                Thread.sleep(50);
            }
            cliente.envia(personasParaEnviar);

          //  List<Persona> personasRecibidas = cliente.recibe();

            cliente.termina();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}