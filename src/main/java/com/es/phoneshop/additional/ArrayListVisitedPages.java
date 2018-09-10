package com.es.phoneshop.additional;

import javax.servlet.http.HttpServletRequest;
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
    public void saveAddress(HttpServletRequest request) {
        if(request.getSession().isNew()) {
            clearAll();
        }
        addresses.add(request.getRequestURI());
    }

    @Override
    public boolean isLastAddressNew() {
        if(addresses.size() < 2) {
            return true;
        }
        return (!addresses.get(addresses.size() - 1).equals(addresses.get(addresses.size() - 2)));
    }

    private void clearAll() {
        addresses.clear();
    }
}
