package com.serviceexceptions.modal;

import org.springframework.http.HttpStatus;

import java.util.Calendar;

public final class EntidadeNaoProcessavelException extends CustomException {
    public EntidadeNaoProcessavelException() {
        this.timestamp = Calendar.getInstance().getTime();
        this.code = HttpStatus.NOT_FOUND.value();
        this.message = "A solicitação não pôde ser processada devido a dados inválidos ou à violação das regras de negócio.";
    }
}
