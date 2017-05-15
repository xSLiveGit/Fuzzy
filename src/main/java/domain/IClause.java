package domain;

/**
 * Created by Sergiu on 5/15/2017.
 */
public interface IClause {
    ClauseValue getClauseValue();
    Boolean isEvaluated();
    void setEvaluated(Boolean value);
}
