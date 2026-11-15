package br.com.leonardo.factorymethod;

// Produto: contrato comum dos objetos criados pelo Factory Method.
public interface Entregador {
    String entregar(String destinatario);
}
