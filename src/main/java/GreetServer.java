import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;


public class GreetServer {

    private ServerSocket serverSocket;
    static Set<String> identify = new HashSet<>();
    static Map<String, String> mapResults = new HashMap<>();
    static Map<String, Integer> mapResults2 = new HashMap<>();
    static LocalTime localTimeStart = LocalTime.now();
    static List<Socket> clients = new ArrayList<>();


    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        while (true)
            new EchoClientHandler(serverSocket.accept()).start();

    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    private static class EchoClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;



        public EchoClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                clients.add(clientSocket);

                String inputLine;

                while ((inputLine = in.readLine()) != null) {


                    LocalTime localTimeLoop = LocalTime.now();

                    if(localTimeLoop.minusMinutes(1).isBefore(localTimeStart) && inputLine.equals("RESULT")){
                        out.println("Glosowanie trwa");
                    }

                    if(localTimeLoop.minusMinutes(1).isAfter(localTimeStart) && inputLine.equals("RESULT")){
                        out.println("wyniki: "+mapResults2+" liczba głosujących: "+ identify.size());
                        System.out.println("zakończono głosowanie");
                        break;
                    }

                    String array[] = inputLine.split(" ");

                    if (array[0].equals("NODE")) {
                        identify.add(array[1]);
                        out.println("zarejestrowano węzeł");
                    }


                    for(String s : identify){

                        String key = s;

                        if(array.length > 1 && key.equals(array[1])){


                        if(array[0].equals("NEW")) {

                            mapResults.put(array[1]+" "+array[2], array[3]);
                            out.println("Zarejestrowano głosowanie");

                            if(array[3].equals("Y")){
                                mapResults2.put(array[2], 1);
                            }else if(array[3].equals("N")){
                                mapResults2.put(array[2], -1);
                            }
                        }

                        if(array[0].equals("VOTE")){
                            boolean change = true;
                            if(mapResults.get(array[1]+" "+array[2]) != null &&mapResults.get(array[1]+" "+array[2]).equals(array[3])){
                                change = false;
                            }
                            mapResults.put(array[1]+" "+array[2], array[3]);
                            out.println("oddano głos");
                            if(change) {
                                if (array[3].equals("Y")) {
                                    mapResults2.put(array[2], mapResults2.get(array[2]) + 1);

                                } else if (array[3].equals("N")) {
                                    mapResults2.put(array[2], mapResults2.get(array[2]) - 1);

                                }
                            }
                        }

                        if(array[0].equals("PING")){
                            out.println("PONG");

                        }

                        if(array[0].equals("PONG")){
                            out.println("Ignore this ;)");
                        }

                        }
                    }

                }


                System.out.println(mapResults);
                System.out.println("Głosy pozytywne:");
                System.out.println(mapResults2);

                    in.close();
                    out.close();
                    clientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public static void main(String[] args) throws IOException {
            GreetServer server = new GreetServer();
            server.start(5017);
        }

    }



}
