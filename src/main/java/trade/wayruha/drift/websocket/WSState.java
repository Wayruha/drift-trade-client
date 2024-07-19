package trade.wayruha.drift.websocket;

public enum WSState {
    IDLE,
    DELAY_CONNECT,
    CONNECTED,
    CLOSED_ON_ERROR,
    CONNECTING
}
