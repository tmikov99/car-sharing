package com.coursework.car.permission;

import com.coursework.car.model.Offer;
import com.coursework.car.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferPermissionService {

    @Autowired
    OfferPermissionStore offerPermissionStore;

    public void addToStore(User user, Offer offer, String level) {
        OfferPermission offerPermission = new OfferPermission();
        offerPermission.setOffer(offer);
        offerPermission.setUser(user);
        offerPermission.setLevel(level);
        offerPermissionStore.save(offerPermission);
    }

    public void removeFromStoreByOffer(Offer offer) {
        offerPermissionStore.deleteByOffer(offer);
    }
}
