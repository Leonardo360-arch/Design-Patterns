package br.com.leonardo.factorymethod;

public class EntregadorBicicleta implements Entregador {
    @Override
    public String entregar(String destinatario) {
        return "Entrega para " + destinatario + " realizada de bicicleta.";
    }
}
