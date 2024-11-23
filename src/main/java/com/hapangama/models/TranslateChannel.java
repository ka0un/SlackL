package com.hapangama.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslateChannel {
    String channelId;
    String lang;
    boolean includeOriginal;
    boolean asReply;
    boolean asUser;
}
