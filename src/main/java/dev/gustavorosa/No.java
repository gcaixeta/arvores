package dev.gustavorosa;

import java.util.LinkedList;
import java.util.Queue;

public class No {
    private No noEsquerdo;
    private No noDireito;
    private int valor;

    public No(int valor) {
        this.valor = valor;
    }

    public void inserir(int valor) {
        if(valor < this.valor) {
            //inserir na esquerda
            if(noEsquerdo == null) {
                noEsquerdo = new No(valor);
                return;
            }
            noEsquerdo.inserir(valor);
        } else {
            //inserir na direita
            if(noDireito == null) {
                noDireito = new No(valor);
                return;
            }
            noDireito.inserir(valor);
        }
    }

    public void emOrdem() {
        imprimeEsquerdo();
        imprimeASiMesmo();
        imprimeDireito();
    }

    public void preOrdem() {
        imprimeASiMesmo();
        imprimeEsquerdo();
        imprimeDireito();
    }

    public void posOrdem() {
        imprimeEsquerdo();
        imprimeDireito();
        imprimeASiMesmo();
    }

    public void emNivel() {
        Queue<No> fila = new LinkedList<>();

        fila.add(this);

        while(!fila.isEmpty()) {
            int tamanhoFila = fila.size();

            for(int i = 0;i < tamanhoFila; i++) {
                No atual = fila.poll();

                if(atual != null) atual.imprimeASiMesmo();

                if(atual.noEsquerdo != null) {
                    fila.add(atual.noEsquerdo);
                }
                if(atual.noDireito != null) {
                    fila.add(atual.noDireito);
                }
            }
            System.out.println();
        }
    }

    public void imprimeDireito() {
        if (noDireito != null) noDireito.posOrdem();
    }

    public void imprimeEsquerdo() {
        if (noEsquerdo != null) noEsquerdo.posOrdem();
    }

    public void imprimeASiMesmo() {
        System.out.println(this.valor);
    }

    public No getNoEsquerdo() {
        return noEsquerdo;
    }

    public No getNoDireito() {
        return noDireito;
    }
}
