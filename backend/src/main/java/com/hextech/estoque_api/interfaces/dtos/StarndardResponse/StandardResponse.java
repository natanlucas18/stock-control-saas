package com.hextech.estoque_api.interfaces.dtos.StarndardResponse;

import com.hextech.estoque_api.interfaces.dtos.errors.CustomError;

import java.util.List;

public class StandardResponse<T> {

    private boolean success;
    private T data;
    private List<CustomError> errors;

    public StandardResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public StandardResponse(boolean success, List<CustomError> errors) {
        this.success = success;
        this.errors = errors;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<CustomError> getErrors() {
        return errors;
    }

    public void setErrors(List<CustomError> errors) {
        this.errors = errors;
    }
}
