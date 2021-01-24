package io.pivotal.services.pivotMart.streams.entity;

import nyla.solutions.core.util.Text;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Set;

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
        return associations != null ? associations.split("|") : null;
    }

    public void setAssociations(Collection<String> associations)
    {
        this.associations = Text.toText(associations,"|");
    }
}
