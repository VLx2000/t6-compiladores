package br.ufscar.dc.compiladores;

import java.util.ArrayList;

import br.ufscar.dc.compiladores.TarParser.ComprimirContext;
import br.ufscar.dc.compiladores.TarParser.ExtrairContext;

public class TarSemantico extends TarBaseVisitor<Void> {

    @Override
    public Void visitPrograma(TarParser.ProgramaContext ctx) {
        return super.visitPrograma(ctx);
    }

    @Override
    public Void visitAcao(TarParser.AcaoContext ctx) {
        return super.visitAcao(ctx);
    }

    @Override
    public Void visitComprimir(ComprimirContext ctx) {
        return super.visitComprimir(ctx);
    }

    @Override
    public Void visitExtrair(ExtrairContext ctx) {
        // TODO Auto-generated method stub
        ArrayList<String> files = new ArrayList<>();
        for (var arq : ctx.ARQUIVO()) {
            String currArq = arq.getText();
            if (files.contains(currArq)) {
                TarSemanticoUtils.adicionarErroSemantico(ctx.start, currArq + " apareceu mais de uma vez.");
            } else {
                files.add(currArq);
            }
        }
        return super.visitExtrair(ctx);
    }
}