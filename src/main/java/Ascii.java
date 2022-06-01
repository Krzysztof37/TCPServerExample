import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ascii {

    private  Map<String,Integer> asciiMap = new HashMap<>();

    private final static List<Character> alphabet = List.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O','P','Q','R','S','T','U','V','W','X','Y', 'Z');


    private static Map<Character, Integer> giveMap(List<Character> list){
        Map<Character, Integer> alphabetMap = new HashMap<>();
        int counter = 65;

        for (Character s : list) {
            alphabetMap.put(s, counter);
            counter++;
        }
         alphabetMap.put(' ', 32);
        return alphabetMap;
    }

    public static String asciiCode(String request){
        String result = "";
        char array[] = request.toCharArray();
        Map<Character, Integer> map = giveMap(alphabet);

        for(int i = 0; i < array.length; i++) {
            char letter = array[i];

            for (Map.Entry<Character, Integer> entry : map.entrySet()) {
                char key = entry.getKey();
                Integer valueAscii = entry.getValue();

                if(letter == key){

                    result = result + valueAscii+";";
                }




            }

        }

        System.out.println(result);
        return result;
    }


    public static void main(String[] args) {

        asciiCode("ALA MA KOTA");




    }



}
