package com.us.nova.feature.changepassword.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.nova.feature.changepassword.packets.outgoing.ChangePasswordSuccessComposer;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangePasswordEvent extends MessageHandler {

    @Override
    public void handle() {
        String currentPassword = this.packet.readString();
        String newPassword = this.packet.readString();
        String newPasswordAgain = this.packet.readString();

        if (currentPassword == null || newPassword == null || newPasswordAgain == null ||
                !newPassword.equals(newPasswordAgain)) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.change_password.invalid"));
            this.client.sendResponse(new ChangePasswordSuccessComposer(false));
            return;
        }

        // Validate current password
        if (!doesPasswordMatch(currentPassword)) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.change_password.invalid"));
            this.client.sendResponse(new ChangePasswordSuccessComposer(false));
            return;
        }

        // Hash the new password
        String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        // Update the password in the database
        if (updatePassword(hashedNewPassword)) {
            this.client.sendResponse(new ChangePasswordSuccessComposer(true));
        } else {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.change_password.error"));
        }
    }

    private Boolean doesPasswordMatch(String rawPassword) {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT password FROM users WHERE id = ?")) {
            statement.setInt(1, this.client.getHabbo().getHabboInfo().getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("password");
                    return BCrypt.checkpw(rawPassword, storedPassword); // Bcrypt password comparison
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private Boolean updatePassword(String hashedPassword) {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE users SET password = ? WHERE id = ?")) {
            statement.setString(1, hashedPassword);
            statement.setInt(2, this.client.getHabbo().getHabboInfo().getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
