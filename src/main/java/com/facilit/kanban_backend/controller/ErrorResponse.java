package com.facilit.kanban_backend.controller;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(Instant timestamp, int status, String error, String message, String path, List<String> details) {}
