package dev.gustavorosa;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class ArvoreRB implements Arvore {

    private NoRB raiz = NoRB.NIL;

    private boolean isPreto(NoRB no) {
        return no.cor == NoRB.Cor.PRETO;
    }

    private boolean isVermelho(NoRB no) {
        return no.cor == NoRB.Cor.VERMELHO;
    }

    private void rotacaoEsquerda(NoRB x) {
        NoRB y = x.direito;
        x.direito = y.esquerdo;
        if (y.esquerdo != NoRB.NIL) y.esquerdo.pai = x;
        y.pai = x.pai;
        if (x.pai == NoRB.NIL) {
            raiz = y;
        } else if (x == x.pai.esquerdo) {
            x.pai.esquerdo = y;
        } else {
            x.pai.direito = y;
        }
        y.esquerdo = x;
        x.pai = y;
    }

    private void rotacaoDireita(NoRB y) {
        NoRB x = y.esquerdo;
        y.esquerdo = x.direito;
        if (x.direito != NoRB.NIL) x.direito.pai = y;
        x.pai = y.pai;
        if (y.pai == NoRB.NIL) {
            raiz = x;
        } else if (y == y.pai.esquerdo) {
            y.pai.esquerdo = x;
        } else {
            y.pai.direito = x;
        }
        x.direito = y;
        y.pai = x;
    }

    @Override
    public void inserir(int valor) {
        NoRB novo = new NoRB(valor);
        NoRB pai = NoRB.NIL;
        NoRB atual = raiz;

        while (atual != NoRB.NIL) {
            pai = atual;
            if (valor < atual.valor) {
                atual = atual.esquerdo;
            } else {
                atual = atual.direito;
            }
        }

        novo.pai = pai;
        if (pai == NoRB.NIL) {
            raiz = novo;
        } else if (valor < pai.valor) {
            pai.esquerdo = novo;
        } else {
            pai.direito = novo;
        }

        consertarPraCima(novo);
    }

    private void consertarPraCima(NoRB z) {
        while (z.pai != null && isVermelho(z.pai)) {
            NoRB avo = z.pai.pai;
            if (z.pai == avo.esquerdo) {
                NoRB tio = avo.direito;
                if (isVermelho(tio)) {
                    z.pai.cor = NoRB.Cor.PRETO;
                    tio.cor = NoRB.Cor.PRETO;
                    avo.cor = NoRB.Cor.VERMELHO;
                    z = avo;
                } else {
                    if (z == z.pai.direito) {
                        z = z.pai;
                        rotacaoEsquerda(z);
                    }
                    z.pai.cor = NoRB.Cor.PRETO;
                    avo.cor = NoRB.Cor.VERMELHO;
                    rotacaoDireita(avo);
                }
            } else {
                NoRB tio = avo.esquerdo;
                if (isVermelho(tio)) {
                    z.pai.cor = NoRB.Cor.PRETO;
                    tio.cor = NoRB.Cor.PRETO;
                    avo.cor = NoRB.Cor.VERMELHO;
                    z = avo;
                } else {
                    if (z == z.pai.esquerdo) {
                        z = z.pai;
                        rotacaoDireita(z);
                    }
                    z.pai.cor = NoRB.Cor.PRETO;
                    avo.cor = NoRB.Cor.VERMELHO;
                    rotacaoEsquerda(avo);
                }
            }
        }
        raiz.cor = NoRB.Cor.PRETO;
    }

    @Override
    public void inserirAleatorio(int min, int max) {
        inserir(new Random().nextInt(min, max + 1));
    }

    @Override
    public boolean buscar(int valor) {
        return buscar(raiz, valor);
    }

    private boolean buscar(NoRB no, int valor) {
        if (no == NoRB.NIL) return false;
        if (valor == no.valor) return true;
        return valor < no.valor ? buscar(no.esquerdo, valor) : buscar(no.direito, valor);
    }

    @Override
    public void remover(int valor) {
        NoRB z = raiz;
        while (z != NoRB.NIL) {
            if (valor == z.valor) break;
            z = valor < z.valor ? z.esquerdo : z.direito;
        }
        if (z == NoRB.NIL) return;

        NoRB y = z;
        NoRB.Cor yCorOriginal = y.cor;
        NoRB x;

        if (z.esquerdo == NoRB.NIL) {
            x = z.direito;
            transplantar(z, z.direito);
        } else if (z.direito == NoRB.NIL) {
            x = z.esquerdo;
            transplantar(z, z.esquerdo);
        } else {
            y = minimo(z.direito);
            yCorOriginal = y.cor;
            x = y.direito;
            if (y.pai == z) {
                x.pai = y;
                transplantar(z, y);
                y.esquerdo = z.esquerdo;
                z.esquerdo.pai = y;
            } else {
                transplantar(y, y.direito);
                y.direito = z.direito;
                z.direito.pai = y;
                transplantar(z, y);
                y.esquerdo = z.esquerdo;
                z.esquerdo.pai = y;
            }
            y.cor = z.cor;
        }

        if (yCorOriginal == NoRB.Cor.PRETO) {
            removerFixUp(x);
        }
    }

    private void transplantar(NoRB u, NoRB v) {
        if (u.pai == NoRB.NIL) {
            raiz = v;
        } else if (u == u.pai.esquerdo) {
            u.pai.esquerdo = v;
        } else {
            u.pai.direito = v;
        }
        v.pai = u.pai;
    }

    private NoRB minimo(NoRB no) {
        while (no.esquerdo != NoRB.NIL) {
            no = no.esquerdo;
        }
        return no;
    }

    private void removerFixUp(NoRB x) {
        while (x != raiz && isPreto(x)) {
            if (x == x.pai.esquerdo) {
                NoRB irmao = x.pai.direito;
                if (isVermelho(irmao)) {
                    irmao.cor = NoRB.Cor.PRETO;
                    x.pai.cor = NoRB.Cor.VERMELHO;
                    rotacaoEsquerda(x.pai);
                    irmao = x.pai.direito;
                }
                if (isPreto(irmao.esquerdo) && isPreto(irmao.direito)) {
                    irmao.cor = NoRB.Cor.VERMELHO;
                    x = x.pai;
                } else {
                    if (isPreto(irmao.direito)) {
                        irmao.esquerdo.cor = NoRB.Cor.PRETO;
                        irmao.cor = NoRB.Cor.VERMELHO;
                        rotacaoDireita(irmao);
                        irmao = x.pai.direito;
                    }
                    irmao.cor = x.pai.cor;
                    x.pai.cor = NoRB.Cor.PRETO;
                    irmao.direito.cor = NoRB.Cor.PRETO;
                    rotacaoEsquerda(x.pai);
                    x = raiz;
                }
            } else {
                NoRB irmao = x.pai.esquerdo;
                if (isVermelho(irmao)) {
                    irmao.cor = NoRB.Cor.PRETO;
                    x.pai.cor = NoRB.Cor.VERMELHO;
                    rotacaoDireita(x.pai);
                    irmao = x.pai.esquerdo;
                }
                if (isPreto(irmao.direito) && isPreto(irmao.esquerdo)) {
                    irmao.cor = NoRB.Cor.VERMELHO;
                    x = x.pai;
                } else {
                    if (isPreto(irmao.esquerdo)) {
                        irmao.direito.cor = NoRB.Cor.PRETO;
                        irmao.cor = NoRB.Cor.VERMELHO;
                        rotacaoEsquerda(irmao);
                        irmao = x.pai.esquerdo;
                    }
                    irmao.cor = x.pai.cor;
                    x.pai.cor = NoRB.Cor.PRETO;
                    irmao.esquerdo.cor = NoRB.Cor.PRETO;
                    rotacaoDireita(x.pai);
                    x = raiz;
                }
            }
        }
        x.cor = NoRB.Cor.PRETO;
    }

    @Override
    public void imprime(ImprimirArvore estrategia) {
        if (raiz != NoRB.NIL) {
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

    private void emOrdem(NoRB no) {
        if (no == NoRB.NIL) return;
        emOrdem(no.esquerdo);
        System.out.println(no.valor);
        emOrdem(no.direito);
    }

    private void preOrdem(NoRB no) {
        if (no == NoRB.NIL) return;
        System.out.println(no.valor);
        preOrdem(no.esquerdo);
        preOrdem(no.direito);
    }

    private void posOrdem(NoRB no) {
        if (no == NoRB.NIL) return;
        posOrdem(no.esquerdo);
        posOrdem(no.direito);
        System.out.println(no.valor);
    }

    private void emNivel(NoRB raiz) {
        Queue<NoRB> fila = new LinkedList<>();
        fila.add(raiz);

        while (!fila.isEmpty()) {
            int tamanhoFila = fila.size();
            for (int i = 0; i < tamanhoFila; i++) {
                NoRB atual = fila.poll();
                if (atual != NoRB.NIL) {
                    System.out.println(atual.valor);
                    if (atual.esquerdo != NoRB.NIL) fila.add(atual.esquerdo);
                    if (atual.direito != NoRB.NIL) fila.add(atual.direito);
                }
            }
            System.out.println();
        }
    }

    public void desenhar() {
        if (raiz == NoRB.NIL) return;
        System.out.println(raiz.valor + "(" + corStr(raiz.cor) + ")");
        desenhar(raiz.esquerdo, "", raiz.direito == NoRB.NIL);
        desenhar(raiz.direito, "", true);
    }

    private void desenhar(NoRB no, String prefixo, boolean ehUltimo) {
        if (no == NoRB.NIL) return;
        System.out.println(prefixo + (ehUltimo ? "└── " : "├── ") + no.valor + "(" + corStr(no.cor) + ")");
        String novoPrefixo = prefixo + (ehUltimo ? "    " : "│   ");
        desenhar(no.esquerdo, novoPrefixo, no.direito == NoRB.NIL);
        desenhar(no.direito, novoPrefixo, true);
    }

    private String corStr(NoRB.Cor cor) {
        return cor == NoRB.Cor.VERMELHO ? "V" : "P";
    }

    private int contagem(NoRB no) {
        return no == NoRB.NIL ? 0 : 1 + contagem(no.esquerdo) + contagem(no.direito);
    }
}
