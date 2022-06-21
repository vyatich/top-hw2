package socket_executors;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpSocketExecutor implements Closeable {

    private final BufferedReader reader;
    private final BufferedWriter writer;
    private final Socket socket;

    public TcpSocketExecutor(String address, int port) {
        try {
            this.socket = new Socket(address, port);
            this.reader = createReader();
            this.writer = createWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TcpSocketExecutor(ServerSocket server) {
        try {
            this.socket = server.accept();
            this.reader = createReader();
            this.writer = createWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return this.socket;
    }

    public void write(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String read() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedReader createReader() throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private BufferedWriter createWriter() throws IOException {
        return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    @Override
    public void close() throws IOException {
        reader.close();
        writer.close();
        socket.close();
    }
}