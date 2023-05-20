package com.cimss.project.apibody;

import com.cimss.project.database.entity.FunctionList;
import com.cimss.project.database.entity.UserId;
import com.cimss.project.database.entity.token.DeliveryMode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

public class ManageBean {
    @Getter
    @Setter
    public static class SendBean{
        @Schema(description = "Data of the user")
        @NotEmpty
        private UserId userId;
        @Schema(description = "The text message.",example = "Hello!")
        @NotEmpty
        private String message;
    }
    @Getter
    @Setter
    public static class BroadcastBean{
        @Schema(description = "The id of the group, composed of six alphanumerics.", example = "AbCd12")
        @NotEmpty
        private String groupId;
        @Schema(description = "The text message.",example = "Hello!")
        @NotEmpty
        private String message;
        @Schema(description = "User in ignore list will not get the broadcast message.")
        @NotEmpty
        private List<UserId> ignoreList;
    }
    @Getter
    @Setter
    public static class NewGroupBean{
        @Schema(description = "Name of the group.",example = "My group")
        @NotEmpty
        private String groupName;
        @Schema(description = "Description of the group.",example = "This is my group!",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private String groupDescription;
        @Schema(description = "Webhook of the group.",example = "https://myWebService/cimssWebhook",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private List<DeliveryMode> deliveryMode;
        @Schema(description = "The config file of delivery.",example = "")
        private DeliveryMode.DeliveryConfig deliveryConfig;
        @Schema(description = "This group is public or private.",example = "true",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private Boolean isPublic;
        @Schema(description = "Can any user join this group by group id?",example = "true",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private Boolean joinById;
    }
    @Getter
    @Setter
    public static class AlterGroupBean{
        @Schema(description = "The id of the group, composed of six alphanumerics.", example = "AbCd12")
        @NotEmpty
        private String groupId;
        @Schema(description = "Name of the group.",example = "My group",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private String groupName;
        @Schema(description = "Description of the group.",example = "This is my group!",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private String groupDescription;
        @Schema(description = "The delivery mode of this group.",example = "")
        private List<DeliveryMode> deliveryMode;
        @Schema(description = "The config file of delivery.",example = "")
        private DeliveryMode.DeliveryConfig deliveryConfig;
        @Schema(description = "This group is public or private.",example = "true",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private Boolean isPublic;
        @Schema(description = "Can any user join this group by group id?",example = "true",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private Boolean joinById;
    }
    @Getter
    @Setter
    public static class AddBean{
        @Schema(description = "Data of the user")
        @NotEmpty
        private UserId userId;
        @Schema(description = "The id of the group, composed of six alphanumerics.", example = "AbCd12")
        @NotEmpty
        private String groupId;
    }
    @Getter
    @Setter
    public static class GrantPermissionBean{
        @Schema(description = "Data of the user")
        @NotEmpty
        private UserId userId;
        @Schema(description = "The id of the group, composed of six alphanumerics.", example = "AbCd12")
        @NotEmpty
        private String groupId;
    }
    @Getter
    @Setter
    public static class FunctionListBean{
        @Schema(description = "The id of the group, composed of six alphanumerics.", example = "AbCd12")
        @NotEmpty
        private String groupId;
        @Schema(description = "The function list of this group.",example = "{}")
        private FunctionList functionList;
    }
}
