package br.ufscar.dc.compiladores;

import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;

// import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class App {

    public static void main(String args[]) throws IOException {

        String entrada = args[0];
        String saida = args[1];

        CharStream cs = CharStreams.fromFileName(entrada);
        TarLexer lex = new TarLexer(cs);

        try (PrintWriter pw = new PrintWriter(new FileWriter(saida))) {
            
            // CommonTokenStream tokens = new CommonTokenStream(lex);
            // TarParser parser = new TarParser(tokens);

            Token t = null;
            Integer line;
            String regra, token, erroLexico = "";

            /* Analisador lexico */
            while ((t = lex.nextToken()).getType() != Token.EOF) {
                // Obtendo linha atual
                line = t.getLine();
                // Obtendo token atual
                token = t.getText();
                // Obtendo tipo
                regra = TarLexer.VOCABULARY.getDisplayName(t.getType());

                // Condição em que algum token não foi identificado
                if (regra.equals("UNKNOWN")) {
                    erroLexico += ("Linha " + line + ": " + token + " - simbolo nao identificado\n");
                }
                else {
                    erroLexico += ("<\'" + token + "\'," + regra + ">\n");
                }                
            }
            if (erroLexico.isEmpty()) {
                // Resetando fluxo de tokens para a análise sintática
                lex.reset();
                
                CommonTokenStream tokens = new CommonTokenStream(lex);
                TarParser parser = new TarParser(tokens);

                // Registrando o error lister personalizado
                MensagensCustomizadas msgs = new MensagensCustomizadas(pw, false);
                //parser.removeErrorListeners();
                parser.addErrorListener(msgs);

                parser.programa();
            }
            
            if (!erroLexico.isEmpty()) {
                pw.write(erroLexico);
                pw.write("Fim da compilacao\n");
                pw.close(); // Fechando arquivo escrito
            }
            pw.write(erroLexico);
        } catch (IOException ex) {
        }
    }
}