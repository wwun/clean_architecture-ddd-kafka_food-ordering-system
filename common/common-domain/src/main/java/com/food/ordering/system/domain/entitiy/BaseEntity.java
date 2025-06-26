package com.food.ordering.system.domain.entitiy;

public abstract class BaseEntity<ID> {
    
    private ID id;  //recordar qe cada entidad debe tener un id
    // Este id puede ser de cualquier tipo, por ejemplo Long, UUID, etc.
    // Esto permite que la clase BaseEntity sea genérica y pueda ser utilizada con diferentes tipos de ID.
    // El uso de generics permite que la clase BaseEntity sea flexible y pueda ser utilizada con diferentes tipos de ID.

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity<?> that = (BaseEntity<?>) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
