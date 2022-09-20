package br.ufscar.dc.compiladores;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;

public class TarSemanticoUtils {
    public static List<String> errosSemanticos = new ArrayList<>();
    
    public static void adicionarErroSemantico(Token t, String mensagem) {
        int linha = t.getLine();
        errosSemanticos.add(String.format("Linha %d: %s \n", linha, mensagem));
    }
}