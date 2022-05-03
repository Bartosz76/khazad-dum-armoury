package bm.app.khazaddumarmoury.order.domain;

import lombok.*;

/**
 * Who is going to receive the armour forged.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recipient {
    private String name;
    private String runePhone;
    private String street;
    private String hold;
    private String holdCode;
    private String runeEmail;
}
