import java.io.*;
import java.util.ArrayList;

public class ChatFilter {
    private String badWordsFileName;
    public ChatFilter(String badWordsFileName) {
        this.badWordsFileName = badWordsFileName;
    }

    public String filter(String msg) throws IOException {
        File f = new File(badWordsFileName);
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        ArrayList<String> words = new ArrayList<>();
        String[] m = new String[0];
        while(true){
            String s = bfr.readLine();
            if(s==null){
                break;
            }
            words.add(s);

        }
        if(msg.contains(" ")){
            m = msg.split(" ");
            for(int i = 0;i<words.size();i++){
                for(int j = 0;j<m.length;j++){
                    if(m[j].toLowerCase().equals(words.get(i))){
                        String re = "";
                        for(int c = 0;c<m[j].length();c++){
                            re += "*";
                        }
                        m[j] = re;
                    }
                }
            }
            msg = "";
            for(int i = 0;i<m.length-1;i++){
                msg += m[i] + " ";
            }
            msg += m[m.length-1];
        }
        else{
            for(int i = 0;i<words.size();i++){
                if(msg.equals(words.get(i))){
                    String re = "";
                    for(int c = 0;c<msg.length();c++){
                        re += "*";
                    }
                    msg = re;
                }
            }
        }
        return msg;
    }
}