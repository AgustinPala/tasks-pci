package ar.edu.undef.fie.tasksPci.domain.entities;

public class SlackMessage {

    private String message;
    private String emoji;

    public SlackMessage(String message, String emoji) {
        this.message = message;
        this.emoji = emoji;
    }

    public SlackMessage() {}

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getEmoji() {
        return emoji;
    }
}

