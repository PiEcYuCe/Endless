package com.java5.assignment.model.purchase;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PurchaseRequest {
    PurchaseData purchaseData;
    List<PurchaseDetailData> purchaseDetailsData;
}
