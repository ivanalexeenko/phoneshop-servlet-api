package com.es.phoneshop.additional;

import javax.servlet.http.HttpServletRequest;

public interface VisitedPagesInterface {

    public void saveAddress(HttpServletRequest request);

    public boolean isLastAddressNew();

}
