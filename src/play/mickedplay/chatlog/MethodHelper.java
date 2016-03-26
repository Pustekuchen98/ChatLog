package play.mickedplay.chatlog;

import play.mickedplay.chatlog.log.MessageType;
import play.mickedplay.chatlog.log.ChatMessage;
import play.mickedplay.chatlog.log.GamePlayer;

/**
 * Created by Privat on 05.02.2016 at 23:44.
 */
public class MethodHelper {

    public static boolean hasPlayerSendMessage(GamePlayer gamePlayer) {
        for (ChatMessage chatMessage : gamePlayer.getChatMessages()) {
            if (chatMessage.getMessageType() == MessageType.SUCCESSFUL) {
                return true;
            }
        }
        return false;
    }
}
