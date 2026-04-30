package org.example.jdbc.orm.onetoone;

import java.util.Objects;

public abstract class Entity {
    private Object id;
    public Entity(Object id){
        this.id = id;
    }
    public Entity(){}
    public Object getId() {
        return id;
    }
    public void setId(Object id) {
        this.id = id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return Objects.equals(id, entity.id);
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
