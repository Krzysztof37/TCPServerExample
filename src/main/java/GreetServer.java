import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.*;


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

                while (true) {

                    inputLine = in.readLine();
                    LocalTime localTimeLoop = LocalTime.now();

                   while(inputLine == null && localTimeLoop.minusMinutes(1).isBefore(localTimeStart) ){
                       localTimeLoop = LocalTime.now();
                       inputLine = in.readLine();
                   }

                   if(inputLine == null){
                       inputLine = "";
                   }


                    if(localTimeLoop.minusMinutes(1).isBefore(localTimeStart) && inputLine.equals("RESULT")){
                        out.println("Glosowanie trwa");
                    }

                    if(localTimeLoop.minusMinutes(1).isAfter(localTimeStart) && inputLine.equals("RESULT")){
                        out.println("wyniki: "+mapResults2+" liczba głosujących: "+ identify.size());
                        System.out.println("zakończono głosowanie");
                        break;
                    }

                    String array2[] = inputLine.split(" ");
                    String array[] = Ascii.asciiCode(inputLine).split(" ");


                    if (array[0].equals("78796869")) {
                        identify.add(array2[1]);
                        out.println("zarejestrowano węzeł");
                    }


                    for(String s : identify){

                        String key = s;

                        if(array.length > 1 && key.equals(array2[1])){


                        if(array[0].equals("786987")) {

                            mapResults.put(array2[1]+" "+array2[2], array2[3]);
                            out.println("Zarejestrowano głosowanie");

                            if(array2[3].equals("Y")){
                                mapResults2.put(array2[2], 1);
                            }else if(array2[3].equals("N")){
                                mapResults2.put(array2[2], -1);
                            }
                        }

                        if(localTimeLoop.minusMinutes(1).isAfter(localTimeStart) && array[0].equals("86798469")){
                            out.println("Proba oddania głosu");
                        }

                        if(localTimeLoop.minusMinutes(1).isBefore(localTimeStart) && array[0].equals("86798469")  ){
                            boolean change = true;
                            if(mapResults.get(array2[1]+" "+array2[2]) != null &&mapResults.get(array2[1]+" "+array2[2]).equals(array2[3])){
                                change = false;
                            }

                            mapResults.put(array2[1]+" "+array2[2], array2[3]);
                            out.println("oddano głos");
                            if(change) {

                                if (array2[3].equals("Y")) {
                                    mapResults2.put(array2[2], mapResults2.get(array2[2]) + 1);

                                } else if (array2[3].equals("N")) {

                                    mapResults2.put(array2[2], mapResults2.get(array2[2]) - 1);

                                }
                            }
                        }

                        if(array[0].equals("80737871")){
                            out.println("PONG");

                        }

                        if(array[0].equals("80797871")){
                            out.println("Ignore this ;)");
                        }

                        }
                    }

                    if(localTimeLoop.minusMinutes(1).isAfter(localTimeStart)){
                        for (Socket z : clients) {
                            if (z != null) {
                                PrintStream outToClient = null;
                                try {
                                    outToClient = new PrintStream(z.getOutputStream());
                                    outToClient.println("wyniki: "+mapResults2+" liczba głosujących: "+ identify.size());

                                } catch (IOException e) {
                                    System.out.println("wystąpił błąð");
                                    e.printStackTrace();
                                }
                            }
                        }

                        break;

                    }


                }


                System.out.println(mapResults);
                System.out.println("Głosy pozytywne:");
                System.out.println(mapResults2);

//                    in.close();
//                    out.close();
//                    clientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public static void main(String[] args) throws IOException {
            GreetServer server = new GreetServer();
            server.start(5018);
        }

    }



}
