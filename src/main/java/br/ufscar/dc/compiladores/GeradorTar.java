package br.ufscar.dc.compiladores;

public class GeradorTar extends LABaseVisitor<Void> {

    StringBuilder saida;
    TabelaDeSimbolos tabela;

    public GeradorTar() {
        saida = new StringBuilder();
        this.tabela = new TabelaDeSimbolos();
    }
}