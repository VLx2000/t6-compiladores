package br.ufscar.dc.compiladores;

import br.ufscar.dc.compiladores.TarParser.AcaoContext;

public class GeradorTar extends TarBaseVisitor<Void> {
    StringBuilder saida;

    public GeradorTar() {
        saida = new StringBuilder();
    }

    @Override
    public Void visitPrograma(TarParser.ProgramaContext ctx) {
        saida.append("tar -");
        for (AcaoContext acao : ctx.acao()){
            if (acao.comprimir() != null){
                visitComprimir(acao.comprimir());
            } else if (acao.extrair() != null){
                visitExtrair(acao.extrair());
            }
        }
        return null;
    }

    @Override
    public Void visitComprimir(TarParser.ComprimirContext ctx) {
        saida.append("c");
        ctx.ARQUIVO().forEach(arquivo -> saida.append(" " + arquivo.getText()));
        saida.append(ctx.TAR().getText());
        return null;
    }

    @Override
    public Void visitExtrair(TarParser.ExtrairContext ctx) {
        saida.append("x");
        saida.append(ctx.DIRETORIO().getText());
        ctx.ARQUIVO().forEach(arquivo -> saida.append(" " + arquivo.getText()));
        saida.append(ctx.TAR().getText());
        return null;
    }
}
