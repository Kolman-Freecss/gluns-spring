package org.gluns.glunsspring.infrastructure.adapters.in.gluns.python.models;

import org.gluns.glunsspring.domain.model.ChatContextType;

/**
 * ChatAnswerPythonRequest
 * Model used to request the Python service.
 * @see org.gluns.glunsspring.infrastructure.adapters.in.gluns.python.ChatAnswerHandlerPythonImpl
 * @version 1.0
 */
public record ChatAnswerPythonRequest(
        long chatHistoryId,
        String userId,
        ChatContextType context,
        String input
) {
}
