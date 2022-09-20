package br.ufscar.dc.compiladores;

import java.util.ArrayList;

import br.ufscar.dc.compiladores.TarParser.AdicionarContext;
import br.ufscar.dc.compiladores.TarParser.ComprimirContext;
import br.ufscar.dc.compiladores.TarParser.ConfigsContext;
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
    public Void visitConfigs(ConfigsContext ctx) {
        String nivelVerbosoValores = "0123";
        String[] formatosValidos = { "gnu", "oldgnu", "pax", "posix", "ustar", "v7" };

        var keys = ctx.CONFIG_KEYS().getText();

        if (keys.equals("NIVEL_VERBOSO")) {
            if (ctx.CONFIG_VALUES() != null) {
                var values = ctx.CONFIG_VALUES().getText();
                if (!nivelVerbosoValores.contains(values)) {
                    TarSemanticoUtils.adicionarErroSemantico(ctx.start, keys + " não aceita " + values);
                }
            } else {
                TarSemanticoUtils.adicionarErroSemantico(ctx.start, keys + " deve ter algum valor");
            }
        } else if (keys.equals("FORMATO")) {
            if (ctx.CONFIG_VALUES() != null) {
                var values = ctx.CONFIG_VALUES().getText();
                boolean achou = false;
                for (String formato : formatosValidos) {
                    if (formato.equals(values)) {
                        achou = true;
                    }
                }
                if (!achou) {
                    TarSemanticoUtils.adicionarErroSemantico(ctx.start, keys + " não aceita " + values);
                }
            } else {
                TarSemanticoUtils.adicionarErroSemantico(ctx.start, keys + " deve ter algum valor");
            }
        }

        return super.visitConfigs(ctx);
    }

    @Override
    public Void visitComprimir(ComprimirContext ctx) {
        return super.visitComprimir(ctx);
    }

    @Override
    public Void visitExtrair(ExtrairContext ctx) {
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
        var splitFile = fileName.split("\\.");

        if (splitFile.length == 2 && splitFile[1].equals("tar")) {
            return super.visitAdicionar(ctx);
        }

        TarSemanticoUtils.adicionarErroSemantico(ctx.start,
                fileName + ": nao e possivel ADICIONAR em arquivo compactado. Use arquivos somente '.tar'.");
        return super.visitAdicionar(ctx);
    }
}