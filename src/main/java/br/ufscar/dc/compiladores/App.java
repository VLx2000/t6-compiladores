package br.ufscar.dc.compiladores;

import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;

import br.ufscar.dc.compiladores.LAParser.ProgramaContext;

public class App {

    public static void main(String args[]) throws IOException {

        String entrada = args[0];
        String saida = args[1];

        CharStream cs = CharStreams.fromFileName(entrada);
        LALexer lex = new LALexer(cs);

        try (PrintWriter pw = new PrintWriter(new FileWriter(saida))) {
            CommonTokenStream tokens = new CommonTokenStream(lex);
            LAParser parser = new LAParser(tokens);
            
            /* Analisador sintatico */
            // Registrando o error lister personalizado
            MensagensCustomizadas msgs = new MensagensCustomizadas(pw, false);
            //parser.removeErrorListeners();
            parser.addErrorListener(msgs);
            ProgramaContext arvore = parser.programa();

            /* Analisador semantico */
            LASemantico as = new LASemantico();
            as.visitPrograma(arvore);
            LASemanticoUtils.errosSemanticos.forEach((s) -> pw.write(s));

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
                regra = LALexer.VOCABULARY.getDisplayName(t.getType());

                // Condição em que algum token não foi identificado
                if (regra.equals("SIMBOLO_DESCONHECIDO")) {
                    erroLexico += ("Linha " + line + ": " + token + " - simbolo nao identificado\n");
                }
                // Condição em que um comentário { } não foi fechado corretamente
                else if (regra.equals("COMENTARIO_N_FECHADO")) {
                    erroLexico += ("Linha " + line + ": comentario nao fechado\n");
                }
                // Condição em que uma cadeia " " não foi fechada corretamente
                else if (regra.equals("CADEIA_N_FECHADA")) {
                    erroLexico += ("Linha " + line + ": cadeia literal nao fechada\n");
                }
                // Se não for um dos casos acima será impresso o token com sua regra neste
                // formato
                else {
                    erroLexico += ("<\'" + token + "\'," + regra + ">\n");
                }
            }
            pw.write(erroLexico);
            
            /* Gerador de codigo */
            if(LASemanticoUtils.errosSemanticos.isEmpty() && erroLexico.isEmpty()) { //sintatico para a execuçao antes de chegar aqui
                LAGeradorC agc = new LAGeradorC();
                agc.visitPrograma(arvore);
                pw.print(agc.saida.toString());
            }
            pw.close(); // Fechando arquivo escrito
            
        } catch (IOException ex) {
        }
    }
}