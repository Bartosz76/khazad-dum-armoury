package bm.app.khazaddumarmoury.order.domain;

import lombok.Value;

/**
 * Who is going to receive the armour forged.
 */

@Value
public class Recipient {
    String name;
    String runePhone;
    String street;
    String hold;
    String holdCode;
    String runeEmail;
}
