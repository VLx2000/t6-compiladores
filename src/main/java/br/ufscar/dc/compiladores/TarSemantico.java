package br.ufscar.dc.compiladores;

public class TarSemantico extends TarBaseVisitor<Void> {

    @Override
    public Void visitPrograma(TarParser.ProgramaContext ctx) {
        return super.visitPrograma(ctx);
    }

    @Override
    public Void visitAcao(TarParser.AcaoContext ctx){            
        return super.visitAcao(ctx);
    }
}