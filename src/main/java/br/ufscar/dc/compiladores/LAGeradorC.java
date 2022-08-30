package br.ufscar.dc.compiladores;

public class LAGeradorC extends LABaseVisitor<Void> {

    StringBuilder saida;
    TabelaDeSimbolos tabela;

    public LAGeradorC() {
        saida = new StringBuilder();
        this.tabela = new TabelaDeSimbolos();
    }
}