package com.google.wave.api;

import java.util.List;

public class MockRobotMessageBundle implements RobotMessageBundle {
    public boolean blipHasChanged(Blip blip) { return false; }
    public Wavelet createWavelet(List<String> participants) { return null; }
    public Wavelet createWavelet(List<String> participants, String dataDocumentWriteBack) { return null; }
    public List<Event> filterEventsByType(EventType eventType) { return null; }
    public Blip getBlip(String waveId, String waveletId, String blipId) { return null; }
    public List<Event> getBlipSubmittedEvents() { return null; }
    public List<Event> getEvents() { return null; }
    public List<Event> getParticipantsChangedEvents() { return null; }
    public String getRobotAddress() { return null; }
    public Wavelet getWavelet() { return null; }
    public Wavelet getWavelet(String waveId, String waveletId) { return null; }
    public boolean isNewWave() { return false; }
    public boolean wasParticipantAddedToNewWave(String participantId) { return false; }
    public boolean wasParticipantAddedToWave(String participantId) { return false; }
    public boolean wasSelfAdded() { return false; }
    public boolean wasSelfRemoved() { return false; }
};
