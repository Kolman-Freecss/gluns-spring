package org.gluns.glunsspring.infrastructure.adapters.in.gluns.python.models;

import org.gluns.glunsspring.domain.model.ChatContextType;

/**
 * TipsAnswerPythonRequest
 */
public record TipsAnswerPythonRequest(
        long chatHistoryId,
        String userId,
        ChatContextType context,
        String input
) {
}
