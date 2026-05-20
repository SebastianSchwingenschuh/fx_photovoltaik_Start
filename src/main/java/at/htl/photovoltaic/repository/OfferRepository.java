package at.htl.photovoltaic.repository;

import at.htl.photovoltaic.model.Offer;

import java.util.ArrayList;
import java.util.List;

public class OfferRepository {
    private static OfferRepository instance;
    List<Offer> offers;
    private int nextId = 1;

    public OfferRepository() {
        offers = new ArrayList<>();
    }

    public OfferRepository getInstance() {
        if(instance == null){
            instance = new OfferRepository();
        }
        return instance;
    }

    public void addOffer(Offer offer){
        offer.setId(nextId++);
        offers.add(offer);
    }
    
    public void updateOffer(Offer offerToUpd)  {
//        offers.forEach(o -> {
//            if(o.getId() == offerToUpd.getId()) {
//                o = offerToUpd;
//            }
//        });

        for (int i = 0; i < offers.size(); i++) {
            if(offers.get(i).getId() == offerToUpd.getId()){
                offers.set(i, offerToUpd);
                break;
            }
        }
    }

    public void deleteOffer (int id){
        offers.removeIf(offer -> offer.getId() == id);
    }

    public double getMaximumPrice (){
        return offers.stream()
                .mapToDouble(Offer::getTotalPrice)
                .max()
                .orElse(0.0);
    }

    public double getMinimumPrice (){
        return offers.stream()
                .mapToDouble(Offer::getTotalPrice)
                .min()
                .orElse(0.0);
    }
}
