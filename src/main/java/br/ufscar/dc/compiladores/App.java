package br.ufscar.dc.compiladores;

import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import br.ufscar.dc.compiladores.TarParser.ProgramaContext;

public class App {

    public static void main(String args[]) throws IOException {

        String entrada = args[0];
        String saida = args[1];

        CharStream cs = CharStreams.fromFileName(entrada);
        TarLexer lex = new TarLexer(cs);
        Token token = null;
        try (PrintWriter pw = new PrintWriter(new FileWriter(saida))) {
           
            // (1) Realização da Análise Léxica do código
            while ((token = lex.nextToken()).getType() != Token.EOF) {
                String tipo = TarLexer.VOCABULARY.getDisplayName(token.getType());
                
                // Captura de erros léxicos. O compilador encerra caso encontre erros léxicos
                if(tipo.equals("UNKNOWN")){
                    pw.write("Erro lexico proximo a linha " + token.getLine());
                    return;
                }             
            }
            // (2) Realização da Análise Semântica do código
            lex.reset();
            CommonTokenStream tokens = new CommonTokenStream(lex);
            TarParser parser = new TarParser(tokens);
            ProgramaContext arvore = parser.programa();
            TarSemantico as = new TarSemantico();
            as.visitPrograma(arvore);

            // Captura de erros semânticos
            TarSemanticoUtils.errosSemanticos.forEach((s) -> pw.write(s));

            // Caso hajam erros semânticos, o compilador não prossegue para a Geração de Código
            if(TarSemanticoUtils.errosSemanticos.size() > 0) {
                return;
            }

            // (3) Realização da Geração de Código a partir da entrada dada
            GeradorTar agc = new GeradorTar();
            agc.visitPrograma(arvore);
            pw.print(agc.saida.toString());
        } catch (IOException ex) {
        }
    }
}