package br.com.ithappens.model.enums;

public enum StatusPedido {
    ATIVO(1),
    PROCESSADO(2),
    CANCELADO(3);

    private int id;

    StatusPedido(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static StatusPedido toEnum(Integer cod){
        if(cod == null){
            return  null;
        }

        for(StatusPedido ped : StatusPedido.values()){
            if(cod.equals(ped.getId()))
                return ped;
        }

        throw new IllegalArgumentException("Id StatusPedido inválido!");
    }
}
