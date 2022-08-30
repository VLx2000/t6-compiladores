package br.ufscar.dc.compiladores;

import java.util.ArrayList;
import java.util.HashMap;

import br.ufscar.dc.compiladores.TabelaDeSimbolos.TipoTar;

public class TarSemantico extends LABaseVisitor<Void> {

    static Escopos escoposAninhados = new Escopos();
    static HashMap<String, ArrayList<TipoTar>> funcoes = new HashMap<>();     
}