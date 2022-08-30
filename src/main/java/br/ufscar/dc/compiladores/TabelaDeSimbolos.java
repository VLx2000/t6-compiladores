package br.ufscar.dc.compiladores;

import java.util.HashMap;
import java.util.Map;

public class TabelaDeSimbolos {
    public enum TipoLA {

    }
    
    class EntradaTabelaDeSimbolos {
        String nome;
        TipoLA tipo;
        TabelaDeSimbolos tipo_registro;

        private EntradaTabelaDeSimbolos(String nome, TipoLA tipo) {
            this.nome = nome;
            this.tipo = tipo;
            this.tipo_registro  = null;
        }
    }
    
    private final Map<String, EntradaTabelaDeSimbolos> tabela;
    
    public TabelaDeSimbolos() {
        this.tabela = new HashMap<>();
    }
    
    public void adicionar(String nome, TipoLA tipo) {
        tabela.put(nome, new EntradaTabelaDeSimbolos(nome, tipo));
    }
    

    public boolean existe(String nome) {
        return tabela.containsKey(nome);
    }
    
    public TipoLA verificar(String nome) {
        return tabela.get(nome).tipo;
    }
    public EntradaTabelaDeSimbolos getVariavel(String nome){
        return tabela.get(nome);
    }

    public TabelaDeSimbolos getTabelaRegistro(String nome){
        return tabela.get(nome).tipo_registro;
    }
}