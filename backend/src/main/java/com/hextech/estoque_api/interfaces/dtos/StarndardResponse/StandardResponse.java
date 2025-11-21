package com.hextech.estoque_api.interfaces.dtos.StarndardResponse;

import com.hextech.estoque_api.interfaces.dtos.errors.CustomError;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
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
}
