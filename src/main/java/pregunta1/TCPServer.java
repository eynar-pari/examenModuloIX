package pregunta1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mik
 */
public class TCPServer {
    private ServerSocket serverSocket;
    private ObjectInputStream flujoEntrada;
    private ObjectOutputStream flujoSalida;

    public TCPServer(int puerto) throws IOException {
        this.serverSocket = new ServerSocket(puerto);
    }

    public Socket inicia() throws IOException {
        System.out.println("[Servidor] esperando conexion...");
        Socket socket = this.serverSocket.accept();
        System.out.println("[Servidor] conexion establecida");
        this.flujoSalida = new ObjectOutputStream(socket.getOutputStream());
        this.flujoEntrada = new ObjectInputStream(socket.getInputStream());
        return socket;
    }

    public void termina(Socket socket) throws IOException {
        System.out.println("[Servidor] cerrando conexion ..");
        this.flujoEntrada.close();
        this.flujoSalida.close();
        socket.close();
        this.serverSocket.close();
        System.out.println("[Servidor] conexiones cerradas");
    }

    public List<Persona> recibe() throws IOException, ClassNotFoundException {
        List<Persona> personas = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            Persona persona = (Persona) this.flujoEntrada.readObject();
            personas.add(persona);
            System.out.println("[Servidor] Persona  recibida: " + persona);
        }
        return personas;
    }

    public void envia(List<Persona> personas) throws IOException {
        for (Persona persona : personas) {
            this.flujoSalida.writeObject(persona);
            System.out.println("[Servidor] Enviando persona: " + persona);
        }
    }

    public static void main(String[] args) {
        int puerto_servidor = 49171;
        try {
            TCPServer servidor = new TCPServer(puerto_servidor);
            Socket socket = servidor.inicia();

            List<Persona> personasRecibidas = servidor.recibe();

            servidor.termina(socket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}