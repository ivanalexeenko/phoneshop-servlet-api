package com.es.phoneshop.additional;

import java.util.ArrayList;
import java.util.List;

public class ArrayListVisitedPages implements VisitedPagesInterface {
    private List<String> addresses;
    private static class ArrayListVisitedPagesHelper {
        private static final ArrayListVisitedPages INSTANCE = new ArrayListVisitedPages();
    }
    public static ArrayListVisitedPages getInstance() {
        return ArrayListVisitedPagesHelper.INSTANCE;
    }
    private ArrayListVisitedPages() {
        addresses = new ArrayList<>();
    }

    @Override
    public void saveAddress(String address) {
        addresses.add(address);
    }

    @Override
    public boolean isLastAddressNew() {
        if(addresses.size() < 2) {
            return true;
        }
        return (!addresses.get(addresses.size() - 1).equals(addresses.get(addresses.size() - 2)));
    }
}
