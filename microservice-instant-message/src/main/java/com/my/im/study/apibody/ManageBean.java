package com.my.im.study.apibody;

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
        public ManageBean transferToManageBean(){
            ManageBean manageBean = new ManageBean();
            manageBean.setInstantMessagingSoftware(instantMessagingSoftware);
            manageBean.setInstantMessagingSoftwareUserId(instantMessagingSoftwareUserId);
            manageBean.setMessage(message);
            return manageBean;
        }
    }
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BroadcastBean{
        @Schema(description = "該群組的ID，由32位英數字組合的字串",example = "0123a012345678b0123456c012345678")
        private String groupId;
        @Schema(description = "要寄送的文字訊息",example = "你好!")
        private String message;
        public ManageBean transferToManageBean(){
            ManageBean manageBean = new ManageBean();
            manageBean.setGroupId(groupId);
            manageBean.setMessage(message);
            return manageBean;
        }
    }
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class NewGroupBean{
        @Schema(description = "該群組的名字",example = "我ㄉ群組")
        private String groupName;
        @Schema(description = "該群組的Webhook",example = "https://myWebService/cimssWebhook",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private String groupWebhook;
        public ManageBean transferToManageBean(){
            ManageBean manageBean = new ManageBean();
            manageBean.setGroupName(groupName);
            manageBean.setGroupWebhook(groupWebhook);
            return manageBean;
        }
    }
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RenameGroupBean{
        @Schema(description = "該群組的ID，由32位英數字組合的字串",example = "0123a012345678b0123456c012345678")
        private String groupId;
        @Schema(description = "該群組的新名字",example = "我ㄉ新群組")
        private String groupName;
        public ManageBean transferToManageBean(){
            ManageBean manageBean = new ManageBean();
            manageBean.setGroupId(groupId);
            manageBean.setGroupName(groupName);
            return manageBean;
        }
    }
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class JoinBean{
        @Schema(description = "該用戶使用的即時通訊軟體全大寫英文名稱",example = "LINE")
        private String instantMessagingSoftware;
        @Schema(description = "該用戶的即時通訊軟體ID",example = "U11111111111111111111111111111111")
        private String instantMessagingSoftwareUserId;
        @Schema(description = "該群組的ID，由32位英數字組合的字串",example = "0123a012345678b0123456c012345678")
        private String groupId;
        public ManageBean transferToManageBean(){
            ManageBean manageBean = new ManageBean();
            manageBean.setInstantMessagingSoftware(instantMessagingSoftware);
            manageBean.setInstantMessagingSoftwareUserId(instantMessagingSoftwareUserId);
            manageBean.setGroupId(groupId);
            return manageBean;
        }
    }
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SetWebhookBean{
        @Schema(description = "該群組的ID，由32位英數字組合的字串",example = "0123a012345678b0123456c012345678")
        private String groupId;
        @Schema(description = "該群組的Webhook",example = "https://myWebService/cimssWebhook")
        private String groupWebhook;
        public ManageBean transferToManageBean(){
            ManageBean manageBean = new ManageBean();
            manageBean.setGroupId(groupId);
            manageBean.setGroupWebhook(groupWebhook);
            return manageBean;
        }
    }
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class GrantPermissionBean{
        @Schema(description = "該用戶使用的即時通訊軟體全大寫英文名稱",example = "LINE")
        private String instantMessagingSoftware;
        @Schema(description = "該用戶的即時通訊軟體ID",example = "U11111111111111111111111111111111")
        private String instantMessagingSoftwareUserId;
        @Schema(description = "該群組的ID，由32位英數字組合的字串",example = "0123a012345678b0123456c012345678")
        private String groupId;
        public ManageBean transferToManageBean(){
            ManageBean manageBean = new ManageBean();
            manageBean.setInstantMessagingSoftware(instantMessagingSoftware);
            manageBean.setInstantMessagingSoftwareUserId(instantMessagingSoftwareUserId);
            manageBean.setGroupId(groupId);
            return manageBean;
        }
    }
}
