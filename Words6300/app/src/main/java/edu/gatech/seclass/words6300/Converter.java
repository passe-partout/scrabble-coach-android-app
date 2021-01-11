package edu.gatech.seclass.words6300;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;


/*
From https://medium.com/@amit.bhandari/storing-java-objects-other-than-primitive-types-in-room-database-11e45f4f6d22
 */
public class Converter {
    private static Gson gson = new Gson();

    @TypeConverter
    public static List<Letter> stringToLetterList(String data) {

        if (data == null) {
            return new ArrayList<Letter>();
        }
        Type type = new TypeToken<List<Letter>>() {}.getType();
        return gson.fromJson(data, type);
    }

    @TypeConverter
    public static String letterListToString(List<Letter> someObjects) {
        if (someObjects == null) {
            return (null);
        }
        Type listType = new TypeToken<List<Letter>>(){}.getType();
        return gson.toJson(someObjects, listType);
    }

    @TypeConverter
    public static Map<Word, Integer> stringToMapWordInt(String data) {

        if (data == null || data.equals("")) {
            return new HashMap<Word, Integer>();
        }
        System.out.println("trying to load from db: " + data);

        HashMap<Word, Integer> wordStatMap = new HashMap<Word, Integer>();
        String[] commaTokens = data.split("\\,");
        for (int x=0; x<commaTokens.length; x++) {
            System.out.println(commaTokens[x]);
            Word w = new Word();
            String[] semiToken = commaTokens[x].split("\\:");
            //semi token will have 2 items:  list of words separated by ";" and the integer value.
            String[] wordItems = semiToken[0].split("\\;");
            //word items should have 4 entries: id, word, pointvalue, numtimesplayed:
            w.setId( Integer.parseInt(wordItems[0]));
            w.setWord( wordItems[1]);
            w.setPointValue(Integer.parseInt(wordItems[2]));
            w.setNumOfTimesPlayed( Integer.parseInt( wordItems[3]));

            wordStatMap.put( w, Integer.parseInt(semiToken[1]));
        }
        return wordStatMap;
    }


    @TypeConverter
    public static String mapWordIntToString(Map<Word, Integer> someObjects) {

        if (someObjects == null) {
            return (null);
        }
        StringBuffer sb = new StringBuffer();
        Iterator<Map.Entry<Word, Integer>> itr1 = someObjects.entrySet().iterator();
        while (itr1.hasNext()) {
            Map.Entry<Word, Integer> entry = itr1.next();
            sb.append(entry.getKey().getId()).append(";");
            sb.append(entry.getKey().getWord()).append(";");
            sb.append(entry.getKey().getPointValue()).append(";");
            sb.append(entry.getKey().getNumOfTimesPlayed()).append(":");
            sb.append(entry.getValue());
            if (itr1.hasNext()) {
                sb.append(",");
            }
        }
        System.out.print("writing to DB: " + sb.toString());
        return sb.toString();
    }

    @TypeConverter
    public static HashMap<Character, Letter> stringToMapCharLetter(String data) {

        if (data == null) {
            return new HashMap<Character, Letter> ();
        }
        Type type = new TypeToken<HashMap<Character, Letter>>() {}.getType();
        return gson.fromJson(data, type);
    }


    @TypeConverter
    public static String mapCharLetterToString(HashMap<Character, Letter> someObjects) {
        if (someObjects == null) {
            return (null);
        }
        Type type = new TypeToken<HashMap<Character, Letter>>() {}.getType();
        return gson.toJson(someObjects, type);
    }

    @TypeConverter
    public static List<Game> stringToGameList(String data) {

        if (data == null) {
            return new ArrayList<Game>();
        }
        Type type = new TypeToken<List<Game>>() {}.getType();
        return gson.fromJson(data, type);
    }

    @TypeConverter
    public static String gameListToString(List<Game> someObjects) {
        if (someObjects == null) {
            return (null);
        }
        Type listType = new TypeToken<List<Game>>() {}.getType();
        return gson.toJson(someObjects, listType);
    }
}

