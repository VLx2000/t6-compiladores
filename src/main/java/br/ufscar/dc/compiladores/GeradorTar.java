package br.ufscar.dc.compiladores;

import org.antlr.v4.runtime.tree.TerminalNode;

import br.ufscar.dc.compiladores.TarParser.AcaoContext;
import br.ufscar.dc.compiladores.TarParser.AdicionarContext;
import br.ufscar.dc.compiladores.TarParser.ConfigsContext;
import br.ufscar.dc.compiladores.TarParser.ConfiguracaoContext;
import br.ufscar.dc.compiladores.TarParser.ListarContext;
import br.ufscar.dc.compiladores.TarParser.TamanhoContext;

public class GeradorTar extends TarBaseVisitor<Void> {
    StringBuilder saida;
    ConfigurationFlags configs = new ConfigurationFlags();

    public GeradorTar() {
        saida = new StringBuilder();
    }

    public Void verificaTar(TerminalNode tar) {
        //-v : exibe o progresso de criação no terminal;
        //-f : nome do arquivo
        
        if (tar.getText().contains("tar.gz"))
            saida.append("z");
        else if (tar.getText().contains("tar.bz2"))
            saida.append("j");
        else if (tar.getText().contains("tar.xz"))
            saida.append("J");

        if (configs.map.get("NIVEL_VERBOSO").equals("1")) {
            saida.append("v");
        } else if (configs.map.get("NIVEL_VERBOSO").equals("2")) {
            saida.append("vv");
        } else if (configs.map.get("NIVEL_VERBOSO").equals("3")) {
            saida.append("vvv");
        } 

        if (configs.map.get("INTERATIVO") != null) {
            saida.append("w");
        } 

        if (configs.map.get("MANTER_PERMISSOES") != null) {
            saida.append("p");
        } 
        
        if (configs.map.get("FORMATO") != null) {
            saida.append(" --format=" + configs.map.get("FORMATO"));
        }
        saida.append(" -f");
        return null;
    }

    @Override
    public Void visitPrograma(TarParser.ProgramaContext ctx) {
        if (ctx.configuracao() != null){
            visitConfiguracao(ctx.configuracao());
        }
        for (AcaoContext acao : ctx.acao()) {
            saida.append("tar -");
            if (acao.comprimir() != null) {
                visitComprimir(acao.comprimir());
            } else if (acao.extrair() != null) {
                visitExtrair(acao.extrair());
            }  else if (acao.tamanho() != null) {
                visitTamanho(acao.tamanho());
            }  else if (acao.listar() != null) {
                visitListar(acao.listar());
            }  else if (acao.adicionar() != null) {
                visitAdicionar(acao.adicionar());
            } 
            saida.append("\n");
        }
        return null;
    }

    @Override
    public Void visitConfiguracao(ConfiguracaoContext ctx) {
        ctx.configs().forEach(config -> visitConfigs(config));
        return super.visitConfiguracao(ctx);
    }

    @Override
    public Void visitConfigs(ConfigsContext ctx) {       
        if (ctx.CONFIG_KEYS().getText().equals("NIVEL_VERBOSO")){
            configs.map.put("NIVEL_VERBOSO", ctx.CONFIG_VALUES().getText());
        } else if (ctx.CONFIG_KEYS().getText().equals("INTERATIVO")){
            configs.map.put("INTERATIVO", "");
        } else if (ctx.CONFIG_KEYS().getText().equals("MANTER_PERMISSOES")){
            configs.map.put("MANTER_PERMISSOES", "");
        } else if (ctx.CONFIG_KEYS().getText().equals("FORMATO")){
            configs.map.put("FORMATO", ctx.CONFIG_VALUES().getText());
        } 
        return super.visitConfigs(ctx);
    }

    @Override
    public Void visitComprimir(TarParser.ComprimirContext ctx) {
        saida.append("c");
        verificaTar(ctx.TAR());
        saida.append(" " + ctx.TAR().getText());
        ctx.ARQUIVO().forEach(arquivo -> saida.append(" " + arquivo.getText()));
        return null;
    }

    @Override
    public Void visitExtrair(TarParser.ExtrairContext ctx) {
        saida.append("x");
        verificaTar(ctx.TAR());
        saida.append(" " + ctx.TAR().getText());
        if (ctx.DIRETORIO() != null){
            saida.append(" -C");
            saida.append(" " + ctx.DIRETORIO().getText());
        }
        ctx.ARQUIVO().forEach(arquivo -> saida.append(" " + arquivo.getText()));    
        return null;
    }

    @Override
    public Void visitListar(ListarContext ctx) {
        saida.append("tvf ");
        saida.append(ctx.TAR().getText());
        return super.visitListar(ctx);
    }

    @Override
    public Void visitTamanho(TamanhoContext ctx) {
        saida.append("czf - ");
        saida.append(ctx.TAR().getText());
        saida.append(" | wc -c");
        return super.visitTamanho(ctx);
    }

    @Override
    public Void visitAdicionar(AdicionarContext ctx) {
        saida.append("rvf ");
        saida.append(ctx.TAR().getText());
        ctx.ARQUIVO().forEach(arquivo -> saida.append(" " + arquivo.getText()));
        return super.visitAdicionar(ctx);
    }
}
