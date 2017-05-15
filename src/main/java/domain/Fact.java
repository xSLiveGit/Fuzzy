package domain;

/**
 * Created by Sergiu on 5/15/2017.
 */
public class Fact {
    Character letter;
    String sense;
    ClauseValue value;

    public Fact(Character letter, String sense, ClauseValue value) {
        this.letter = letter;
        this.sense = sense;
        this.value = value;
    }

    public Character getLetter() {
        return letter;
    }

    public Fact setLetter(Character letter) {
        this.letter = letter;
        return this;
    }

    public String getSense() {
        return sense;
    }

    public Fact setSense(String sense) {
        this.sense = sense;
        return this;
    }

    public ClauseValue getValue() {
        return value;
    }

    public Fact setValue(ClauseValue value) {
        this.value = value;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fact)) return false;

        Fact fact = (Fact) o;

        return letter.equals(fact.letter);
    }

    @Override
    public int hashCode() {
        return letter.hashCode();
    }
}
