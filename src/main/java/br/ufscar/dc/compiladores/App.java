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

            CommonTokenStream tokens = new CommonTokenStream(lex);
            TarParser parser = new TarParser(tokens);
            parser.programa();
        } catch (IOException ex) {
        }
    }
}