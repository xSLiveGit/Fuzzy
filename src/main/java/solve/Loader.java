package solve;

import domain.*;
import javafx.util.Pair;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Sergiu on 5/15/2017.
 */
public class Loader {
    public HashMap<Character,Fact> loadFacts(String fileName){
        HashMap<Character,Fact> map = new HashMap<Character, Fact>();
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream is = loader.getResourceAsStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = reader.readLine()) != null) {
                map.put(new Character(line.charAt(0)),new Fact(line.charAt(0),line.substring(2), ClauseValue.UNKNOWN));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public Pair<Character,List<Character>> loadReverse(String fileName){
        Pair<Character,List<Character>> pair = null;
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream is = loader.getResourceAsStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            line = reader.readLine();
            Character c = line.charAt(0);
            line = reader.readLine();
            List<Character> list = Arrays.stream(line.split(" ")).map(el->el.charAt(0)).collect(Collectors.toList());
            return new Pair<>(c, list);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pair;
    }

    public static HashSet<Character> loadKnowledges(String fileName){
        return loadChars(fileName);
    }

    public static HashSet<Character> loadTargets(String fileName){
        return loadChars(fileName);
    }

    private static HashSet<Character> loadChars(String fileName){
        File file = null;
        HashSet<Character> set = new HashSet<Character>();
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream is = loader.getResourceAsStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                set.add(line.charAt(0));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }

    public static HashMap<IClause,ConcreteClause> loadRules(String fileName,HashMap<Character,Fact> facts){
        HashMap<IClause,ConcreteClause> map = new HashMap<>();
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream is = loader.getResourceAsStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                Integer position = line.lastIndexOf("atunci");
                IClause cond = parse(line.substring(0,position),facts);
                Character c = line.substring(position + 6).replaceAll("\\s", "").charAt(0);
                ConcreteClause result = new ConcreteClause(facts.get(c));
                map.put(
                        cond,
                        result
                );
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    private static IClause parse(String line,HashMap<Character,Fact> facts){
        Integer position = findHighLevelOperatorPosition(line);
        if(null == position){
            Character c = line.replaceAll("\\s", "").charAt(0);
            return new ConcreteClause(facts.get(c));
        }
        else{
            IClause clause1 = parse(line.substring(0,position),facts);
            IClause clause2 = parse(line.substring(position+1),facts);
            Operator operator;
            if(line.charAt(position) == '^'){
                operator = Operator.CONJ;
            }
            else {
                operator = Operator.DISJ;
            }
            return new Clause(clause1,clause2,operator);
        }
    }

    private static Integer findHighLevelOperatorPosition(String line){
        Integer level = 0;
        for(Integer i=0;i<line.length();i++){
            if(line.charAt(i) == '('){
                level++;
            }
            if(line.charAt(i) == ')'){
                level--;
            }
            if(level.equals(0) && (line.charAt(i) == '^' || line.charAt(i) == 'v')){
                return i;
            }
        }
        return null;
    }
}
