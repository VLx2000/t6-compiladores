package br.ufscar.dc.compiladores;

import java.util.ArrayList;
import java.util.HashMap;

import br.ufscar.dc.compiladores.TabelaDeSimbolos.TipoLA;

public class LASemantico extends LABaseVisitor<Void> {

    static Escopos escoposAninhados = new Escopos();
    static HashMap<String, ArrayList<TipoLA>> funcoes = new HashMap<>();     
}