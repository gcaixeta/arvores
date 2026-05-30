package dev.gustavorosa;

public class ArvoreBinaria {
    private No raiz;

    public ArvoreBinaria() {

    }

    public void inserir(int valor) {
        if(raiz == null) {
            raiz = new No(valor);
        } else {
            raiz.inserir(valor);
        }
    }

    public void imprimeArvore(ImprimirArvore metodo) {
        if (raiz != null) {
            switch (metodo) {
                case ImprimirArvore.EM_ORDEM:
                    raiz.emOrdem();
                    break;
                case ImprimirArvore.PRE_ORDEM:
                    raiz.preOrdem();
                    break;
                case ImprimirArvore.POS_ORDEM:
                    raiz.posOrdem();
                    break;
                case ImprimirArvore.EM_NIVEL:
                    raiz.emNivel();
                    break;
                default:
                    System.out.println("Nao conheco esse...");
            }
            System.out.println();
        } else {
            System.out.println("Arvore vazia");
            System.out.println();
        }
    }

    public static int contagem(No no) {
        return (no == null) ? 0 : 1 + (contagem(no.getNoEsquerdo())) + (contagem(no.getNoDireito()));
    }

    public int contagemDeNos() {
        return contagem(raiz);
    }
}
