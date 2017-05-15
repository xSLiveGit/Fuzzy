import domain.ClauseValue;
import domain.ConcreteClause;
import domain.Fact;
import domain.IClause;
import javafx.util.Pair;
import solve.Loader;
import solve.Solver;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Sergiu on 5/15/2017.
 */
public class Main {
    public static void main(String argv[]) throws URISyntaxException {
        Loader loader = new Loader();
        HashMap<Character, Fact> facts = loader.loadFacts("Facts.txt");
        HashSet<Character> knowledge = Loader.loadKnowledges("Knowledge.txt");
        knowledge.stream().forEach(el->{
            Fact fact = facts.get(el);
            if(null != fact){
                fact.setValue(ClauseValue.TRUE);
            }
        });
        HashSet<Character> targets = Loader.loadTargets("Targets.txt");
        HashMap<IClause, ConcreteClause> rules = Loader.loadRules("Rules.txt",facts);
        Solver solver = new Solver(facts,knowledge,targets,rules);
        Fact fact = solver.forwardChaining();
        System.out.println(fact.getSense());
        Pair<Character,List<Character>> pair = loader.loadReverse("RevTargets.txt");
        List<Character> list = solver.backwardChaining(pair.getKey());
        Boolean all = Boolean.TRUE;
        for(Character el : pair.getValue()){
            if(!list.contains(el)){
                all = Boolean.FALSE;
            }
        }
        if(all.equals(Boolean.TRUE)){
            System.out.println("All codition are satisfied");
        }
        else{
            System.out.println("There exists clauses that aren't satisfied");
        }
    }
}
