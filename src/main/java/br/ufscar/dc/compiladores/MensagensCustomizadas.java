package br.ufscar.dc.compiladores;

import java.io.PrintWriter;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;

public class MensagensCustomizadas extends BaseErrorListener {
    PrintWriter pw;

    public MensagensCustomizadas(PrintWriter pw) {
        this.pw = pw;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> arg0, Object arg1, int arg2, int arg3, String arg4,
            RecognitionException arg5) {
        Token t = (Token) arg1;

        if (t.getType() != Token.EOF) {
            pw.write("Linha " + arg2 + ": erro sintatico proximo a " + t.getText() + "\n");
        } else {
            pw.write("Linha " + arg2 + ": erro sintatico proximo a EOF" + "\n");
        }

        pw.close(); // Fechando arquivo escrito
    }
}
