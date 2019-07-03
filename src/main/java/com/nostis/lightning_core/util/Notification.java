package com.nostis.lightning_core.util;

import com.nostis.lightning_core.util.Informations;

public interface Notification {
    void sendNotification(Informations informations, String content);
}
