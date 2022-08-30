package br.ufscar.dc.compiladores;

import java.util.HashMap;
import java.util.Map;

public class TabelaDeSimbolos {
    public enum TipoTar {

    }
    
    class EntradaTabelaDeSimbolos {
        String nome;
        TipoTar tipo;
        TabelaDeSimbolos tipo_registro;

        private EntradaTabelaDeSimbolos(String nome, TipoTar tipo) {
            this.nome = nome;
            this.tipo = tipo;
            this.tipo_registro  = null;
        }
    }
    
    private final Map<String, EntradaTabelaDeSimbolos> tabela;
    
    public TabelaDeSimbolos() {
        this.tabela = new HashMap<>();
    }
    
    public void adicionar(String nome, TipoTar tipo) {
        tabela.put(nome, new EntradaTabelaDeSimbolos(nome, tipo));
    }
    

    public boolean existe(String nome) {
        return tabela.containsKey(nome);
    }
    
    public TipoTar verificar(String nome) {
        return tabela.get(nome).tipo;
    }
    public EntradaTabelaDeSimbolos getVariavel(String nome){
        return tabela.get(nome);
    }

    public TabelaDeSimbolos getTabelaRegistro(String nome){
        return tabela.get(nome).tipo_registro;
    }
}