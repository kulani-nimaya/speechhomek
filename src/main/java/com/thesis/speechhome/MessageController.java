package com.thesis.speechhome;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {

    private final MessageRepository repo;

    public MessageController(MessageRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/patients/{id}/messages")
    public List<Message> getMessages(@PathVariable Long id) {
        return repo.findByPatientIdOrderByCreatedAtAsc(id);
    }

    @PostMapping("/patients/{id}/messages")
    public Message sendMessage(@PathVariable Long id,
                               @RequestParam String content,
                               @RequestParam String senderRole,
                               @RequestParam Long senderId) {

        Message m = new Message();
        m.setPatientId(id);
        m.setContent(content);
        m.setSenderRole(senderRole);
        m.setSenderId(senderId);

        return repo.save(m);
    }
}