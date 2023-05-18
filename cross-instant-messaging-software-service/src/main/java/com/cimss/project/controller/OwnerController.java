package com.cimss.project.controller;

import com.cimss.project.apibody.ManageBean;
import com.cimss.project.service.APIHandlerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Group owner Services API")
@RestController
public class OwnerController {
	@Autowired
	private APIHandlerService apiHandlerService;

	//Patch method
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Grant permission", description = "Grant manager permission to any member.")
	@PatchMapping("/permission/grant")
	public void grantPermission(@RequestHeader(name = "Authorization") String accessToken,
								@Valid @RequestBody ManageBean.GrantPermissionBean grantPermissionBean) {
		apiHandlerService.grantPermission(accessToken,grantPermissionBean);
	}
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Revoke permission", description = "Revoke manager permission to any member.")
	@PatchMapping("/permission/revoke")
	public void revokePermission(@RequestHeader(name = "Authorization") String accessToken,
								 @Valid @RequestBody ManageBean.GrantPermissionBean grantPermissionBean) {
		apiHandlerService.revokePermission(accessToken,grantPermissionBean);
	}

	//delete method
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Delete group", description = "Delete the group by group id.")
	@DeleteMapping("/groups/{groupId}/delete")
	public void deleteGroup(@RequestHeader(name = "Authorization") String accessToken,
							@Parameter(description = "The id of the group, composed of six alphanumerics.", example = "AbCd12")
							@PathVariable String groupId) {
		apiHandlerService.deleteGroup(accessToken,groupId);
	}
}