package dev.gustavorosa;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class ArvoreAVL implements Arvore {

    private NoAVL raiz;

    private int altura(NoAVL no) {
        return no == null ? 0 : no.altura;
    }

    private int fatorBalanceamento(NoAVL no) {
        return no == null ? 0 : altura(no.esquerdo) - altura(no.direito);
    }

    private void atualizarAltura(NoAVL no) {
        if (no != null) {
            no.altura = 1 + Math.max(altura(no.esquerdo), altura(no.direito));
        }
    }

    private NoAVL rotacaoDireita(NoAVL y) {
        NoAVL x = y.esquerdo;
        NoAVL T2 = x.direito;
        x.direito = y;
        y.esquerdo = T2;
        atualizarAltura(y);
        atualizarAltura(x);
        return x;
    }

    private NoAVL rotacaoEsquerda(NoAVL x) {
        NoAVL y = x.direito;
        NoAVL T2 = y.esquerdo;
        y.esquerdo = x;
        x.direito = T2;
        atualizarAltura(x);
        atualizarAltura(y);
        return y;
    }

    @Override
    public void inserir(int valor) {
        raiz = inserir(raiz, valor);
    }

    @Override
    public void inserirAleatorio(int min, int max) {
        inserir(new Random().nextInt(min, max + 1));
    }

    private NoAVL inserir(NoAVL no, int valor) {
        if (no == null) return new NoAVL(valor);

        if (valor < no.valor) {
            no.esquerdo = inserir(no.esquerdo, valor);
        } else {
            no.direito = inserir(no.direito, valor);
        }

        atualizarAltura(no);

        int fb = fatorBalanceamento(no);

        if (fb > 1 && valor < no.esquerdo.valor) {
            return rotacaoDireita(no);
        }
        if (fb < -1 && valor > no.direito.valor) {
            return rotacaoEsquerda(no);
        }
        if (fb > 1 && valor > no.esquerdo.valor) {
            no.esquerdo = rotacaoEsquerda(no.esquerdo);
            return rotacaoDireita(no);
        }
        if (fb < -1 && valor < no.direito.valor) {
            no.direito = rotacaoDireita(no.direito);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    @Override
    public boolean buscar(int valor) {
        return buscar(raiz, valor);
    }

    private boolean buscar(NoAVL no, int valor) {
        if (no == null) return false;
        if (valor == no.valor) return true;
        return valor < no.valor ? buscar(no.esquerdo, valor) : buscar(no.direito, valor);
    }

    @Override
    public void remover(int valor) {
        raiz = remover(raiz, valor);
    }

    @Override
    public void imprime(ImprimirArvore estrategia) {
        if (raiz != null) {
            switch (estrategia) {
                case EM_ORDEM:
                    emOrdem(raiz);
                    break;
                case PRE_ORDEM:
                    preOrdem(raiz);
                    break;
                case POS_ORDEM:
                    posOrdem(raiz);
                    break;
                case EM_NIVEL:
                    emNivel(raiz);
                    break;
                case DESENHO:
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

    private NoAVL remover(NoAVL no, int valor) {
        if (no == null) return null;

        if (valor < no.valor) {
            no.esquerdo = remover(no.esquerdo, valor);
        } else if (valor > no.valor) {
            no.direito = remover(no.direito, valor);
        } else {
            if (no.esquerdo == null) return no.direito;
            if (no.direito == null) return no.esquerdo;

            NoAVL sucessor = menorValor(no.direito);
            no.valor = sucessor.valor;
            no.direito = remover(no.direito, sucessor.valor);
        }

        if (no == null) return null;

        atualizarAltura(no);

        int fb = fatorBalanceamento(no);

        if (fb > 1 && fatorBalanceamento(no.esquerdo) >= 0) {
            return rotacaoDireita(no);
        }
        if (fb > 1 && fatorBalanceamento(no.esquerdo) < 0) {
            no.esquerdo = rotacaoEsquerda(no.esquerdo);
            return rotacaoDireita(no);
        }
        if (fb < -1 && fatorBalanceamento(no.direito) <= 0) {
            return rotacaoEsquerda(no);
        }
        if (fb < -1 && fatorBalanceamento(no.direito) > 0) {
            no.direito = rotacaoDireita(no.direito);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    private NoAVL menorValor(NoAVL no) {
        NoAVL atual = no;
        while (atual.esquerdo != null) {
            atual = atual.esquerdo;
        }
        return atual;
    }

    public void desenhar() {
        if (raiz == null) return;
        System.out.println(raiz.valor);
        desenhar(raiz.esquerdo, "", raiz.direito == null);
        desenhar(raiz.direito, "", true);
    }

    private void desenhar(NoAVL no, String prefixo, boolean ehUltimo) {
        if (no == null) return;
        System.out.println(prefixo + (ehUltimo ? "└── " : "├── ") + no.valor);
        String novoPrefixo = prefixo + (ehUltimo ? "    " : "│   ");
        desenhar(no.esquerdo, novoPrefixo, no.direito == null);
        desenhar(no.direito, novoPrefixo, true);
    }

    private void emOrdem(NoAVL no) {
        if (no == null) return;
        emOrdem(no.esquerdo);
        System.out.println(no.valor);
        emOrdem(no.direito);
    }

    private void preOrdem(NoAVL no) {
        if (no == null) return;
        System.out.println(no.valor);
        preOrdem(no.esquerdo);
        preOrdem(no.direito);
    }

    private void posOrdem(NoAVL no) {
        if (no == null) return;
        posOrdem(no.esquerdo);
        posOrdem(no.direito);
        System.out.println(no.valor);
    }

    private void emNivel(NoAVL raiz) {
        Queue<NoAVL> fila = new LinkedList<>();
        fila.add(raiz);

        while (!fila.isEmpty()) {
            int tamanhoFila = fila.size();
            for (int i = 0; i < tamanhoFila; i++) {
                NoAVL atual = fila.poll();
                if (atual != null) {
                    System.out.println(atual.valor);
                    if (atual.esquerdo != null) fila.add(atual.esquerdo);
                    if (atual.direito != null) fila.add(atual.direito);
                }
            }
            System.out.println();
        }
    }

    private int contagem(NoAVL no) {
        return no == null ? 0 : 1 + contagem(no.esquerdo) + contagem(no.direito);
    }
}
