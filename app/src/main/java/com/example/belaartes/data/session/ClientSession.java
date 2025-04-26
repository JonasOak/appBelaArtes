package com.example.belaartes.data.session;

import com.example.belaartes.data.model.entities.Cliente;

public class ClientSession {
    public static Cliente clientSession;

    public ClientSession() {
        clientSession.setIdCliente(0);
    }

    public static Cliente getClientSession() {
        return clientSession;
    }

    public static void setClientSession(Cliente clientSession) {
        ClientSession.clientSession = clientSession;
    }
}
