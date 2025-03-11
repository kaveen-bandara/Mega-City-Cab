package com.megaCityCab.backend.controller;

import com.megaCityCab.backend.dto.Response;
import com.megaCityCab.backend.service.connection.IVehicleService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private IVehicleService vehicleService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addNewVehicle(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("licensePlate") String licensePlate,
            @RequestParam("vehicleType") String vehicleType,
            @RequestParam("model") String model,
            @RequestParam("color") String color,
            @RequestParam("description") String description,
            @RequestParam("cabFare") BigDecimal cabFare,
            @RequestParam("driverName") String driverName) {

        if(photo == null || photo.isEmpty()
                || licensePlate == null || licensePlate.isBlank()
                || vehicleType == null || vehicleType.isBlank()
                || model == null || model.isBlank()
                || color == null || color.isBlank()
                || description == null || description.isBlank()
                || cabFare == null || cabFare.compareTo(BigDecimal.ZERO) <= 0
                || driverName == null || driverName.isBlank()) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Please provide values for all fields!");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        Response response = vehicleService.addNewVehicle(photo, licensePlate, vehicleType, model, color, description, cabFare, driverName);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/types")
    public List<String> getAllVehicleTypes() {
        return vehicleService.getAllVehicleTypes();
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllVehicles() {
        Response response = vehicleService.getAllVehicles();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{cabId}")
    public ResponseEntity<Response> getVehicleById(@PathVariable("cabId") String cabId) {
        Response response = vehicleService.getVehicleById(cabId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{cabId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteVehicle(@PathVariable String cabId) {
        Response response = vehicleService.deleteVehicle(cabId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update/{cabId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateVehicleDetails(
            @PathVariable String cabId,
            @RequestParam(required = false) MultipartFile photo,
            @RequestParam(required = false) String licensePlate,
            @RequestParam(required = false) String vehicleType,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) BigDecimal cabFare,
            @RequestParam(required = false) String driverName) {
        Response response = vehicleService.updateVehicleDetails(cabId, licensePlate, vehicleType, model, color, description, cabFare, driverName, photo);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all-available-vehicles")
    public ResponseEntity<Response> getAllAvailableVehicles() {
        Response response = vehicleService.getAllAvailableVehicles();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/available-vehicles-by-datetime-and-type")
    public ResponseEntity<Response> getAvailableVehiclesByDateTimeAndVehicleType(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime pickupDateTime,
            @RequestParam @NotBlank String vehicleType) {

        Response response = vehicleService.getAvailableVehiclesByDateTimeAndVehicleType(pickupDateTime, vehicleType);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
