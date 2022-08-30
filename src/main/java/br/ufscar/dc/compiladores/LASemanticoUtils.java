package br.ufscar.dc.compiladores;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;

public class LASemanticoUtils {
    public static List<String> errosSemanticos = new ArrayList<>();
    
    public static void adicionarErroSemantico(Token t, String mensagem) {
        int linha = t.getLine();
        errosSemanticos.add(String.format("Linha %d: %s", linha, mensagem));
    }
}