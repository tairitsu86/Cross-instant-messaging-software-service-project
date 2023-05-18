package com.cimss.project.controller.exception;

import com.cimss.project.database.entity.token.GroupRole;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NoPermissionException extends RuntimeException {
    private GroupRole requestRole;
    private GroupRole userRole;
    public String getErrorMessage(){
        return String.format("Request group role:%s,your role:%s",requestRole.name(),userRole.name());
    }
}
