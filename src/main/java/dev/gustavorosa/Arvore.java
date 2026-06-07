package dev.gustavorosa;

public interface Arvore {
    void inserir(int valor);
    void inserirAleatorio(int min, int max);
    boolean buscar(int valor);
    void remover(int valor);
}
