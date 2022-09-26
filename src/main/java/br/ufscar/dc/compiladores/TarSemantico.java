package br.ufscar.dc.compiladores;

import java.util.ArrayList;

import br.ufscar.dc.compiladores.TarParser.AdicionarContext;
import br.ufscar.dc.compiladores.TarParser.ConfigsContext;
import br.ufscar.dc.compiladores.TarParser.ExtrairContext;

public class TarSemantico extends TarBaseVisitor<Void> {

    @Override
    public Void visitConfigs(ConfigsContext ctx) {
        String nivelVerbosoValores = "0123";
        String[] formatosValidos = { "gnu", "oldgnu", "pax", "posix", "ustar", "v7" };

        var keys = ctx.CONFIG_KEYS().getText();

        // caso detectado a config NIVEL_VERBOSO ela deve: ter um valor associado e
        // esse valor deve estar no intervalo de 0...3 incluso
        if (keys.equals("NIVEL_VERBOSO")) {
            if (ctx.CONFIG_VALUES() != null) {
                var values = ctx.CONFIG_VALUES().getText();
                // valor em intervalo inválido
                if (!nivelVerbosoValores.contains(values)) {
                    TarSemanticoUtils.adicionarErroSemantico(ctx.start, keys + " não aceita " + values);
                }
            // não possui valor associado
            } else {
                TarSemanticoUtils.adicionarErroSemantico(ctx.start, keys + " deve ter algum valor");
            }

        // caso detectado a config FORMATO ela deve: ter um valor associado e
        // esse valor deve ser um dentre "gnu", "oldgnu", "pax", "posix", "ustar", "v7"
        } else if (keys.equals("FORMATO")) {
            // verifica se há valor associado
            if (ctx.CONFIG_VALUES() != null) {
                var values = ctx.CONFIG_VALUES().getText();
                boolean achou = false;
                // verifica se o valor associado pertence a lista de valores válidos
                for (String formato : formatosValidos) {
                    if (formato.equals(values)) {
                        achou = true;
                    }
                }
                // se não for valor válido, disparar erro semântico
                if (!achou) {
                    TarSemanticoUtils.adicionarErroSemantico(ctx.start, keys + " não aceita " + values);
                }
            // se não há valor, disparar erro semântico
            } else {
                TarSemanticoUtils.adicionarErroSemantico(ctx.start, keys + " deve ter algum valor");
            }
        }

        return super.visitConfigs(ctx);
    }

    @Override
    public Void visitExtrair(ExtrairContext ctx) {
        // o comando extrair é semânticamente válido
        // caso os arquivos listados sejam todos diferentes, não se pode especificar o mesmo arquivo 2x
        ArrayList<String> files = new ArrayList<>();
        for (var arq : ctx.ARQUIVO()) {
            String currArq = arq.getText();
            // caso um arquivo se repita, disparar erro semântico
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
        // adicionar é semânticamente válido caso,
        // seja especificado um arquivo TAR: .tar, .tar.gz, tar.bz2 e .tar.xz
        if (ctx.TAR() != null) {
            var fileName = ctx.TAR().getText();
            var splitFile = fileName.split("\\.");

            if (splitFile.length == 2 && splitFile[1].equals("tar")) {
                return super.visitAdicionar(ctx);
            }
            TarSemanticoUtils.adicionarErroSemantico(ctx.start,
                    fileName + ": nao e possivel ADICIONAR em arquivo compactado. Use somente arquivos '.tar'.");
        } else {
            TarSemanticoUtils.adicionarErroSemantico(ctx.start,
                    "Erro ao ADICIONAR em arquivo compactado. Use somente arquivos '.tar'.");
        }
        return super.visitAdicionar(ctx);
    }
}