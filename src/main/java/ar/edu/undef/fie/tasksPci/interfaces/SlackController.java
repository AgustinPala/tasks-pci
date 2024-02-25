package ar.edu.undef.fie.tasksPci.interfaces;

import ar.edu.undef.fie.tasksPci.domain.entities.SlackMessage;
import ar.edu.undef.fie.tasksPci.services.SlackService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class SlackController {

    private final SlackService slackService;

    public SlackController(SlackService slackService) {
        this.slackService = slackService;
    }

    @PostMapping("/slack")
    public void sendMessage(@RequestBody SlackMessage slackMessage) {
       String message = slackMessage.getMessage() + " " + slackMessage.getEmoji();
       slackService.sendMessage(message);
    }
}
