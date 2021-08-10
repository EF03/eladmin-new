package me.zhengjie.enums;

/**
 * @author bron
 */
public class HtechEnum {


    public enum HazelcastTopic {
        /**
         * Hazelcast topic 事件
         */
        TOPIC_ENT_IM,
        TOPIC_IM,
        TOPIC_AGENT,
        TOPIC_CALL_CENTER,
        NAMESPACE,
        TOPIC_JOB_DETAIL,
        TOPIC_VOTE,
        REMOVE_SESSION;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    /**
     * Hazelcast cache 名稱
     */
    public enum CacheName {
        //  登錄用戶
        USERS,
        // API_USER
        API_USER,
        //  登錄用戶的session
        SESSIONS;
        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }


}
