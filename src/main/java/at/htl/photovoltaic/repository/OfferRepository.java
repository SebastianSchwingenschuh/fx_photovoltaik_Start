package at.htl.photovoltaic.repository;

import at.htl.photovoltaic.model.Offer;

import java.util.ArrayList;
import java.util.List;

public class OfferRepository {
    private static OfferRepository instance;
    private final List<Offer> offers = new ArrayList<>();
    private int nextId = 1; //da in-Memory, keine DB

    public OfferRepository() {
    }

    public static synchronized OfferRepository getInstance() {
        if (instance == null) {
            instance = new OfferRepository();
        }
        return instance;
    }

    public void addOffer(Offer offer) {
        offer.setId(nextId++);
        offers.add(offer);
    }

    public void updateOffer(Offer updateOffer) {
        for (int i = 0; i < offers.size(); i++) {
            if (offers.get(i).getId() == updateOffer.getId()) {
                offers.set(i, updateOffer);
                break;
            }
        }
    }

    public void deleteOffer(int id) {
        offers.removeIf(offer -> offer.getId() == id);
    }

    public double getMinimumPrice() {
        return offers.stream()
                .mapToDouble(Offer::getTotalPrice)
                .min()
                .orElse(0.0);
    }

    public double getMaximumPrice() {
        return offers.stream()
                .mapToDouble(Offer::getTotalPrice)
                .max()
                .orElse(0.0);
    }

    public List<Offer> getAllOffers() {
        return new ArrayList<>(offers);
    }

    public Offer getOfferById(int id) {
        return offers.stream()
                .filter(offer -> offer.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
