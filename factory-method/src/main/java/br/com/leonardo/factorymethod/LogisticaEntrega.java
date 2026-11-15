package br.com.leonardo.factorymethod;

public abstract class LogisticaEntrega {
    // Factory Method: cada subclasse escolhe o produto concreto.
    protected abstract Entregador criarEntregador();

    public final String processarEntrega(String destinatario) {
        if (destinatario == null || destinatario.isBlank()) {
            throw new IllegalArgumentException("Informe o nome do destinatário.");
        }

        // O fluxo comum conhece somente a interface Entregador.
        Entregador entregador = criarEntregador();
        return entregador.entregar(destinatario.strip());
    }
}
