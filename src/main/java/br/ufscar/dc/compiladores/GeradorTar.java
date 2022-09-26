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

    public Void verificaVerboso() {
        // adiciona a flag que indica o quao verboso o programa sera de acordo com a configuração do compilador
        if (configs.map.get("NIVEL_VERBOSO").equals("1")) {
            saida.append("v");
        } else if (configs.map.get("NIVEL_VERBOSO").equals("2")) {
            saida.append("vv");
        } else if (configs.map.get("NIVEL_VERBOSO").equals("3")) {
            saida.append("vvv");
        } 
        return null;
    }

    public Void verificaTar(TerminalNode tar) {
        //-v : exibe o progresso de criação no terminal;
        //-f : nome do arquivo
        

        // discrimina qual o formato de arquivo compactado será utilizado
        // para arquivos .tar, não se faz necessário flags adicionais

        // para tar.gz, precisa-se da flag -z
        if (tar.getText().contains("tar.gz"))
            saida.append("z");
        // para tar.bz2, precisa-se da flag -j
            else if (tar.getText().contains("tar.bz2"))
            saida.append("j");
        // para tar.xz, precisa-se da flag -J
        else if (tar.getText().contains("tar.xz"))
            saida.append("J");

        verificaVerboso();

        // adiciona as flags caso o modo INTERATIVO seja configurado
        if (configs.map.get("INTERATIVO") != null) {
            saida.append("w");
        } 

        // adiciona as flags caso seja desejado manter as permissões
        if (configs.map.get("MANTER_PERMISSOES") != null) {
            saida.append("p");
        } 
        
        // adiciona as flags caso seja desejado especificar o formato do arquivo TAR compactado
        if (configs.map.get("FORMATO") != null) {
            saida.append(" --format=" + configs.map.get("FORMATO"));
        }

        // a flag -f ao fim para ocorrer a listagem dos arquivos a serem compactados
        saida.append(" -f");
        return null;
    }

    @Override
    public Void visitPrograma(TarParser.ProgramaContext ctx) {
        // caso seja passado um header de configuração, realizar o setup da mesma
        if (ctx.configuracao() != null){
            visitConfiguracao(ctx.configuracao());
        }

        // itera sobre as ações descritas no programa e designa seu gerador de código específico
        for (AcaoContext acao : ctx.acao()) {
            // prefixo que independe da ação
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
            
            // quebra de linha ao fim da geração de código para cada ação
            saida.append("\n");
        }
        return null;
    }

    @Override
    public Void visitConfiguracao(ConfiguracaoContext ctx) {
        // itera sobre a lista de configurações dadas
        ctx.configs().forEach(config -> visitConfigs(config));
        return super.visitConfiguracao(ctx);
    }

    @Override
    public Void visitConfigs(ConfigsContext ctx) {      
        // discrimina cada configuração e muda seus valores padrões caso necessário 
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
        // flag específica que indica a compressão de um arquivo
        saida.append("c");
        // setup com as flags dadas as configurações do compilador
        verificaTar(ctx.TAR());
        // especifica o nome do arquivo tar
        saida.append(" " + ctx.TAR().getText());
        // escreve os arquivos que entrarão na compressão
        ctx.ARQUIVO().forEach(arquivo -> saida.append(" " + arquivo.getText()));
        return null;
    }

    @Override
    public Void visitExtrair(TarParser.ExtrairContext ctx) {
        // flag específica que indica a extração de um arquivo
        saida.append("x");
        // setup com as flags dadas as configurações do compilador
        verificaTar(ctx.TAR());
        // especifica o nome do arquivo tar
        saida.append(" " + ctx.TAR().getText());
        // caso seja especificado um diretorio, passar a flag necessária e o caminho
        if (ctx.DIRETORIO() != null){
            saida.append(" -C");
            saida.append(" " + ctx.DIRETORIO().getText());
        }
        //escreve os arquivos que deseja-se extrair, caso não haja especificação, todos serão extraidos
        ctx.ARQUIVO().forEach(arquivo -> saida.append(" " + arquivo.getText()));    
        return null;
    }

    @Override
    public Void visitListar(ListarContext ctx) {
        // adiciona as flags especificar da acao listar
        saida.append("t");
        verificaVerboso();
        saida.append("f ");
        saida.append(ctx.TAR().getText());
        return super.visitListar(ctx);
    }

    @Override
    public Void visitTamanho(TamanhoContext ctx) {
        // flags utilizadas para a verificação do tamanjo de um arquivo TAR
        saida.append("czf - ");
        // especifica o nome do arquivo TAR
        saida.append(ctx.TAR().getText());
        // pipe para o programa wc
        saida.append(" | wc -c");
        return super.visitTamanho(ctx);
    }

    @Override
    public Void visitAdicionar(AdicionarContext ctx) {
        // adiciona as flags especificar da acao adicionar
        saida.append("r");
        verificaVerboso();
        saida.append("f ");
        saida.append(ctx.TAR().getText());
        // lista os arquivos que devem ser adicionados
        ctx.ARQUIVO().forEach(arquivo -> saida.append(" " + arquivo.getText()));
        return super.visitAdicionar(ctx);
    }
}
