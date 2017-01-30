package hr.foi.air.dajsve.Helpers;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Filip on 29.1.2017..
 */

public class PretrazivanjeKeyword implements SearchDataListener{

    public String method(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length()-1)=='x') {
            str = str.substring(0, str.length()-1);
        }
        return str;
    }

    @Override
    public ArrayList<String> onDataArrived(String keywords, List<String> allElements, List<String> ID, boolean ok) {
        int Slength = keywords.length();
        int brojac=-1;
        String word ="";
        List<String> ListaKljučnihRiječi = new ArrayList<>();
        for (int i = 0 ; i<Slength;i++)
        {
            if(Character.isWhitespace(keywords.charAt(i)) || i==Slength-1){
                System.out.println(word);
                if(word!=""){
                    if(i!=Slength-1) {
                        word = word.substring(0, word.length() - 1);
                    }
                    ListaKljučnihRiječi.add(word);
                    word="";
                }
            }
            else
            {
                word+=keywords.charAt(i);
            }
        }

        Set<String> targetSet = new HashSet();
        Set<Integer> targetSetInt = new HashSet();
        String sviKeywords = TextUtils.join("|", ListaKljučnihRiječi);
        System.out.println(sviKeywords);
        ArrayList<String> allMatchedElements = new ArrayList<>();
        for (String element : allElements)
        {
            brojac++;
            Matcher matcher = Pattern.compile(sviKeywords.toLowerCase()).matcher(element.toLowerCase());
            if (matcher.find()) {
                targetSet.add(element);
                System.out.println("OVO : "+element+ " HASH : "+ ID.get(brojac));
                allMatchedElements.add(ID.get(brojac));
            }

        }
        return allMatchedElements;
    }
}
