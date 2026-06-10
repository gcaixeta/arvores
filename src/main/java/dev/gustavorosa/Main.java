package dev.gustavorosa;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Arvore Binaria ===");
        Arvore arvoreBinaria = new ArvoreBinaria();
        demonstrar(arvoreBinaria);

        System.out.println("=== Arvore AVL ===");
        Arvore arvoreAVL = new ArvoreAVL();
        demonstrar(arvoreAVL);

        System.out.println("=== Arvore Red-Black ===");
        Arvore arvoreRB = new ArvoreRB();
        demonstrar(arvoreRB);
    }

    private static void demonstrar(Arvore arvore) {
        int[] valores = {9, 1, 2, 8, 2, 3, 10, 15, -5, 7, 12, -2};
        for (int v : valores) {
            arvore.inserir(v);
            arvore.imprime(ImprimirArvore.DESENHO);
            arvore.inserirAleatorio(-20, 20);
            arvore.imprime(ImprimirArvore.DESENHO);
        }

        System.out.println("Buscar 8: " + arvore.buscar(8));
        System.out.println("Buscar 99: " + arvore.buscar(99));

        arvore.remover(2);
        System.out.println("Buscar 2 apos remover: " + arvore.buscar(2));

        System.out.println("Desenho:");

        arvore.imprime(ImprimirArvore.DESENHO);
        System.out.println();
    }


}
