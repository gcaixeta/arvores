package dev.gustavorosa;

public class NoAVL {

    int valor;
    NoAVL esquerdo;
    NoAVL direito;
    int altura;

    NoAVL(int valor) {
        this.valor = valor;
        this.altura = 1;
    }
}
