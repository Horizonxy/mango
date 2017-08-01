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
}
