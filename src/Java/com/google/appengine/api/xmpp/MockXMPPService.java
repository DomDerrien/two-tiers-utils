package com.google.appengine.api.xmpp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.xmpp.SendResponse.Status;

public class MockXMPPService implements XMPPService {

    private Map<String, Boolean> presences = new HashMap<String, Boolean>();

    public void setPresence(String jabberId, Boolean isAvailable) {
        presences.put(jabberId, isAvailable);
    }

    public Presence getPresence(JID jabberId) {
        return new Presence(presences.containsKey(jabberId.getId()) && presences.get(jabberId.getId()), jabberId, jabberId);
    }

    public Presence getPresence(JID jabberId, JID fromJid) {
        return getPresence(jabberId);
    }

    public Message parseMessage(HttpServletRequest arg0) throws IOException {
        return null;
    }

    public void sendInvitation(JID jabberId) {
    }

    public void sendInvitation(JID jabberId, JID fromJid) {
    }

    private List<Message> sentMessages = new ArrayList<Message>();
    private List<Status> plannedStatus = new ArrayList<Status>();

    /**
     * Inject the value of the simulated response to a message post.
     *
     * @param status simulated value
     */
    public void injectResponseStatus(Status status) {
        plannedStatus.add(status);
    }

    /**
     * Store the given message for future analysis and return
     * the corresponding simulated response (default: <code>SUCCESS</code>).
     *
     * @param message Message to track
     * @return simulated response, with the <code>SUCCESS</code> if none has been injected before
     */
    public SendResponse sendMessage(Message message) {
        Status status = plannedStatus.size() == 0 ? Status.SUCCESS : plannedStatus.remove(0);
        if (Status.SUCCESS.equals(status)) {
            sentMessages.add(0, message);
        }
        SendResponse response = new SendResponse();
        response.addStatus(message.getRecipientJids()[0], status);
        return response;
    }

    /**
     * Give the last message sent with the service
     * @return Last message sent
     */
    public Message getLastSentMessage() {
        if (sentMessages.size() == 0) {
            return null;
        }
        return sentMessages.get(0);
    }

    /**
     * Return the identified message by starting with 0 as the last sent message
     *
     * @param index place of the message in the reverse received order
     * @return message sent with the service
     */
    public Message getSentMessage(int index) {
        return sentMessages.get(index);
    }

    @Override
    public Presence parsePresence(HttpServletRequest arg0) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Subscription parseSubscription(HttpServletRequest arg0) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void sendPresence(JID arg0, PresenceType arg1, PresenceShow arg2, String arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void sendPresence(JID arg0, PresenceType arg1, PresenceShow arg2, String arg3, JID arg4) {
        // TODO Auto-generated method stub

    }

}
