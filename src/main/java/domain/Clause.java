package domain;

/**
 * Created by Sergiu on 5/15/2017.
 */
public class Clause implements IClause{
    IClause clause1;
    IClause clause2;
    Operator operator;
    Boolean isEvaluated;

    public ClauseValue getClauseValue() {
        if (operator.equals(Operator.CONJ) && clause1.getClauseValue().equals(ClauseValue.TRUE) && clause2.getClauseValue().equals(ClauseValue.TRUE)) {
            return ClauseValue.TRUE;
        }
        else if (operator.equals(Operator.DISJ) && (clause1.getClauseValue().equals(ClauseValue.TRUE) || clause2.getClauseValue().equals(ClauseValue.TRUE))) {
            return ClauseValue.FALSE;
        }

        if (clause1.getClauseValue().equals(ClauseValue.FALSE) && clause2.getClauseValue().equals(ClauseValue.FALSE)) {
            return ClauseValue.FALSE;
        }
        return ClauseValue.UNKNOWN;
    }

    @Override
    public Boolean isEvaluated() {
        return this.isEvaluated;
    }

    @Override
    public void setEvaluated(Boolean value) {
        this.isEvaluated = value;
    }


    public Clause(IClause clause1, IClause clause2, Operator operator) {
        this.clause1 = clause1;
        this.clause2 = clause2;
        this.operator = operator;
        isEvaluated = false;
    }

    public IClause getClause1() {
        return clause1;
    }

    public Clause setClause1(IClause clause1) {
        this.clause1 = clause1;
        return this;
    }

    public IClause getClause2() {
        return clause2;
    }

    public Clause setClause2(IClause clause2) {
        this.clause2 = clause2;
        return this;
    }

    public Operator getOperator() {
        return operator;
    }

    public Clause setOperator(Operator operator) {
        this.operator = operator;
        return this;
    }
}
