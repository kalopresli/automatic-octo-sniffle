package com.backend.votingservice.controller;

import com.backend.votingservice.domain.Party;
import com.backend.votingservice.service.MessageSenderService;
import com.backend.votingservice.service.PartyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/party")
public class PartyController {

    private final PartyService partyService;
    private final MessageSenderService messageSenderService;

    @GetMapping("/{userId}")
    public Party getParties(@PathVariable Long userId) throws Exception {
        return partyService.getParty(userId);
    }

    @PostMapping("/create")
    public void createParty (@RequestBody Long userId) {
        messageSenderService.sendPartyCreationTrigger(userId);
    }
}
