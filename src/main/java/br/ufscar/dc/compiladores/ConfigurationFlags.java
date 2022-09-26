package br.ufscar.dc.compiladores;

import java.util.HashMap;


// Esta classe contém e expõe publicamente um objeto de HashMap
// sua responsabilidade é atuar como camada superior desse HashMap e conceder a ele valores incias
// esses valores iniciais são as configurações 'por padrão' do compilador
public class ConfigurationFlags {
    public HashMap<String, String> map = new HashMap<>();

    public ConfigurationFlags(){
        map.put("NIVEL_VERBOSO", "1");
        map.put("INTERATIVO", null);
        map.put("MANTER_PERMISSOES", null);
        // apesar de null, ainda assim o padrão continua sendo 'gnu'
        // pois está de acordo com o padrão a última versão do programa TAR
        // caso mude no futuro, o compilador continuará seguindo o padrão
        map.put("FORMATO", null);
    }
}
