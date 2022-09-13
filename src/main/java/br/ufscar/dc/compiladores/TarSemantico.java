package br.ufscar.dc.compiladores;

import java.util.ArrayList;

import br.ufscar.dc.compiladores.TarParser.AdicionarContext;
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

    @Override
    public Void visitAdicionar(AdicionarContext ctx) {
        var fileName = ctx.TAR().getText();
        var splitFile =  fileName.split("\\.");
        
        if(splitFile.length == 2 && splitFile[1].equals("tar")) {
            return super.visitAdicionar(ctx);
        }

        TarSemanticoUtils.adicionarErroSemantico(ctx.start, fileName + ": nao e possivel ADICIONAR em arquivo compactado. Use arquivos somente '.tar'.");
        return super.visitAdicionar(ctx);
    }
}