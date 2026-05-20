package at.htl.photovoltaic.service;

import at.htl.photovoltaic.model.Offer;
import at.htl.photovoltaic.model.SortCriteria;
import at.htl.photovoltaic.repository.OfferRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OfferService {
    public List<Offer> getTop3Offers(SortCriteria selectedCriteria) {
        List<Offer> allOffers = OfferRepository.getInstance().getAllOffers();
        if (allOffers.isEmpty()) {
            return List.of();
        }

        return allOffers.stream()
                .sorted(getComparatorForCriteria(selectedCriteria))
                .limit(3)
                .collect(Collectors.toList());
    }

    private Comparator<Offer> getComparatorForCriteria(SortCriteria selectedCriteria) {
        switch (selectedCriteria) {
            case PRICE_PER_KWP:
                return Comparator.comparingDouble(o -> {
                    double totalPower = o.getNumberOfPanels() * o.getPowerPerPanel();
                    if (totalPower == 0) {
                        return Double.MAX_VALUE;
                    }
                    return o.getTotalPrice() / totalPower;
                });

            case TOTAL_PRICE:
                return Comparator.comparingDouble(Offer::getTotalPrice);

            default:
                throw new RuntimeException("Unbekannted Sortkriterium: " + selectedCriteria);
        }
    }
}
