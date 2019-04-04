package com.cb.Shuo.service.util;

import java.time.Clock;
import java.time.LocalDateTime;

public class GetCurrentLocalDateTime {
  //  private static final String serverTimeZone = "Asia/Taipei";
  //  private static final String serverTimeZone = "UTC";

  public static LocalDateTime getCurrentTime() {
    return LocalDateTime.now(Clock.systemDefaultZone()).withNano(0);
  }
}
