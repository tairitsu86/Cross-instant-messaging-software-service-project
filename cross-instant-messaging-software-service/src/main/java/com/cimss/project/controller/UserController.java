package com.cimss.project.controller;

import com.cimss.project.apibody.ManageBean;
import com.cimss.project.apibody.MessageBean;
import com.cimss.project.database.entity.Group;
import com.cimss.project.service.APIHandlerService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Basic user Services API")
@RestController
public class UserController {
	
	@Autowired
    private APIHandlerService apiHandlerService;

	//get method
	@Hidden
	@GetMapping("/")
	public ResponseEntity<MessageBean> homeTest() {
		return apiHandlerService.homeTest();
	}

	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Search group", description = "Search group by the keyword you provide.")
	@GetMapping("/groups/{keyword}/search")
	public List<Group.GroupData> searchGroup(@Parameter(description = "Any word", example = "my group")
											 @PathVariable String keyword) {
		return apiHandlerService.searchGroup(keyword);
	}

	//patch method
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Create new group", description = "Create your own group.")
	@PostMapping("/groups/new")
	public Group newGroup(@Valid @RequestBody ManageBean.NewGroupBean newGroupBean) {
		return apiHandlerService.newGroup(newGroupBean);
	}

	//post method
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Send text message to order user", description = "The user must be in the group that api key mapping.")
	@PostMapping("/send/text")
	public void sendTextMessage(@RequestHeader(name = "Authorization") String accessToken,
								@Valid @RequestBody ManageBean.SendBean sendBean) {
		apiHandlerService.sendTextMessage(accessToken,sendBean);
	}
}