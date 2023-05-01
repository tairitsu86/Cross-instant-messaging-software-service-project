package com.cimss.project.apibody;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManageBean {
    private String groupId;
    private String groupName;
    private String groupWebhook;
    private String userName;
    private String instantMessagingSoftware;
    private String instantMessagingSoftwareUserId;
    private String message;

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SendBean{
        @Schema(description = "該用戶使用的即時通訊軟體全大寫英文名稱",example = "LINE")
        private String instantMessagingSoftware;
        @Schema(description = "該用戶的即時通訊軟體ID",example = "U11111111111111111111111111111111")
        private String instantMessagingSoftwareUserId;
        @Schema(description = "要寄送的文字訊息",example = "你好!")
        private String message;
    }
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BroadcastBean{
        @Schema(description = "該群組的ID，由6位英數字組合的字串", example = "AbCd12")
        private String groupId;
        @Schema(description = "要寄送的文字訊息",example = "你好!")
        private String message;
    }
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class NewGroupBean{
        @Schema(description = "該群組的名字",example = "我ㄉ群組")
        private String groupName;
        @Schema(description = "該群組的敘述",example = "這是我的群組",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private String groupDescription;
        @Schema(description = "該群組的Webhook",example = "https://myWebService/cimssWebhook",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private String groupWebhook;
        @Schema(description = "若文字訊息開頭符合關鍵字，將觸發Webhook寄送事件",example = "myService",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private String groupKeyword;
        @Schema(description = "該群組是否為公開(能被搜尋功能找到)群組?(預設true)",example = "true",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private Boolean isPublic;
        @Schema(description = "其他使用者是否可以透過系統指令和group id加入此群組?(對API無影響)(預設true)",example = "true",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private Boolean joinById;
    }
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AlterGroupBean{
        @Schema(description = "該群組的ID，由6位英數字組合的字串", example = "AbCd12")
        private String groupId;
        @Schema(description = "該群組的名字",example = "我ㄉ群組",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private String groupName;
        @Schema(description = "該群組的敘述",example = "這是我的群組",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private String groupDescription;
        @Schema(description = "該群組的Webhook",example = "https://myWebService/cimssWebhook",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private String groupWebhook;
        @Schema(description = "若文字訊息開頭符合關鍵字，將觸發Webhook寄送事件",example = "myService",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private String groupKeyword;
        @Schema(description = "該群組是否為公開(能被搜尋功能找到)群組?",example = "true",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private Boolean isPublic;
        @Schema(description = "其他使用者是否可以透過系統指令和group id加入此群組?(對API無影響)",example = "true",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private Boolean joinById;
    }
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AddBean{
        @Schema(description = "該用戶使用的即時通訊軟體全大寫英文名稱",example = "LINE")
        private String instantMessagingSoftware;
        @Schema(description = "該用戶的即時通訊軟體ID",example = "U11111111111111111111111111111111")
        private String instantMessagingSoftwareUserId;
        @Schema(description = "該群組的ID，由6位英數字組合的字串", example = "AbCd12")
        private String groupId;
    }
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SetWebhookBean{
        @Schema(description = "該群組的ID，由6位英數字組合的字串", example = "AbCd12")
        private String groupId;
        @Schema(description = "該群組的Webhook",example = "https://myWebService/cimssWebhook")
        private String groupWebhook;
    }
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class GrantPermissionBean{
        @Schema(description = "該用戶使用的即時通訊軟體全大寫英文名稱",example = "LINE")
        private String instantMessagingSoftware;
        @Schema(description = "該用戶的即時通訊軟體ID",example = "U11111111111111111111111111111111")
        private String instantMessagingSoftwareUserId;
        @Schema(description = "該群組的ID，由6位英數字組合的字串", example = "AbCd12")
        private String groupId;
    }
}
