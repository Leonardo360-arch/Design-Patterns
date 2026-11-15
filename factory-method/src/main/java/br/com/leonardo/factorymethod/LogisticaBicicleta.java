package br.com.leonardo.factorymethod;

public class LogisticaBicicleta extends LogisticaEntrega {
    @Override
    protected Entregador criarEntregador() {
        return new EntregadorBicicleta();
    }
}
