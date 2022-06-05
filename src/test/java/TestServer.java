import org.junit.Test;

import java.io.IOException;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class TestServer {

    @Test
    public void voteTest() throws IOException {
        GreetClient client1 = new GreetClient();
        client1.startConnection("127.0.0.1", 5018);

        String msg0 = client1.sendMessage("NODE Wacek");
        String msg1 = client1.sendMessage("NEW Wacek burmistrzAnna Y wiadomosc");
        String msg2 = client1.sendMessage("NEW Wacek wójtWaldemar Y wiadomość");
        String msg3 = client1.sendMessage("NEW Wacek PrezydentBronek Y wiadomość");

        System.out.println(msg0);
        System.out.println(msg1);
        System.out.println(msg2);
        System.out.println(msg3);
    }



    @Test
    public void voteTest2() throws IOException, InterruptedException {
        GreetClient client2 = new GreetClient();
        client2.startConnection("127.0.0.1", 5018);
        String msg1 = client2.sendMessage("NODE Placek");
        String msg2 = client2.sendMessage("VOTE Placek burmistrzAnna N blablabla");
        String msg3 = client2.sendMessage("NODE Jacek");
        String msg4 = client2.sendMessage("VOTE Jacek wójtWaldemar Y blablabla");

        LocalTime timeStart = LocalTime.now();

        while(true){

            String results = client2.sendMessage("");

            if(!results.equals("")){
                System.out.println(results);
                break;
            }
            System.out.println("test 2, czekam na wyniki");
            Thread.sleep(1000);

        }

        System.out.println("petla zakonczona");
        System.out.println(msg2);
        //System.out.println(msg5);




    }

    @Test
    public void voteTest3() throws IOException, InterruptedException {
        GreetClient client3 = new GreetClient();
        client3.startConnection("127.0.0.1",5018);
        String msg1 = client3.sendMessage("NODE Paweł");
        String msg2 = client3.sendMessage("VOTE Paweł burmistrzAnna N blablabla");
        LocalTime timeStart = LocalTime.now();
        while(true){

            String results = client3.sendMessage("");

            if(!results.equals("")){
                System.out.println(results);
                break;
            }

            System.out.println("czekam na wyniki");
            Thread.sleep(1000);

        }

    }

    @Test
    public void voteTest4() throws IOException, InterruptedException {
        GreetClient client4 = new GreetClient();
        client4.startConnection("127.0.0.1", 5018);
        String msg1 = client4.sendMessage("NODE Kasia");
        String msg2 = client4.sendMessage("VOTE Placek burmistrzAnna Y blablabla");
        String msg3 = client4.sendMessage("NODE Basia");
        String msg4 = client4.sendMessage("VOTE Jacek wójtWaldemar Y blablabla");

        System.out.println(msg1);
        System.out.println(msg2);
        System.out.println(msg3);
        System.out.println(msg4);

    }


}
