package ar.edu.undef.fie.tasksPci.interfaces;

import ar.edu.undef.fie.tasksPci.domain.entities.SlackMessage;
import ar.edu.undef.fie.tasksPci.services.SlackService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SlackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SlackService slackService;

    @Test
    void sendMessage() throws Exception {
        SlackMessage slackMessage = new SlackMessage("Test Message", ":grinning:");

        mockMvc.perform(post("/api/v1/slack")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"Test Message\", \"emoji\":\":grinning:\"}"))
                .andExpect(status().isOk());

        verify(slackService, times(1)).sendMessage(slackMessage.getMessage() + " " + slackMessage.getEmoji());
    }
}