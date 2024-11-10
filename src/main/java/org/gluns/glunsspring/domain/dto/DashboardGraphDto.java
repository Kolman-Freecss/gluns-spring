package org.gluns.glunsspring.domain.dto;

/**
 * DashboardGraphDto
 */
public record DashboardGraphDto(
    String customer_id,
    String month,
    String monthly_expense,
    String monthly_income
) {
    
}
