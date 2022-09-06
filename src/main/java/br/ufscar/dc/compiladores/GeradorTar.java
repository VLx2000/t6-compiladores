package br.ufscar.dc.compiladores;

import org.antlr.v4.runtime.tree.TerminalNode;

import br.ufscar.dc.compiladores.TarParser.AcaoContext;

public class GeradorTar extends TarBaseVisitor<Void> {
    StringBuilder saida;

    public GeradorTar() {
        saida = new StringBuilder();
    }

    public Void verificaTar(TerminalNode tar) {
        if (tar.getText().contains("tar.gz"))
            saida.append("z");
        else if (tar.getText().contains("tar.bz2"))
            saida.append("j");
        saida.append("vf");
        //-v : exibe o progresso de criação no terminal;
        //-f : nome do arquivo
        return null;
    }

    @Override
    public Void visitPrograma(TarParser.ProgramaContext ctx) {
        saida.append("tar -");
        for (AcaoContext acao : ctx.acao()) {
            if (acao.comprimir() != null) {
                visitComprimir(acao.comprimir());
            } else if (acao.extrair() != null) {
                visitExtrair(acao.extrair());
            }
        }
        return null;
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
        ctx.ARQUIVO().forEach(arquivo -> saida.append(" " + arquivo.getText()));
        if (ctx.DIRETORIO() != null){
            saida.append(" -C ");
            saida.append(ctx.DIRETORIO().getText());
        }
        return null;
    }
}
