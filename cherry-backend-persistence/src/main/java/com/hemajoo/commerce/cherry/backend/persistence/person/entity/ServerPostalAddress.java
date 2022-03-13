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
package com.hemajoo.commerce.cherry.backend.persistence.person.entity;

import com.hemajoo.commerce.cherry.backend.persistence.base.entity.ServerEntity;
import com.hemajoo.commerce.cherry.backend.shared.person.address.PostalAddress;

/**
 * Behavior of a server postal address entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface ServerPostalAddress extends PostalAddress, ServerEntity
{
//    /**
//     * Returns the owner of this entity.
//     * @param <T> Type of the owner entity.
//     * @return Owner entity.
//     */
//    <T extends ServerEntity & Person> T getPerson();
//
//    /**
//     * Sets the owner of this entity.
//     * @param owner Owner entity.
//     * @param <T> Type of the owner entity.
//     */
//    <T extends ServerEntity & Person> void setPerson(final T owner);

    ServerPersonEntity getPerson();

    void setPerson(final ServerPersonEntity owner);
}
