package cn.com.mangopi.android.util;

public class BusEvent {

    @com.mcxiaoke.bus.annotation.BusEvent
    public static class NetEvent{
        private boolean hasNet;

        public boolean isHasNet() {
            return hasNet;
        }

        public void setHasNet(boolean hasNet) {
            this.hasNet = hasNet;
        }
    }

    @com.mcxiaoke.bus.annotation.BusEvent
    public static class ActivityFinishEvent{
        private boolean finish;

        public boolean isFinish() {
            return finish;
        }

        public void setFinish(boolean finish) {
            this.finish = finish;
        }
    }

    @com.mcxiaoke.bus.annotation.BusEvent
    public static class WxOpenIdEvent{
        private String openId;
        private String unoinId;

        public String getUnoinId() {
            return unoinId;
        }

        public void setUnoinId(String unoinId) {
            this.unoinId = unoinId;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }
    }

    @com.mcxiaoke.bus.annotation.BusEvent
    public static class FileDownLoadEvent{
        private long progress, total;

        public FileDownLoadEvent(long progress, long total) {
            this.progress = progress;
            this.total = total;
        }

        public long getProgress() {
            return progress;
        }

        public void setProgress(long progress) {
            this.progress = progress;
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }
    }

    @com.mcxiaoke.bus.annotation.BusEvent
    public static class InputEvent{
        private String type;
        private String content;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    @com.mcxiaoke.bus.annotation.BusEvent
    public static class HasMessageEvent{
        private boolean hasMessage;

        public boolean isHasMessage() {
            return hasMessage;
        }

        public void setHasMessage(boolean hasMessage) {
            this.hasMessage = hasMessage;
        }
    }

    @com.mcxiaoke.bus.annotation.BusEvent
    public static class RefreshMemberEvent {

    }

    @com.mcxiaoke.bus.annotation.BusEvent
    public static class RefreshTrendListEvent {

    }

    @com.mcxiaoke.bus.annotation.BusEvent
    public static class CancelOrderEvent{
        private long id;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }

    @com.mcxiaoke.bus.annotation.BusEvent
    public static class CancelOrderSeheduleEvent{
        private long id;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }

    @com.mcxiaoke.bus.annotation.BusEvent
    public static class ActorCompanyCommentEvent{
        private long id;
        private String comment;
        private int score;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }

    @com.mcxiaoke.bus.annotation.BusEvent
    public static class PayOrderSuccessEvent{
        private long id;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }

    @com.mcxiaoke.bus.annotation.BusEvent
    public static class PayCodeEvent{
        private int errorCode;

        public PayCodeEvent(int errorCode) {
            this.errorCode = errorCode;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }
    }
}
