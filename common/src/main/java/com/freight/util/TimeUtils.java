package com.freight.util;

import com.freight.model_view.TimeModelView;

import java.time.Duration;
import java.time.Instant;

import static com.freight.model_view.TimeModelView.Unit.DAYS;
import static com.freight.model_view.TimeModelView.Unit.HOURS;
import static com.freight.model_view.TimeModelView.Unit.MINUTES;
import static com.freight.model_view.TimeModelView.Unit.MONTHS;
import static com.freight.model_view.TimeModelView.Unit.SECONDS;
import static com.freight.model_view.TimeModelView.Unit.YEARS;

/**
 * Created by toshikijahja on 3/26/19.
 */
public class TimeUtils {

    public static TimeModelView getTimePassed(final Instant created) {
        final Duration duration = Duration.between(created, Instant.now());

        System.out.println("now: " + Instant.now().toEpochMilli());
        System.out.println("created: " + created.toEpochMilli());

        if (duration.getSeconds() < 60) {
            return new TimeModelView(duration.getSeconds(), SECONDS);
        } else if (duration.toMinutes() < 60) {
            return new TimeModelView(duration.toMinutes(), MINUTES);
        } else if (duration.toHours() < 24) {
            return new TimeModelView(duration.toHours(), HOURS);
        } else if (duration.toDays() < 31) {
            return new TimeModelView(duration.toDays(), DAYS);
        } else if (duration.toDays() < 365) {
            return new TimeModelView(duration.toDays() / 30, MONTHS);
        } else {
            return new TimeModelView(duration.toDays() / 365, YEARS);
        }
    }
}
