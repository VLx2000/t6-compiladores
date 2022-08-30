package br.ufscar.dc.compiladores;

import java.io.PrintWriter;
import java.util.BitSet;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.Token;

public class MensagensCustomizadas implements ANTLRErrorListener {

    PrintWriter pw;
    boolean erroSintatico;
    public MensagensCustomizadas(PrintWriter pw, boolean erroSintatico) {
        this.pw = pw;
        this.erroSintatico = erroSintatico;    
    }

    @Override
    public void reportAmbiguity(Parser arg0, DFA arg1, int arg2, int arg3, boolean arg4, BitSet arg5,
            ATNConfigSet arg6) {
    }

    @Override
    public void reportAttemptingFullContext(Parser arg0, DFA arg1, int arg2, int arg3, BitSet arg4, ATNConfigSet arg5) {
    }

    @Override
    public void reportContextSensitivity(Parser arg0, DFA arg1, int arg2, int arg3, int arg4, ATNConfigSet arg5) {
    }

    @Override
    public void syntaxError(Recognizer<?, ?> arg0, Object arg1, int arg2, int arg3, String arg4,
            RecognitionException arg5) {
        Token t = (Token) arg1;

        if (!erroSintatico) {
            if (t.getType() != Token.EOF) {
                pw.write("Linha " + arg2 + ": erro sintatico proximo a " + t.getText() + "\n");
            }
            else {
                pw.write("Linha " + arg2 + ": erro sintatico proximo a EOF" + "\n");
            }
        }
        erroSintatico = true;
        pw.write("Fim da compilacao\n");
        pw.close(); // Fechando arquivo escrito
    }
}
