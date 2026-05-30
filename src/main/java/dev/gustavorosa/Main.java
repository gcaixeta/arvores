package dev.gustavorosa;

public class Main {
    public static void main(String[] args) {
        var minhaArvre = new ArvoreBinaria();

        minhaArvre.inserir(9);
        minhaArvre.inserir(1);
        minhaArvre.inserir(2);
        minhaArvre.inserir(8);
        minhaArvre.inserir(2);
        minhaArvre.inserir(3);

        minhaArvre.imprimeArvore(ImprimirArvore.EM_ORDEM);
        minhaArvre.imprimeArvore(ImprimirArvore.PRE_ORDEM);
        minhaArvre.imprimeArvore(ImprimirArvore.POS_ORDEM);
        minhaArvre.imprimeArvore(ImprimirArvore.EM_NIVEL);

        System.out.println("A arvre tem " + minhaArvre.contagemDeNos() + " nos.");
    }
}