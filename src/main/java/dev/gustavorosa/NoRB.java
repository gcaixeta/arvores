package dev.gustavorosa;

public class NoRB {

    enum Cor {
        VERMELHO,
        PRETO
    }

    static final NoRB NIL = new NoRB();

    static {
        NIL.cor = Cor.PRETO;
        NIL.esquerdo = NIL;
        NIL.direito = NIL;
        NIL.pai = NIL;
    }

    int valor;
    NoRB esquerdo;
    NoRB direito;
    NoRB pai;
    Cor cor;

    NoRB(int valor) {
        this.valor = valor;
        this.cor = Cor.VERMELHO;
        this.esquerdo = NIL;
        this.direito = NIL;
        this.pai = NIL;
    }

    private NoRB() {
    }
}
