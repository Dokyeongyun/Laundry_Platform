package com.coders.laundry.service.notify;

public interface NotificationService {

  void send(int memberId, long totalTimeInMillis);
}
