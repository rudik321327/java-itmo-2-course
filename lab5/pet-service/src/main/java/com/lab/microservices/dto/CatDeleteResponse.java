package com.lab.microservices.pet.dto;

import java.io.Serializable;

/**
 * DTO: ответ на удаление кота: success = true/false.
 */
public class CatDeleteResponse implements Serializable {
    private boolean success;

    public CatDeleteResponse() {
    }

    public CatDeleteResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
