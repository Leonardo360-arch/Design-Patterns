package br.com.leonardo.factorymethod;

public class LogisticaMoto extends LogisticaEntrega {
    @Override
    protected Entregador criarEntregador() {
        return new EntregadorMoto();
    }
}
