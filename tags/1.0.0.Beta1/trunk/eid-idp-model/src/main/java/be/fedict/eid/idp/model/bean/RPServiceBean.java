/*
 * eID Identity Provider Project.
 * Copyright (C) 2010 FedICT.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License version
 * 3.0 as published by the Free Software Foundation.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, see 
 * http://www.gnu.org/licenses/.
 */

package be.fedict.eid.idp.model.bean;

import be.fedict.eid.idp.entity.RPEntity;
import be.fedict.eid.idp.model.RPService;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class RPServiceBean implements RPService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RPEntity> listRPs() {

        return RPEntity.listRPs(this.entityManager);
    }

    @Override
    public void remove(RPEntity rp) {

        RPEntity attachedRp = this.entityManager.find(RPEntity.class, rp.getId());
        this.entityManager.remove(attachedRp);
    }

    @Override
    public RPEntity save(RPEntity rp) {

        RPEntity attachedRp = this.entityManager.find(RPEntity.class, rp.getId());
        if (null != attachedRp) {
            // save
            attachedRp.setName(rp.getName());
            if (null != rp.getDomain() && rp.getDomain().trim().isEmpty()) {
                attachedRp.setDomain(null);
            } else {
                attachedRp.setDomain(rp.getDomain());
            }
            attachedRp.setEncodedCertificate(rp.getEncodedCertificate());
            return attachedRp;
        } else {
            // add
            this.entityManager.persist(rp);
            return rp;
        }
    }
}