package com.us.nova.feature.security.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.nova.feature.security.packets.outgoing.CurrentEmailComposer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangeEmailEvent extends MessageHandler {

    @Override
    public void handle() {
        String newEmail = this.packet.readString();

        if (newEmail == null || !isValidEmail(newEmail) || !isNotTaken(newEmail)) {
            this.client.getHabbo().whisper("nova.change_email.invalid");
            this.client.sendResponse(new CurrentEmailComposer(this.client.getHabbo(), false));
            return;
        }

        String password = this.packet.readString();

        if (password == null) {
            this.client.getHabbo().whisper("nova.change_email.invalid");
            this.client.sendResponse(new CurrentEmailComposer(this.client.getHabbo(), false));
            return;
        }

        if (!ChangePasswordEvent.doesPasswordMatch(this.client.getHabbo().getHabboInfo().getId(), password)) {
            this.client.getHabbo().whisper("nova.change_email.invalid");
            this.client.sendResponse(new CurrentEmailComposer(this.client.getHabbo(), false));
            return;
        }

        this.client.getHabbo().getHabboInfo().setMail(newEmail);
        this.client.getHabbo().getHabboInfo().run();

        this.client.sendResponse(new CurrentEmailComposer(this.client.getHabbo(), true));
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    private boolean isNotTaken(String email) {
        String query = "SELECT 1 FROM users WHERE mail = ?";
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                return !resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error while checking email availability", e);
        }
    }



}