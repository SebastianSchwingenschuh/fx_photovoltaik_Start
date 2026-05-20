package at.htl.photovoltaic.service;

import at.htl.photovoltaic.model.Offer;
import at.htl.photovoltaic.model.SortCriterion;
import at.htl.photovoltaic.repository.OfferRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OfferService {
    public List<Offer> getTop3Offers(SortCriterion criterion) {
        List<Offer> allOffers = OfferRepository.getInstance().getAllOffers();

        if(allOffers.isEmpty()){
            return List.of();
        }

        // Stream-Pipeline: Sortieren -> Limit 3 -> Sammeln
        return allOffers.stream()
                .sorted(getComparatorForCriterion(criterion))
                .limit(3)
                .collect(Collectors.toList());
    }

    private Comparator<Offer> getComparatorForCriterion(SortCriterion criterion) {
        switch (criterion) {
            case PRICE_PER_KWP:
                return Comparator.comparingDouble(o -> {
                    double totalPower = o.getNumberOfPanels() * o.getPowerPerPanel();
                    if(totalPower == 0){
                        return Double.MAX_VALUE;
                    }
                    return o.getTotalPrice() / totalPower;
                });

            case TOTAL_PRICE:
                return Comparator.comparingDouble(Offer::getTotalPrice);

            default:
                throw new RuntimeException("Unbekanntes Sortkriterium: " + criterion);
        }
    }
}
