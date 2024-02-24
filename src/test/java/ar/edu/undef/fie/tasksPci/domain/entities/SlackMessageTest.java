package ar.edu.undef.fie.tasksPci.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SlackMessageTest {

    @Test
    void setMessage() {
        SlackMessage slackMessage = new SlackMessage();
        slackMessage.setMessage("Test Message");
        assertEquals("Test Message", slackMessage.getMessage());
    }

    @Test
    void getMessage() {
        SlackMessage slackMessage = new SlackMessage();
        slackMessage.setMessage("Test Message");
        assertEquals("Test Message", slackMessage.getMessage());
    }

    @Test
    void setEmoji() {
        SlackMessage slackMessage = new SlackMessage();
        slackMessage.setEmoji(":grinning:");
        assertEquals(":grinning:", slackMessage.getEmoji());
    }

    @Test
    void getEmoji() {
        SlackMessage slackMessage = new SlackMessage();
        slackMessage.setEmoji(":grinning:");
        assertEquals(":grinning:", slackMessage.getEmoji());
    }
}