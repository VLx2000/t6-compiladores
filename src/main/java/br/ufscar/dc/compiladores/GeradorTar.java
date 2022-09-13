package br.ufscar.dc.compiladores;

import org.antlr.v4.runtime.tree.TerminalNode;

import br.ufscar.dc.compiladores.TarParser.AcaoContext;
import br.ufscar.dc.compiladores.TarParser.AdicionarContext;
import br.ufscar.dc.compiladores.TarParser.ListarContext;
import br.ufscar.dc.compiladores.TarParser.TamanhoContext;

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
        else if (tar.getText().contains("tar.xz"))
            saida.append("J");
        saida.append("vf");
        //-v : exibe o progresso de criação no terminal;
        //-f : nome do arquivo
        return null;
    }

    @Override
    public Void visitPrograma(TarParser.ProgramaContext ctx) {
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
