package com.java5.assignment.controllers.admin;

import com.java5.assignment.dto.RevenueStatisticsDTO;
import com.java5.assignment.jpa.OrderDetailRepository;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class StatisticalRevenueController {

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_STATISTICAL_REVENUE);
    }


    @ModelAttribute("revenueStatistics")
    public List<RevenueStatisticsDTO> dailyOrderStatistics() {
        List<Object[]> results = orderDetailRepository.findDailyOrderStatistics();
        List<RevenueStatisticsDTO> dtoList = new ArrayList<>();

        for (Object[] result : results) {
            RevenueStatisticsDTO dto = new RevenueStatisticsDTO();
            dto.setId(((Number) result[0]).longValue());
            dto.setTime((Date) result[1]);
            dto.setNumberOfInvoices(((Number) result[2]).longValue());
            dto.setNumberOfProductsSold(((Number) result[3]).longValue());
            dto.setTotalRevenue((BigDecimal) result[4]);
            dtoList.add(dto);
        }

        return dtoList;
    }


    @GetMapping("/statistical-revenue")
    public String get() {
        return "admin/layout";
    }

}
