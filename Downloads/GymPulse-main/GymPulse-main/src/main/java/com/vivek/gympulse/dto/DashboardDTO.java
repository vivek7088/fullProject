package com.vivek.gympulse.dto;

public class DashboardDTO {

    private long totalMembers;
    private long activeMembers;
    private long expiredMembers;
    private long pendingMembers;

    private Double totalIncome;
    private Double totalPendingAmount;
    private Double todayCollection;

    public DashboardDTO(long totalMembers,
                        long activeMembers,
                        long expiredMembers,
                        long pendingMembers,
                        Double totalIncome,
                        Double totalPendingAmount,
                        Double todayCollection) {

        this.totalMembers = totalMembers;
        this.activeMembers = activeMembers;
        this.expiredMembers = expiredMembers;
        this.pendingMembers = pendingMembers;
        this.totalIncome = totalIncome;
        this.totalPendingAmount = totalPendingAmount;
        this.todayCollection = todayCollection;
    }

    public long getTotalMembers() {
        return totalMembers;
    }

    public long getActiveMembers() {
        return activeMembers;
    }

    public long getExpiredMembers() {
        return expiredMembers;
    }

    public long getPendingMembers() {
        return pendingMembers;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public Double getTotalPendingAmount() {
        return totalPendingAmount;
    }

    public Double getTodayCollection() {
        return todayCollection;
    }
}