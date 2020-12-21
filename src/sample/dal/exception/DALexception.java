package sample.dal.exception;

public class DALexception extends Exception{
    public DALexception() {
        super();
    }

    public DALexception(String messege) {
        super(messege);
    }

    public DALexception(String messege, Exception ex) {
        super(messege, ex);
    }
}
