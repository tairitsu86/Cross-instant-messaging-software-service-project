package com.cimss.project.apibody;

import com.cimss.project.database.entity.UserId;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

public class ManageBean {
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SendBean{
        @Schema(description = "Data of the user")
        private UserId userId;
        @Schema(description = "The text message.",example = "Hello!")
        private String message;
    }
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BroadcastBean{
        @Schema(description = "The id of the group, composed of six alphanumerics.", example = "AbCd12")
        private String groupId;
        @Schema(description = "The text message.",example = "Hello!")
        private String message;
        @Schema(description = "User in ignore list will not get the broadcast message.")
        private List<UserId> ignoreList;
    }
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class NewGroupBean{
        @Schema(description = "Name of the group.",example = "My group")
        private String groupName;
        @Schema(description = "Description of the group.",example = "This is my group!",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private String groupDescription;
        @Schema(description = "Webhook of the group.",example = "https://myWebService/cimssWebhook",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private String groupWebhook;
        @Schema(description = "The prefix of the command this group provide.",example = "myService",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private String groupKeyword;
        @Schema(description = "This group is public or private.",example = "true",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private Boolean isPublic;
        @Schema(description = "Can any user join this group by group id?",example = "true",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private Boolean joinById;
    }
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AlterGroupBean{
        @Schema(description = "The id of the group, composed of six alphanumerics.", example = "AbCd12")
        private String groupId;
        @Schema(description = "Name of the group.",example = "My group",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private String groupName;
        @Schema(description = "Description of the group.",example = "This is my group!",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private String groupDescription;
        @Schema(description = "Webhook of the group.",example = "https://myWebService/cimssWebhook",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private String groupWebhook;
        @Schema(description = "The prefix of the command this group provide.",example = "myService",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private String groupKeyword;
        @Schema(description = "This group is public or private.",example = "true",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private Boolean isPublic;
        @Schema(description = "Can any user join this group by group id?",example = "true",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private Boolean joinById;
    }
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AddBean{
        @Schema(description = "Data of the user")
        private UserId userId;
        @Schema(description = "The id of the group, composed of six alphanumerics.", example = "AbCd12")
        private String groupId;
    }
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SetWebhookBean{
        @Schema(description = "The id of the group, composed of six alphanumerics.", example = "AbCd12")
        private String groupId;
        @Schema(description = "Webhook of the group.",example = "https://myWebService/cimssWebhook")
        private String groupWebhook;
    }
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class GrantPermissionBean{
        @Schema(description = "Data of the user")
        private UserId userId;
        @Schema(description = "The id of the group, composed of six alphanumerics.", example = "AbCd12")
        private String groupId;
    }
}
