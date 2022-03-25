/*
 * (C) Copyright Resse Christophe 2021 - All Rights Reserved
 * -----------------------------------------------------------------------------------------------
 * All information contained herein is, and remains the property of
 * Resse Christophe. and its suppliers, if any. The intellectual and technical
 * concepts contained herein are proprietary to Resse C. and its
 * suppliers and may be covered by U.S. and Foreign Patents, patents
 * in process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained from
 * Resse Christophe (christophe.resse@gmail.com).
 * -----------------------------------------------------------------------------------------------
 */
package com.hemajoo.commerce.cherry.backend.persistence.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hemajoo.commerce.cherry.backend.commons.entity.EntityIdentity;
import com.hemajoo.commerce.cherry.backend.commons.type.EntityType;
import com.hemajoo.commerce.cherry.backend.persistence.document.entity.DocumentServer;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Represents a server base entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ENTITY")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ServerEntity extends AbstractServerStatusEntity implements IServerEntity
{
    /**
     * Property used to set a search criteria for the <b>identifier</b> field.
     */
    public static final String FIELD_ID = "id";

    /**
     * Property used to set a search criteria for the <b>entity type</b> field.
     */
    public static final String FIELD_ENTITY_TYPE = "entityType";

    /**
     * Property used to set a search criteria for the <b>name</b> field.
     */
    public static final String FIELD_NAME = "name";

    /**
     * Property used to set a search criteria for the <b>description</b> field.
     */
    public static final String FIELD_DESCRIPTION = "description";

    /**
     * Property used to set a search criteria for the <b>reference</b> field.
     */
    public static final String FIELD_REFERENCE = "reference";

    /**
     * Property used to set a search criteria for the <b>parent</b> field.
     */
    public static final String FIELD_PARENT = "parent";

    /**
     * Property used to set a search criteria for the <b>parent type</b> field.
     */
    public static final String FIELD_PARENT_TYPE = "parentType";

//    /**
//     * Entity identifier.
//     */
//    @Getter
//    @Setter
//    @Id
//    @Type(type = "uuid-char") // Allow displaying in the DB the UUID as a string instead of a binary field!
//    @GeneratedValue
//    private UUID id;

    /**
     * Entity identifier.
     */
    @Getter
    @Setter
    @Id
    @Type(type = "uuid-char") // Allow displaying in the DB the UUID as a string instead of a binary field!
    @GenericGenerator(name = "cherry-uuid-gen", strategy = "com.hemajoo.commerce.cherry.backend.persistence.base.entity.UuidGenerator")
    @GeneratedValue(generator = "cherry-uuid-gen")
    private UUID id;

    /**
     * Entity type.
     */
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "ENTITY_TYPE", length = 50)
    private EntityType entityType;

    /**
     * Entity name.
     */
    @Getter
    @Setter
    @Column(name = "NAME")
    private String name;

    /**
     * Entity description.
     */
    @Getter
    @Setter
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * Entity internal reference.
     */
    @Getter
    @Setter
    @Column(name = "REFERENCE", length = 100)
    private String reference;

    /**
     * Documents associated with this entity.
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL/*, orphanRemoval = true*/)
    private List<DocumentServer> documents = null;

    /**
     * The entity (parent) this entity (child) belongs to.
     */
    @Getter
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties
    @ManyToOne(targetEntity = ServerEntity.class, fetch = FetchType.EAGER)
    private ServerEntity parent;

    /**
     * Parent type.
     */
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "PARENT_TYPE", length = 50)
    private EntityType parentType;

    /**
     * Creates a new base entity.
     * @param type Entity type.
     */
    protected ServerEntity(final EntityType type)
    {
        this.entityType = type;
    }

    /**
     * Adds a document to this entityDocumentEntity.
     * @param document Document.
     */
    public final void addDocument(final @NonNull DocumentServer document)
    {
        if (documents == null)
        {
            documents = new ArrayList<>();
        }

        documents.add(document);
        document.setOwner(this);
    }

    /**
     * Returns the documents associated to this entity.
     * @return List of documents.
     */
    public List<DocumentServer> getDocuments()
    {
        if (entityType == EntityType.MEDIA)
        {
            return new ArrayList<>();
        }

        return documents != null ? Collections.unmodifiableList(documents) : null;
    }

    @Override
    public final EntityIdentity getIdentity()
    {
        return new EntityIdentity(id, entityType);
    }

    /**
     * Sets the parent entity this entity belongs to.
     * @param parent Parent entity.
     * @throws RuntimeException Thrown to indicate an error occurred while trying to set the parent entity.
     */
    public void setParent(final ServerEntity parent) throws RuntimeException
    {
        if (parent != null && parent.getId() == this.getId())
        {
            throw new RuntimeException("Cannot set itself as parent!");
        }

        this.parent = parent;
        this.parentType = parent != null ? parent.getEntityType() : null;
    }
}
