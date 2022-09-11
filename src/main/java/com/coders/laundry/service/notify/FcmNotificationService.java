package com.coders.laundry.service.notify;

import com.coders.laundry.domain.entity.DeviceTokenEntity;
import com.coders.laundry.repository.DeviceTokenRepository;
import com.coders.laundry.scheduler.NotificationScheduler;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class FcmNotificationService implements NotificationService {

    private static final String TITLE = "Laundry";
    private static final String START_MESSAGE = "빨래가 시작되었습니다.";
    private static final String IN_PROGRESS_MESSAGE = "빨래완료 10분전입니다.";
    private static final String COMPLETE_MESSAGE = "빨래가 완료되었습니다.";

    private static final long TEN_MINUTE = 1000 * 60 * 10L;

    private final DeviceTokenRepository deviceTokenRepository;
    private final NotificationScheduler scheduler;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Value("${fcm.path}")
    private String FCM_PRIVATE_KEY_PATH;

    @PostConstruct
    public void init() {
        // TODO: 파일경로를 선언해주는건 보안적으로좋지않음. 환경변수로 export 하여 접근하도록 할것.
        try {
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials
                    .fromStream(new FileUrlResource(FCM_PRIVATE_KEY_PATH).getInputStream())
                    .createScoped(
                        "https://www.googleapis.com/auth/firebase",
                        "https://www.googleapis.com/auth/cloud-platform",
                        "https://www.googleapis.com/auth/firebase.readonly"
                    )
                )
                .build();
            if (FirebaseApp.getApps().isEmpty()) {
                log.info("successfully FirebaseApp init");
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void send(int memberId, long totalTimeInMillis) {
        DeviceTokenEntity deviceTokenEntity = deviceTokenRepository.selectByMemberId(memberId);

        Message startMessage = buildMessage(START_MESSAGE, deviceTokenEntity.getToken());
        Message inProgressMessage = buildMessage(IN_PROGRESS_MESSAGE, deviceTokenEntity.getToken());
        Message completeMessage = buildMessage(COMPLETE_MESSAGE, deviceTokenEntity.getToken());

        reserveSendMessage(startMessage, Duration.ZERO);
        reserveSendMessage(inProgressMessage, Duration.ofMillis(totalTimeInMillis - TEN_MINUTE));
        reserveSendMessage(completeMessage, Duration.ofMillis(totalTimeInMillis));
    }

    private CompletableFuture<Void> reserveSendMessage(Message message, Duration duration) {
        return scheduler.submit(() -> {
                try {
                    FirebaseMessaging.getInstance().send(message);
                    log.info("Successfully sent message: {}", message);
                } catch (FirebaseMessagingException e) {
                    log.error(e);
                }
            },
            duration
        );
    }

    private Message buildMessage(String message, String deviceToken) {
        return Message.builder()
            .putData("time", dateFormat.format(new Date()))
            .setNotification(
                Notification.builder()
                    .setTitle(TITLE)
                    .setBody(message)
                    .build())
            .setToken(deviceToken)
            .build();
    }
}
