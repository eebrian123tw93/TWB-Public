package com.cb.Shuo.service.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class GetCurrentLocalDateTime {
  private static final String serverTimeZone = "Asia/Taipei";

  public static LocalDateTime getCurrentTime() {
    return LocalDateTime.now(ZoneId.of(serverTimeZone)).withNano(0);
  }
}
