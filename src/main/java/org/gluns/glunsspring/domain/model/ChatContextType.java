package org.gluns.glunsspring.domain.model;

/**
 * ChatContext
 * Used to define the ChatContext object.
 */
public enum ChatContextType {
    CAPITAL_MANAGEMENT("Capital Management"),
    INVESTMENT("Investment"),
    SAVE_MONEY("Save Money"),
    BILLS_BALANCE("Bills Balance"),
    SUMMARY_INVOICES("Summary Invoices");
    
    final String name;
    
    ChatContextType(String name) {
        this.name = name;
    }
}
