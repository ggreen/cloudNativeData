package io.pivotal.market.api.dao;


import nyla.solutions.core.util.Organizer;
import nyla.solutions.core.util.Text;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.*;

@Entity
@Table(name="ProductAssociation", schema = "pivotalmarkets")
public class ProductAssociationEntity
{
    @Id
    private String id;

    private String associations;

    public ProductAssociationEntity()
    {
    }

    public ProductAssociationEntity(String id, Set<String> associations)
    {
        this.id = id;
        setAssociations(associations);
    }

    public ProductAssociationEntity(String id, String associates)
    {
        this.id = id;
        setAssociations(associates != null ? Arrays.asList(associates.split("\\|")) : null);
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String[] getAssociations()
    {
        return this.associations != null ? associations.split("\\|") : null;
    }

    public void setAssociations(Collection<String> associations)
    {
        this.associations = Text.toText((Collection)associations,"|");
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductAssociationEntity entity = (ProductAssociationEntity) o;
        return Objects.equals(id, entity.id) &&
                Objects.equals(associations, entity.associations);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, associations);
    }

    public String retrieveAssociationsText()
    {
        return associations;
    }
}
