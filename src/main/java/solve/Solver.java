package solve;

import domain.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Sergiu on 5/15/2017.
 */
public class Solver {
    HashMap<Character, Fact> facts;
    HashSet<Character> knowledge;
    HashSet<Character> targets;
    HashMap<IClause,ConcreteClause> rules;

    public Solver(HashMap<Character, Fact> facts, HashSet<Character> knowledge, HashSet<Character> targets, HashMap<IClause, ConcreteClause> rules) {
        this.facts = facts;
        this.knowledge = knowledge;
        this.targets = targets;
        this.rules = rules;
    }

    public Fact forwardChaining(){
        Boolean changed;
        ConcreteClause clause = null;
        do{
            changed = Boolean.FALSE;
            for(IClause el : rules.keySet()){
                if(el.getClauseValue().equals(ClauseValue.TRUE)){
                    changed = Boolean.TRUE;
                    Character c = rules.get(el).getFact().getLetter();
                    knowledge.add(c);
                    for(Character character : targets){
                        if(character.equals(c)){
                            return facts.get(character);
                        }
                    }
                }
            }
        }while(changed.equals(Boolean.TRUE));
        return null;
    }

    public List<Character> backwardChaining(Character resultChar){
        Queue<Character> queue = new LinkedList<>();
        queue.add(resultChar);
        Character c;
        Boolean found = Boolean.FALSE;
        HashMap<Character,Character> searched = new HashMap<>();
        Character currentCharacter;

        while(!queue.isEmpty() ){
            currentCharacter = queue.remove();
            for(IClause el : rules.keySet()){
                if(rules.get(el).getFact().getLetter().equals(currentCharacter)){
                    if(searched.get(rules.get(el).getFact().getLetter()) == null){
                        c = rules.get(el).getFact().getLetter();
                        queue.addAll(getAllLetters(el));

                    }
                }

            }
            searched.put(currentCharacter,currentCharacter);
        }
        return searched.keySet().stream().collect(Collectors.toList());
    }

    public List<Character> getAllLetters(IClause clause){
        List<Character> list = new ArrayList<>();
        if(clause instanceof ConcreteClause){
            list.add(((ConcreteClause)clause).getFact().getLetter());
        }
        else if(clause instanceof Clause){
            Clause clause1 = (Clause)clause;
            List<Character> list1 = getAllLetters(clause1.getClause1());
            List<Character> list2 = getAllLetters(clause1.getClause2());
            list.addAll(list1);
            list.addAll(list2);
        }
        return list;
    }
}
