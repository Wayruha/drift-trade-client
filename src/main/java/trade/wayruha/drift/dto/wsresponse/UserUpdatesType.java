package trade.wayruha.drift.dto.wsresponse;

import lombok.Getter;

@Getter
public enum UserUpdatesType {
	CANCEL("orderCancel"), EXPIRE("orderExpire"), CREATE("orderCreate"), FILL("fill"), MODIFY("orderCancelMissing"), FUNDING("fundingPayment");

	private final String name;

	UserUpdatesType(String name) { this.name = name; }
}
