package com.contentieux.gestion_contentieux.service;

import com.contentieux.gestion_contentieux.model.Encaissement;
import com.contentieux.gestion_contentieux.model.RepartitionResultats;

public class RepartitionService {

    public RepartitionResultats calculerParts(Encaissement encaissement) {
        RepartitionResultats res = new RepartitionResultats();

        double produitDisponible = encaissement.getMontantEncaisse();
        double partIndicateur = produitDisponible * 0.10;
        double produitNet = produitDisponible - partIndicateur;
        double partFlcf = produitNet * 0.10;
        double partTresor = produitNet * 0.15;
        double produitNetDroits = produitNet - (partFlcf + partTresor);
        double partChefs = produitNetDroits * 0.15;
        double partSaisissants = produitNetDroits * 0.35;
        double partMutuelle = produitNetDroits * 0.05;
        double partMasseCommune = produitNetDroits * 0.30;
        double partInteressement = produitNetDroits * 0.15;

        res.setEncaissementId(encaissement.getId());
        res.setProduitDisponible(produitDisponible);
        res.setPartIndicateur(partIndicateur);
        res.setProduitNet(produitNet);
        res.setPartFlcf(partFlcf);
        res.setPartTresor(partTresor);
        res.setProduitNetDroits(produitNetDroits);
        res.setPartChefs(partChefs);
        res.setPartSaisissants(partSaisissants);
        res.setPartMutuelle(partMutuelle);
        res.setPartMasseCommune(partMasseCommune);
        res.setPartInteressement(partInteressement);

        return res;
    }
}