package com.ptms.app.service;

import com.ptms.app.model.TicketTracking;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class TicketTrackingServiceTest {

    private final TicketTrackingService trackingService =
            new TicketTrackingService();

    @Test
    void createTrackingShouldRejectNullTracking()
            throws SQLException {

        assertThrows(
                IllegalArgumentException.class,
                () -> trackingService.createTracking(null)
        );
    }

    @Test
    void createTrackingShouldRejectInvalidTicketId()
            throws SQLException {

        TicketTracking tracking = new TicketTracking();

        tracking.setTicketId(0);
        tracking.setStatus("OPEN");
        tracking.setProgress(0);
        tracking.setUpdatedBy(1);

        assertThrows(
                IllegalArgumentException.class,
                () -> trackingService.createTracking(tracking)
        );
    }

    @Test
    void createTrackingShouldRejectMissingStatus()
            throws SQLException {

        TicketTracking tracking = new TicketTracking();

        tracking.setTicketId(1);
        tracking.setStatus("");
        tracking.setProgress(0);
        tracking.setUpdatedBy(1);

        assertThrows(
                IllegalArgumentException.class,
                () -> trackingService.createTracking(tracking)
        );
    }

    @Test
    void createTrackingShouldRejectInvalidStatus()
            throws SQLException {

        TicketTracking tracking = new TicketTracking();

        tracking.setTicketId(1);
        tracking.setStatus("INVALID");
        tracking.setProgress(0);
        tracking.setUpdatedBy(1);

        assertThrows(
                IllegalArgumentException.class,
                () -> trackingService.createTracking(tracking)
        );
    }

    @Test
    void createTrackingShouldRejectProgressBelowZero()
            throws SQLException {

        TicketTracking tracking = new TicketTracking();

        tracking.setTicketId(1);
        tracking.setStatus("OPEN");
        tracking.setProgress(-1);
        tracking.setUpdatedBy(1);

        assertThrows(
                IllegalArgumentException.class,
                () -> trackingService.createTracking(tracking)
        );
    }

    @Test
    void createTrackingShouldRejectProgressAbove100()
            throws SQLException {

        TicketTracking tracking = new TicketTracking();

        tracking.setTicketId(1);
        tracking.setStatus("OPEN");
        tracking.setProgress(101);
        tracking.setUpdatedBy(1);

        assertThrows(
                IllegalArgumentException.class,
                () -> trackingService.createTracking(tracking)
        );
    }

    @Test
    void createTrackingShouldRejectInvalidUpdatedBy()
            throws SQLException {

        TicketTracking tracking = new TicketTracking();

        tracking.setTicketId(1);
        tracking.setStatus("OPEN");
        tracking.setProgress(0);
        tracking.setUpdatedBy(0);

        assertThrows(
                IllegalArgumentException.class,
                () -> trackingService.createTracking(tracking)
        );
    }

    @Test
    void createTrackingShouldRejectCompletedWithLessThan100Progress()
            throws SQLException {

        TicketTracking tracking = new TicketTracking();

        tracking.setTicketId(1);
        tracking.setStatus("COMPLETED");
        tracking.setProgress(90);
        tracking.setUpdatedBy(1);

        assertThrows(
                IllegalArgumentException.class,
                () -> trackingService.createTracking(tracking)
        );
    }

    @Test
    void findTrackingByTicketIdShouldRejectInvalidId() {

        assertThrows(
                IllegalArgumentException.class,
                () -> trackingService.findTrackingByTicketId(0)
        );
    }

    @Test
    void findTrackingByIdShouldRejectInvalidId() {

        assertThrows(
                IllegalArgumentException.class,
                () -> trackingService.findTrackingById(0)
        );
    }

    @Test
    void updateTrackingShouldRejectNullTracking()
            throws SQLException {

        assertThrows(
                IllegalArgumentException.class,
                () -> trackingService.updateTracking(null)
        );
    }

    @Test
    void updateTrackingShouldRejectInvalidId()
            throws SQLException {

        TicketTracking tracking = new TicketTracking();

        tracking.setTicketId(1);
        tracking.setStatus("OPEN");
        tracking.setProgress(0);
        tracking.setUpdatedBy(1);
        tracking.setId(0);

        assertThrows(
                IllegalArgumentException.class,
                () -> trackingService.updateTracking(tracking)
        );
    }
}

