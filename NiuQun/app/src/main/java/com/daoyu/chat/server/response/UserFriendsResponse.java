package com.daoyu.chat.server.response;

import java.util.List;

import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * 好友列表响应
 *
 */
public class UserFriendsResponse
{

    private static final String TAG = "UserFriendsResponse";

    private String code;

    private String message;

    private List<ResultEntity> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(List<ResultEntity> data)
    {
        this.data = data;
    }

    public List<ResultEntity> getResult()
    {
        return data;
    }

    public static class ResultEntity implements Comparable
    {

        private UserEntity user;

        private String user_id;

        private String user_name;

        private String avatar;

        private String token;

        private String message;

        private String displayName;

        private String status;

        private String updatedAt;

        public ResultEntity()
        {

        }

        public ResultEntity(String user_name, String message, String user_id, String token, String avatar,
            String status)
        {
            this.user_name = user_name;
            this.message = message;
            this.user_id = user_id;
            this.token = token;
            this.avatar = avatar;
            this.status = status;
        }

        public String getUser_id()
        {
            return user_id;
        }

        public void setUser_id(String user_id)
        {
            this.user_id = user_id;
        }

        public String getUser_name()
        {
            return user_name;
        }

        public void setUser_name(String user_name)
        {
            this.user_name = user_name;
        }

        public String getAvatar()
        {
            return avatar;
        }

        public void setAvatar(String avatar)
        {
            this.avatar = avatar;
        }

        public String getToken()
        {
            return token;
        }

        public void setToken(String token)
        {
            this.token = token;
        }

        public void setDisplayName(String displayName)
        {
            this.displayName = displayName;
        }

        public void setMessage(String message)
        {
            this.message = message;
        }

        public void setStatus(String status)
        {
            this.status = status;
        }

        public void setUpdatedAt(String updatedAt)
        {
            this.updatedAt = updatedAt;
        }

        public void setUser(UserEntity user)
        {
            this.user = user;
        }

        public String getDisplayName()
        {
            return TextUtils.isEmpty(displayName) ? "" : displayName;
        }

        public String getMessage()
        {
            return message;
        }

        public String getStatus()
        {
            //已是好友
            if (TextUtils.isEmpty(status))
            {
                return "1";
            }
            return status;
        }

        public String getUpdatedAt()
        {
            return TextUtils.isEmpty(updatedAt) ? "" : updatedAt;
        }

        public UserEntity getUser()
        {
            if (null == user)
            {
                user = new UserEntity();
                user.setId(user_id);
                user.setNickname(user_name);
                user.setPortraitUri(avatar);
            }
            return user;
        }

        @Override
        public int compareTo(@NonNull Object another)
        {
            if (another instanceof ResultEntity)
            {
                ResultEntity other = (ResultEntity) another;
                return this.user_id.equals(other.getUser_id()) ? 1 : 0;
            }
            return 0;
        }

        public String toString()
        {
            return  "ResultEntity{user_name = " + user_name + "}";
        }

        public static class UserEntity
        {
            private String id;
            private String nickname;
            private String portraitUri;

            public void setId(String id)
            {
                this.id = id;
            }

            public void setNickname(String nickname)
            {
                this.nickname = nickname;
            }

            public void setPortraitUri(String portraitUri)
            {
                this.portraitUri = portraitUri;
            }

            public String getId()
            {
                return id;
            }

            public String getNickname()
            {
                return nickname;
            }

            public String getPortraitUri()
            {
                return portraitUri;
            }
        }
    }

    public String toString()
    {
        return TAG + "{code = " + code + " ,message " + message + " ,data = " + data.toString() + "}";
    }

}
