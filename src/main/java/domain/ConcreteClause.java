package domain;

/**
 * Created by Sergiu on 5/15/2017.
 */
public class ConcreteClause implements IClause {
    Fact fact;
    Boolean isEvaluated;
    public ClauseValue getClauseValue() {
        return fact.getValue();
    }

    @Override
    public Boolean isEvaluated() {
        return this.isEvaluated;
    }

    @Override
    public void setEvaluated(Boolean value) {
        this.isEvaluated = value;
    }

    public ConcreteClause() {
        isEvaluated = false;
    }

    public ConcreteClause(Fact fact) {
        isEvaluated = false;
        this.fact = fact;
    }

    public Fact getFact() {
        return fact;
    }

    public ConcreteClause setFact(Fact fact) {
        this.fact = fact;
        return this;
    }

}
