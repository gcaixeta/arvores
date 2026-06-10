package dev.gustavorosa;

import java.util.Random;

public class ArvoreBinaria implements Arvore {
    private No raiz;

    public ArvoreBinaria() {

    }

    @Override
    public void inserir(int valor) {
        if(raiz == null) {
            raiz = new No(valor);
        } else {
            raiz.inserir(valor);
        }
    }

    @Override
    public void inserirAleatorio(int min, int max) {
        inserir(new Random().nextInt(min, max + 1));
    }

    @Override
    public boolean buscar(int valor) {
        return buscar(raiz, valor);
    }

    private boolean buscar(No no, int valor) {
        if (no == null) return false;
        if (valor == no.getValor()) return true;
        return valor < no.getValor()
                ? buscar(no.getNoEsquerdo(), valor)
                : buscar(no.getNoDireito(), valor);
    }

    @Override
    public void remover(int valor) {
        raiz = remover(raiz, valor);
    }

    @Override
    public void imprime(ImprimirArvore estrategia) {
        if (raiz != null) {
            switch (estrategia) {
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
                case ImprimirArvore.DESENHO:
                    desenhar();
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

    private No remover(No no, int valor) {
        if (no == null) return null;

        if (valor < no.getValor()) {
            no.setNoEsquerdo(remover(no.getNoEsquerdo(), valor));
        } else if (valor > no.getValor()) {
            no.setNoDireito(remover(no.getNoDireito(), valor));
        } else {
            if (no.getNoEsquerdo() == null) return no.getNoDireito();
            if (no.getNoDireito() == null) return no.getNoEsquerdo();

            No sucessor = menorValor(no.getNoDireito());
            no.setValor(sucessor.getValor());
            no.setNoDireito(remover(no.getNoDireito(), sucessor.getValor()));
        }
        return no;
    }

    private No menorValor(No no) {
        No atual = no;
        while (atual.getNoEsquerdo() != null) {
            atual = atual.getNoEsquerdo();
        }
        return atual;
    }

    public void desenhar() {
        if (raiz == null) return;
        System.out.println(raiz.getValor());
        desenhar(raiz.getNoEsquerdo(), "", raiz.getNoDireito() == null);
        desenhar(raiz.getNoDireito(), "", true);
    }

    private void desenhar(No no, String prefixo, boolean ehUltimo) {
        if (no == null) return;
        System.out.println(prefixo + (ehUltimo ? "└── " : "├── ") + no.getValor());
        String novoPrefixo = prefixo + (ehUltimo ? "    " : "│   ");
        desenhar(no.getNoEsquerdo(), novoPrefixo, no.getNoDireito() == null);
        desenhar(no.getNoDireito(), novoPrefixo, true);
    }

    public static int contagem(No no) {
        return (no == null) ? 0 : 1 + (contagem(no.getNoEsquerdo())) + (contagem(no.getNoDireito()));
    }

    public int contagemDeNos() {
        return contagem(raiz);
    }
}
