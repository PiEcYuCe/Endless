package com.java5.assignment.services;

import com.java5.assignment.entities.ProductVersion;
import com.java5.assignment.entities.PurchaseOrder;
import com.java5.assignment.entities.PurchaseOrderDetail;
import com.java5.assignment.jpa.ProductVersionRepository;
import com.java5.assignment.jpa.PurchaseOrderDetailRepository;
import com.java5.assignment.jpa.PurchaseOrderRepository;
import com.java5.assignment.model.purchase.PurchaseData;
import com.java5.assignment.model.purchase.PurchaseDetailData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseService {
    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    PurchaseOrderDetailRepository purchaseOrderDetailRepository;

    @Autowired
    ProductVersionRepository productVersionRepository;

    @Transactional
    public List<PurchaseOrderDetail> addPurchaseOrderAndDetails(PurchaseData purchaseData, List<PurchaseDetailData> purchaseDetailsData) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setPurchaseDate(purchaseData.getPurchaseDate());
        purchaseOrder.setPurchaseOrderStatus(purchaseData.getPurchaseStatus());
        purchaseOrder.setTotalMoney(purchaseData.getPurchaseTotal());
        purchaseOrderRepository.save(purchaseOrder);

        List<PurchaseOrderDetail> purchaseOrderDetails = new ArrayList<>();
        for(PurchaseDetailData purchaseDetailData : purchaseDetailsData) {
            ProductVersion productVersion = productVersionRepository.findById(purchaseDetailData.getProductVersionID());
            PurchaseOrderDetail purchaseOrderDetail = new PurchaseOrderDetail();
            purchaseOrderDetail.setPurchaseOrderID(purchaseOrder);
            purchaseOrderDetail.setProductVersionID(productVersion);
            purchaseOrderDetail.setPrice(purchaseDetailData.getPrice());
            purchaseOrderDetail.setQuantity(purchaseDetailData.getQuantity());
            productVersion.setQuantity(productVersion.getQuantity()+purchaseDetailData.getQuantity());
            productVersionRepository.save(productVersion);
        }

        return  purchaseOrderDetailRepository.saveAll(purchaseOrderDetails);

    }
}
