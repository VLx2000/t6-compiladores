package br.ufscar.dc.compiladores;

import java.util.HashMap;

public class ConfigurationFlags {
    public HashMap<String, String> map = new HashMap<>();

    public ConfigurationFlags(){
        map.put("NIVEL_VERBOSO", "3");
        map.put("INTERATIVO", null);
        map.put("MANTER_PERMISSOES", null);
        map.put("FORMATO", null);
    }
}
