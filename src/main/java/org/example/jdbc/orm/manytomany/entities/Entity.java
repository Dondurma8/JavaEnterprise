package org.example.jdbc.orm.manytomany.entities;

import java.util.Objects;

public abstract class Entity<K>{
    private K id;
    public Entity(K id){
        this.id = id;
    }
    public Entity(){}
    public K getId() {
        return id;
    }
    public void setId(K id) {
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
